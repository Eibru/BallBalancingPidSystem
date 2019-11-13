/**
 * Class that stores the three servo angles.
 */
public class ServoPos {
    private double angle1;
    private double angle2;
    private double angle3;

    /**
     * The constructor
     * @param a1 servo1 angle
     * @param a2 servo2 angle
     * @param a3 servo3 angle
     */
    public ServoPos(double a1, double a2, double a3){
        this.angle1 = a1;
        this.angle2 = a2;
        this.angle3 = a3;
    }

    /**
     * Sets the servo1 angle
     * @param angle the angle
     */
    public void setAngle1(double angle){
        this.angle1 = angle;
    }

    /**
     * Sets the servo2 angle
     * @param angle the angle
     */
    public void setAngle2(double angle){
        this.angle2 = angle;
    }

    /**
     * Sets the servo3 angle
     * @param angle the angle
     */
    public void setAngle3(double angle){
        this.angle3 = angle;
    }

    /**
     * Gets the servo1 angle
     * @return the servo1 angle
     */
    public double getAngle1(){
        return this.angle1;
    }

    /**
     * Gets the servo2 angle
     * @return the servo2 angle
     */
    public double getAngle2(){
        return this.angle2;
    }

    /**
     * Gets the servo3 angle
     * @return the servo3 angle
     */
    public double getAngle3(){
        return this.angle3;
    }

    /**
     * @return a string representation of the angles
     */
    public String toString(){
        return this.angle1 + "," + this.angle2 + "," + this.angle3;
    }
}
