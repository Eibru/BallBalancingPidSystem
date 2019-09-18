/**
 * Class for storing x and y position
 */
public class BallPos {
    private double xPos;
    private double yPos;

    /**
     * Constructor
     * @param x x value of the position
     * @param y y value of the position
     */
    public BallPos(double x, double y){
        this.xPos = x;
        this.yPos = y;
    }

    /**
     * Gets the x value of position
     * @return the x value
     */
    public double getX(){
        return this.xPos;
    }

    /**
     * Gets the y value of position
     * @return the y value
     */
    public double getY(){
        return this.yPos;
    }

    /**
     * Sets the x and y values of the position
     * @param x the x value
     * @param y the y value
     */
    public void set(double x, double y){
        this.xPos = x;
        this.yPos = y;
    }

    /**
     * @return a string that represents the object
     */
    public String toString(){
        return "x: " + this.xPos + "\ny: " + this.yPos;
    }
}
