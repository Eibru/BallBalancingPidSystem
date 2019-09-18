public class BallPos {
    private double xPos;
    private double yPos;

    public BallPos(double x, double y){
        this.xPos = x;
        this.yPos = y;
    }

    public double getX(){
        return this.xPos;
    }

    public double getY(){
        return this.yPos;
    }

    public void set(double x, double y){
        this.xPos = x;
        this.yPos = y;
    }
}
