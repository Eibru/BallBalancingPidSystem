/**
 * Class that acts like a simple storagebox for storing the setpoints
 */
public class SB_setpoint {
    private Setpoint setpoint;
    private boolean changes;

    /**
     * Constructor
     */
    public SB_setpoint(){
        this.setpoint = new Setpoint(0,0);
        this.changes = true;
    }

    /**
     * Sets the setpoints
     * @param setpoint setpoint
     */
    public void setSetpoint(Setpoint setpoint){
        this.setpoint = setpoint;
        this.changes = true;
    }

    /**
     * Sets the setpoints
     * @param x x
     * @param y y
     */
    public void setSetpoint(double x, double y){
        this.changes = true;
        this.setpoint.setSetpoint(x,y);
    }

    /**
     * Gets the setpoint
     * @return setpoint
     */
    public Setpoint getSetpoint(){
        this.changes = false;
        return this.setpoint;
    }

    /**
     * Used to see if the object has had any changes since last read
     * @return changes
     */
    public boolean hasChanges(){
        return this.changes;
    }
}
