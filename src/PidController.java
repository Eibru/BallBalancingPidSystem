/**
 * Reads the data from the ballPos storagebox and generates the new servo angles and puts the values into the servoPos storagebox
 */

public class PidController extends Thread {

    //Storage boxes
    private SB_ballPos storageBoxBall;
    private SB_platformAngle storageBoxAngle;

    // PID gains:
    private double Kp = 4;
    private double Ki = 0;
    private double Kd = 0;

    // Setpoint
    private double setpointX = 0;
    private double setpointY = 0;

    // Outputs
    private double outputX;
    private double outputY;

    // PID contributions
    private double integralX = 0;
    private double derivativeX = 0;
    private double prevErrorX = 0;
    private double integralY = 0;
    private double derivativeY = 0;
    private double prevErrorY = 0;
    private double DT = 0.010; //sek


    /**
     * Constructor
     *
     * @param storageBoxBall  Storagebox for ball position
     */
    public PidController(SB_ballPos storageBoxBall, SB_platformAngle storageBoxAngle) {
        this.storageBoxBall = storageBoxBall;

    }

    public void run() {
        while(true) {
            double ballPosX = storageBoxBall.getX();
            double ballPosY = storageBoxBall.getY();

            double errorX = setpointX - ballPosX;
            if(errorX <-2 || errorX > 2){
                integralX = integralX + errorX*DT;
            }else{
                integralX = 0;
            }
            derivativeX = (errorX - prevErrorX)/DT;
            outputX = (Kp*errorX) + (Ki*integralX) + (Kd*derivativeX);
            prevErrorX = errorX;


            double errorY = setpointY - ballPosY;
            if(errorY <-2 || errorY > 2){
                integralY = integralY + errorY*DT;
            }else{
                integralY = 0;
            }
            derivativeY = (errorY - prevErrorY)/DT;
            outputY = (Kp*errorY) + (Ki*integralY) + (Kd*derivativeY);
            prevErrorY = errorY;



            this.storageBoxAngle.setAngle(outputX, outputY);
        }
    }

    /**
     * PID controller for platform in y og x movement
     *
     * @param input The current ball position in y or x direction
     * @param setpoint Desired ball position in y or x direction
     * @return The angle which the platform needs to move in given direction
     */
    double PIDcontroller(double setpoint, double input){
        /*double error = setpoint - input;
        if(error <-2 || error > 2){
            integral = integral + error*DT;
        }else{
            integral = 0;
        }
        derivative = (error - prevError)/DT;
        double output = (Kp*error) + (Ki*integral) + (Kd*derivative);
        prevError = error;
        return output;*/
        return 0;
    }
}
