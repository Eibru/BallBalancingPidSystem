/**
 * Reads the data from the ballPos storagebox and generates the new servo angles and puts the values into the servoPos storagebox
 */

import java.lang.Math;


public class PidController extends Thread{
    SB_ballPos storageBoxBall;
    SB_servoPos storageBoxServo;

    private double sommeErreurX;
    private double sommeErreurY;
    private double prevBallPosX ;
    private double prevBallPosY;
    private double servoPos;
    private double errorX;
    private double errorY;
    private double integral;
    private double prevError;



    /**
     * Constructor
     * @param storageBoxBall Storagebox for ball position
     * @param storageBoxServo Storagebox for servo angles
     */
    public PidController(SB_ballPos storageBoxBall, SB_servoPos storageBoxServo){
        this.storageBoxBall = storageBoxBall;
        this.storageBoxServo = storageBoxServo;
         double sommeErreurX = 0;
        double sommeErreurY = 0;
         double prevBallPosX = 0;
         double prevBallPosY = 0;
         double errorX = 0;
         double errorY = 0;
         double integral = 0;
         double prevError = 0;

    }

    public void run(){


    }

    private double pid(double posX, double posY, double setpoint){
         double ballPosX = posX;
         double ballPosY = posY;

            int Kp = 1;
            int Ki = 0;
            int Kd = 0;
            int DT = 0;

            errorX = setpoint - posX;
            errorY = setpoint - posY;
            double totalError = errorX + errorY;

            if (totalError < -2 || totalError > 2) {
                integral = integral + totalError * DT;
            } else {
                integral = 0;
            }


            double derivative = (totalError - prevError) / DT;

            double servoPos = Kp * totalError + Ki * integral + Kd * derivative;


            prevError = totalError;

            prevBallPosX = ballPosX;
            prevBallPosY = ballPosY;


        return servoPos;
    }
}
