/**
 * Class that stores pid values.
 */
public class PidValues {
    private double kp;
    private double ki;
    private double kd;
    private double dt;
    private double filter_alpha;
    private int filter_iterations;

    /**
     * Constructor
     * @param kp kp
     * @param ki ki
     * @param kd kd
     * @param dt dt
     * @param filter_alpha filter_alpha
     * @param filter_iterations filter_iterations
     */
    public PidValues(double kp, double ki, double kd, double dt, double filter_alpha, int filter_iterations){
        this.kp = kp;
        this.ki = ki;
        this.kd = kd;
        this.dt = dt;
        this.filter_alpha = filter_alpha;
        this.filter_iterations = filter_iterations;
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
     * Sets filter_alpha
     * @param filter_alpha filter_alpha
     */
    public void setFilter_alpha(double filter_alpha){
        this.filter_alpha = filter_alpha;
    }

    /**
     * Sets filter_iterations
     * @param filter_iterations filter_iterations
     */
    public void setFilter_iterations(int filter_iterations){
        this.filter_iterations = filter_iterations;
    }

    /**
     * Sets pid values
     * @param kp kp
     * @param ki ki
     * @param kd kd
     * @param dt dt
     */
    public void setValues(double kp, double ki, double kd, double dt, double filter_alpha, int filter_iterations){
        this.kp = kp;
        this.ki = ki;
        this.kd = kd;
        this.dt = dt;
        this.filter_alpha = filter_alpha;
        this.filter_iterations = filter_iterations;
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
     * Gets filter_alpha
     * @return filter_alpha
     */
    public double getFilter_alpha(){
        return this.filter_alpha;
    }

    /**
     * Gets filter_iterations
     * @return filter_iterations
     */
    public int getFilter_iterations(){
        return this.filter_iterations;
    }

    /**
     * Gets a string representing the object
     * @return a string representing the object
     */
    public String toString(){
        return "Kp="+this.kp+"\nKi="+this.ki+"\nKd="+this.kd+"\nDT="+this.dt+"\nFilter alpha="+this.filter_alpha+"\nFilter iterations="+this.filter_iterations+"\n";
    }
}
