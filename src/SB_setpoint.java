public class SB_setpoint {
    private Setpoint setpoint;
    private boolean changes;

    public SB_setpoint(){
        this.setpoint = new Setpoint(0,0);
        this.changes = true;
    }

    public void setSetpoint(Setpoint setpoint){
        this.setpoint = setpoint;
        this.changes = true;
    }

    public void setSetpoint(double x, double y){
        this.changes = true;
        this.setpoint.setSetpoint(x,y);
    }

    public Setpoint getSetpoint(){
        this.changes = false;
        return this.setpoint;
    }

    public boolean hasChanges(){
        return this.changes;
    }
}
