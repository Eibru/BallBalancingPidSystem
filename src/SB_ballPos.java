/**
 * Class that acts like a simple storagebox for storing the position of the ball.
 */
public class SB_ballPos {
    BallPos ballPos;

    /**
     * Constructor
     */
    public SB_ballPos(){
        this.ballPos = new BallPos(0,0);
    }

    /**
     * Sets the ball position
     * @param ballPos the ball position object
     */
    public synchronized void set(BallPos ballPos){
        this.ballPos = ballPos;
    }

    /**
     * Sets the ball position
     * @param x the x position of the ball
     * @param y the y position of the ball
     */
    public synchronized void set(double x, double y){
        this.ballPos.set(x,y);
    }

    /**
     * Gets the ball position
     * @return the ball position
     */
    public synchronized BallPos get(){
        return this.ballPos;
    }

    /**
     * Gets the x value of the ball position
     * @return the x value of the ball position
     */
    public synchronized double getX(){
        return this.ballPos.getX();
    }

    /**
     * Gets the y value of the ball position
     * @return the y value of the ball position
     */
    public synchronized double getY(){
        return this.ballPos.getY();
    }
}
