public class PidValues {
    private double kp;
    private double ki;
    private double kd;
    private double dt;
    private double setpointX;
    private double setpointY;

    public PidValues(double kp, double ki, double kd, double dt, double setpointX, double setpointY){
        this.kp = kp;
        this.ki = ki;
        this.kd = kd;
        this.dt = dt;
        this.setpointX = setpointX;
        this.setpointY = setpointY;
    }

    public void setKp(double kp){
        this.kp = kp;
    }

    public void setKi(double ki){
        this.ki = ki;
    }

    public void setKd(double kd){
        this.kd = kd;
    }

    public void setDt(double dt){
        this.dt = dt;
    }

    public void setSetpointX(double s){
        this.setpointX = s;
    }

    public void setSetpointY(double s){
        this.setpointY = s;
    }

    public void setValues(double kp, double ki, double kd, double dt){
        this.kp = kp;
        this.ki = ki;
        this.kd = kd;
        this.dt = dt;
    }

    public void setSetpoints(double x, double y){
        this.setpointX = x;
        this.setpointY = y;
    }

    public double getKp(){
        return this.kp;
    }

    public double getKi(){
        return this.ki;
    }

    public double getKd(){
        return this.kd;
    }

    public double getDt(){
        return this.dt;
    }

    public double getSetpointX(){
        return this.setpointX;
    }

    public double getSetpointY(){
        return this.setpointY;
    }
}
