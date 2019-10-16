/**
 * Reads the data from the ballPos storagebox and generates the new servo angles and puts the values into the servoPos storagebox
 */

public class PidController extends Thread {
    SB_ballPos storageBoxBall;

    // PID gains:
    private double Kp = 4;
    private double Ki = 0;
    private double Kd = 0;

    // Setpoint
    private double setpointX = 0;
    private double setpointY = 0;

    // Outputs
    private double outputX;
    private double outputy;

    // PID contributions
    private double integral = 0;
    private double derivative = 0;
    private double prevError = 0;
    private double DT = 0.010; //sek


    /**
     * Constructor
     *
     * @param storageBoxBall  Storagebox for ball position
     */
    public PidController(SB_ballPos storageBoxBall) {
        this.storageBoxBall = storageBoxBall;

    }

    public void run() {

        double ballPosX = storageBoxBall.getX();
        double ballPosY = storageBoxBall.getY();

        outputX = PIDcontroller(setpointX, ballPosX);
        outputy = PIDcontroller(setpointY, ballPosY);

    }

    /**
     * PID controller for platform in y og x movement
     *
     * @param input The current ball position in y or x direction
     * @param setpoint Desired ball position in y or x direction
     * @return The angle which the platform needs to move in given direction
     */
    double PIDcontroller(double setpoint, double input){
            double error = setpoint - input;

        if(error <-2 || error > 2){
            integral = integral + error*DT;
        }else{
            integral = 0;
        }

        derivative = (error - prevError)/DT;

        double output = (Kp*error) + (Ki*integral) + (Kd*derivative);

        prevError = error;

        return output;
    }

    /**
     * Gets the x output of the ball position
     * @return the x output of the ball position
     */
    public double getOutputX(){
        return this.outputX;
    }

    /**
     * Gets the y output of the ball position
     * @return the y output of the ball position
     */
    public double getOutputY(){
        return this.outputy;
    }

}
