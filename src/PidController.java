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

    //PID gains
    private double Kp = 0.13;
    private double Ki = 0.0;
    private double Kd = 0.001;

    //Setpoint
    private double setpointX = 0;
    private double setpointY = 0;

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
    private double DT = 0.001; //sek

    /**
     * Constructor
     * @param eventBallStorageBox event for the ball storage box
     * @param storageBoxBall storage box for ball position
     * @param eventPlatformAngleStorageBox event for the platform storage box
     * @param storageBoxAngle storage box for the platform angle
     */
    public PidController(Event eventBallStorageBox, SB_ballPos storageBoxBall, Event eventPlatformAngleStorageBox, SB_platformAngle storageBoxAngle) {
        this.storageBoxBall = storageBoxBall;
        this.storageBoxAngle = storageBoxAngle;
        this.eventBallStorageBox = eventBallStorageBox;
        this.eventPlatformAngleStorageBox = eventPlatformAngleStorageBox;
    }

    /**
     * Calculates the pitch and roll of the platform when ball position is given
     */
    public void run() {
        while(true) {
            try {
                //Wait conditionally for the correct state
                this.eventBallStorageBox.await(Event.EventState.UP);
            }   catch (InterruptedException e) {}
            double ballPosX = storageBoxBall.getX();
            double ballPosY = storageBoxBall.getY();
            double errorX = setpointX - ballPosX;
            double errorY = setpointY - ballPosY;

            //Toggle event state
            this.eventBallStorageBox.toggle();

            if(errorX <-2 || errorX > 2){
                integralX = integralX + errorX*DT;
            }else{
                integralX = 0;
            }

            derivativeX = (errorX - prevErrorX)/DT;
            outputX = (Kp*errorX) + (Ki*integralX) + (Kd*derivativeX);
            prevErrorX = errorX;

            if(errorY <-2 || errorY > 2){
                integralY = integralY + errorY*DT;
            }else{
                integralY = 0;
            }

            derivativeY = (errorY - prevErrorY)/DT;
            outputY = (Kp*errorY) + (Ki*integralY) + (Kd*derivativeY);
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
                Thread.sleep(Math.round(DT*1000));
            } catch (Exception ex){
                System.out.println(ex.toString());
            }

            try {
                //Wait conditionally for the correct state
                this.eventPlatformAngleStorageBox.await(Event.EventState.DOWN);
            }   catch (InterruptedException e) {}

            //Set pitch and roll
            this.storageBoxAngle.setAngle(outputX, -outputY);

            //Toggle event state
            this.eventPlatformAngleStorageBox.toggle();

            //Debug
            //System.out.println("--PidController--\n"+outputX+", "+outputY+"\n"+ballPosX+", "+ballPosY+"\n");
        }
    }
}
