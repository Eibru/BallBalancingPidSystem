/**
 * Class that acts like a simple storagebox for storing the angles of the three servos
 */
public class SB_servoPos {
    private ServoPos servoPos;

    /**
     * Constructor
     */
    public SB_servoPos(){
        this.servoPos = new ServoPos(0,0,0);
    }

    /**
     * Sets the servos angles
     * @param servoPos the servo positions
     */
    public void setServoPositions(ServoPos servoPos){
        this.servoPos = servoPos;
    }

    /**
     * Sets the servos angles
     * @param a1 servo1 angle
     * @param a2 servo2 angle
     * @param a3 servo3 angle
     */
    public void setServoPositions(double a1, double a2, double a3){
        this.servoPos.setAngle1(a1);
        this.servoPos.setAngle2(a2);
        this.servoPos.setAngle3(a3);
    }

    /**
     * Gets the servo angles
     * @return
     */
    public ServoPos getServoPos(){
        return this.servoPos;
    }
}
