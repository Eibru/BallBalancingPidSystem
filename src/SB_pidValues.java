/**
 * Class that acts like a simple storagebox for storing pid values
 */
public class SB_pidValues {
    private PidValues pidValues;
    private boolean changes;

    /**
     * Constructor
     */
    public SB_pidValues(){
        this.pidValues = new PidValues(0,0,0,0/*,0,0*/);
        this.changes = true;
    }

    /**
     * Sets the pid values
     * @param pidValues pidValues
     */
    public void setPidValues(PidValues pidValues){
        this.pidValues = pidValues;
        this.changes = true;
    }

    /**
     * Sets the pid values
     * @param kp kp
     * @param ki ki
     * @param kd kd
     * @param dt dt
     */
    public void setPidValues(double kp, double ki, double kd, double dt){
        this.pidValues.setKp(kp);
        this.pidValues.setKi(ki);
        this.pidValues.setKd(kd);
        this.pidValues.setDt(dt);
        this.changes = true;
    }

    /**
     * Gets the pid values
     * @return pidValues
     */
    public PidValues getPidValues(){
        this.changes = false;
        return this.pidValues;
    }

    /**
     * Used to see if the object has had any changes since last read
     * @return changes
     */
    public boolean hasChanges(){
        return this.changes;
    }
}