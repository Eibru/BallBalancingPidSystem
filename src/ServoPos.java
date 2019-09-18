public class ServoPos {
    private double angle1;
    private double angle2;
    private double angle3;

    public ServoPos(double a1, double a2, double a3){
        this.angle1 = a1;
        this.angle2 = a2;
        this.angle3 = a3;
    }

    public void setAngle1(double angle){
        this.angle1 = angle;
    }

    public void setAngle2(double angle){
        this.angle2 = angle;
    }

    public void setAngle3(double angle){
        this.angle3 = angle;
    }

    public double getAngle1(){
        return this.angle1;
    }

    public double getAngle2(){
        return this.angle2;
    }

    public double getAngle3(){
        return this.angle3;
    }
}
