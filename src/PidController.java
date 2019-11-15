/**
 * Reads the data from the ballPos storagebox and generates the new servo angles and puts the values into the servoPos storagebox
 */

public class PidController extends Thread {
   //Create a shared Event handler class
   private Event eventBallStorageBox;
   private Event eventPlatformAngleStorageBox;

    //Storage boxes
    private SB_ballPos storageBoxBall;
    private SB_platformAngle storageBoxAngle;
    private SB_pidValues storageBoxPid;
    private SB_setpoint storageBoxSetpoint;

    //Outputs
    private double outputX = 0;
    private double outputY = 0;

    //PID contributions
    private double integralX = 0;
    private double derivativeX = 0;
    private double prevErrorX = 0;
    private double integralY = 0;
    private double derivativeY = 0;
    private double prevErrorY = 0;

    // Filter constributons
    private static final double ALPHA = 0.2;
    private double prevOutputX;
    private double prevOutputY;

    /**
     * Constructor
     * @param eventBallStorageBox event for the ball storage box
     * @param storageBoxBall storage box for ball position
     * @param eventPlatformAngleStorageBox event for the platform storage box
     * @param storageBoxAngle storage box for the platform angle
     */
    public PidController(Event eventBallStorageBox, SB_ballPos storageBoxBall, Event eventPlatformAngleStorageBox, SB_platformAngle storageBoxAngle, SB_pidValues storageBoxPid, SB_setpoint storageBoxSetpoint) {
        this.storageBoxBall = storageBoxBall;
        this.storageBoxAngle = storageBoxAngle;
        this.eventBallStorageBox = eventBallStorageBox;
        this.eventPlatformAngleStorageBox = eventPlatformAngleStorageBox;
        this.storageBoxPid = storageBoxPid;
        this.storageBoxSetpoint = storageBoxSetpoint;
    }

    /**
     * Calculates the pitch and roll of the platform when ball position is given
     */
    public void run() {
        PidValues pidValues = this.storageBoxPid.getPidValues();
        Setpoint setpoint = this.storageBoxSetpoint.getSetpoint();
        while(true) {
            if(this.storageBoxPid.hasChanges()){
                pidValues = this.storageBoxPid.getPidValues();
            }
            if(this.storageBoxSetpoint.hasChanges())
                setpoint = this.storageBoxSetpoint.getSetpoint();
            try {
                //Wait conditionally for the correct state
                this.eventBallStorageBox.await(Event.EventState.UP);
            }   catch (InterruptedException e) {}
            double ballPosX = storageBoxBall.getX();
            double ballPosY = storageBoxBall.getY();
            double errorX = setpoint.getSetpointX() - ballPosX;
            double errorY = setpoint.getSetpointY() - ballPosY;

            //Toggle event state
            this.eventBallStorageBox.toggle();

            if(errorX <-10 || errorX > 10){
                integralX = integralX + errorX*pidValues.getDt();
            }else{
                integralX = 0;
            }

            derivativeX = (errorX - prevErrorX)/pidValues.getDt();
            outputX = (pidValues.getKp()*errorX) + (pidValues.getKi()*integralX) + (pidValues.getKd()*derivativeX);
            prevErrorX = errorX;

            if(errorY <-10 || errorY > 10){
                integralY = integralY + errorY*pidValues.getDt();
            }else{
                integralY = 0;
            }

            derivativeY = (errorY - prevErrorY)/pidValues.getDt();
            outputY = (pidValues.getKp()*errorY) + (pidValues.getKi()*integralY) + (pidValues.getKd()*derivativeY);
            prevErrorY = errorY;

            //Makes sure the pitch and roll never exceeds +/- 30 degrees
            if(outputX > 30){
                outputX = 30;
            } else if(outputX < -30){
                outputX = -30;
            }
            if(outputY > 30){
                outputY = 30;
            } else if(outputY < -30){
                outputY = -30;
            }

            try {
                Thread.sleep(Math.round(pidValues.getDt()*1000));
            } catch (Exception ex){
                System.out.println(ex.toString());
            }

            try {
                //Wait conditionally for the correct state
                this.eventPlatformAngleStorageBox.await(Event.EventState.DOWN);
            }   catch (InterruptedException e) {}

            outputX = lowPass (outputX, prevOutputX, pidValues.getFilter_alpha(), pidValues.getFilter_iterations());
            outputY = lowPass(outputY, prevOutputY, pidValues.getFilter_alpha(), pidValues.getFilter_iterations());


            //Set pitch and roll
            this.storageBoxAngle.setAngle(outputX, -outputY);
            prevOutputX = outputX;
            prevOutputY = outputY;
            //Toggle event state
            this.eventPlatformAngleStorageBox.toggle();

            //Debug
            //System.out.println("--PidController--\n"+outputX+", "+outputY+"\n"+ballPosX+", "+ballPosY+"\n");
        }
    }

    /**
     *
     * @param input
     * @param output
     * @return
     */
    private double lowPass (double input, double output, double alpha, int iterations) {
        for ( int i=0; i<iterations; i++ ) {
            output = output + alpha * (input - output);
        }
        return output;
    }


}
