public class Setpoint {
    private double setpointX;
    private double setpointY;

    public Setpoint(double x, double y){
        this.setpointX = x;
        this.setpointY = y;
    }

    public void setSetpointX(double x){
        this.setpointX = x;
    }

    public void setSetpointY(double y){
        this.setpointY = y;
    }

    public void setSetpoint(double x, double y){
        this.setpointX = x;
        this.setpointY = y;
    }

    public double getSetpointX(){
        return this.setpointX;
    }

    public double getSetpointY(){
        return this.setpointY;
    }

    public String toString(){
        return "X="+this.setpointX+"\nY="+this.setpointY+"\n";
    }
}
