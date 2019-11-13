/**
 * Class that stores pid values.
 */
public class PidValues {
    private double kp;
    private double ki;
    private double kd;
    private double dt;

    /**
     * Constructor
     * @param kp kp
     * @param ki ki
     * @param kd kd
     * @param dt dt
     */
    public PidValues(double kp, double ki, double kd, double dt){
        this.kp = kp;
        this.ki = ki;
        this.kd = kd;
        this.dt = dt;
    }

    /**
     * Sets kp
     * @param kp kp
     */
    public void setKp(double kp){
        this.kp = kp;
    }

    /**
     * Sets ki
     * @param ki ki
     */
    public void setKi(double ki){
        this.ki = ki;
    }

    /**
     * Sets kd
     * @param kd kd
     */
    public void setKd(double kd){
        this.kd = kd;
    }

    /**
     * Sets dt
     * @param dt dt
     */
    public void setDt(double dt){
        this.dt = dt;
    }

    /**
     * Sets pid values
     * @param kp kp
     * @param ki ki
     * @param kd kd
     * @param dt dt
     */
    public void setValues(double kp, double ki, double kd, double dt){
        this.kp = kp;
        this.ki = ki;
        this.kd = kd;
        this.dt = dt;
    }

    /**
     * Gets kp
     * @return kp
     */
    public double getKp(){
        return this.kp;
    }

    /**
     * Gets ki
     * @return ki
     */
    public double getKi(){
        return this.ki;
    }

    /**
     * Gets kd
     * @return kd
     */
    public double getKd(){
        return this.kd;
    }

    /**
     * Gets dt
     * @return dt
     */
    public double getDt(){
        return this.dt;
    }

    /**
     * Gets a string representing the object
     * @return a string representing the object
     */
    public String toString(){
        return "Kp="+this.kp+"\nKi="+this.ki+"\nKd="+this.kd+"\nDT="+this.dt+"\n";
    }
}
