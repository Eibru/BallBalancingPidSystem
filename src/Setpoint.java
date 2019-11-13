/**
 * Class for storing x and y coordinates of a setpoint.
 */
public class Setpoint {
    private double setpointX;
    private double setpointY;

    /**
     * Constructor
     * @param x x coordinate
     * @param y y coordinate
     */
    public Setpoint(double x, double y){
        this.setpointX = x;
        this.setpointY = y;
    }

    /**
     * Sets the x coordinate
     * @param x x coordinate
     */
    public void setSetpointX(double x){
        this.setpointX = x;
    }

    /**
     * Sets the y coordinate
     * @param y y coordinate
     */
    public void setSetpointY(double y){
        this.setpointY = y;
    }

    /**
     * Sets the coordinates of the setpoint
     * @param x x coordinate
     * @param y y coordinate
     */
    public void setSetpoint(double x, double y){
        this.setpointX = x;
        this.setpointY = y;
    }

    /**
     * Gets the x coordinate
     * @return x coordinate
     */
    public double getSetpointX(){
        return this.setpointX;
    }

    /**
     * Gets the y coordinate
     * @return y coordinate
     */
    public double getSetpointY(){
        return this.setpointY;
    }

    /**
     * Gets a string representing the object
     * @return a string representing the object
     */
    public String toString(){
        return "X="+this.setpointX+"\nY="+this.setpointY+"\n";
    }
}
