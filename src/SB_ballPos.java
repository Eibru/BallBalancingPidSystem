public class SB_ballPos {
    BallPos ballPos;
    public SB_ballPos(){
        this.ballPos = new BallPos(0,0);
    }

    public void set(BallPos ballPos){
        this.ballPos = ballPos;
    }

    public void set(double x, double y){
        this.ballPos.set(x,y);
    }

    public BallPos get(){
        return this.ballPos;
    }

    public double getX(){
        return this.ballPos.getX();
    }

    public double getY(){
        return this.ballPos.getY();
    }
}
