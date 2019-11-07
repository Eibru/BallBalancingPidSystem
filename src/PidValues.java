public class PidValues {
    private double kp;
    private double ki;
    private double kd;
    private double dt;

    public PidValues(double kp, double ki, double kd, double dt){
        this.kp = kp;
        this.ki = ki;
        this.kd = kd;
        this.dt = dt;
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

    public void setValues(double kp, double ki, double kd, double dt){
        this.kp = kp;
        this.ki = ki;
        this.kd = kd;
        this.dt = dt;
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

    public String toString(){
        return "Kp="+this.kp+"\nKi="+this.ki+"\nKd="+this.kd+"\nDT="+this.dt+"\n";
    }
}
