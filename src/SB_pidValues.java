public class SB_pidValues {
    private PidValues pidValues;
    private boolean changes;

    public SB_pidValues(){
        this.pidValues = new PidValues(0,0,0,0/*,0,0*/);
        this.changes = true;
    }

    public void setPidValues(PidValues pidValues){
        this.pidValues = pidValues;
        this.changes = true;
    }

    public void setPidValues(double kp, double ki, double kd, double dt){
        this.pidValues.setKp(kp);
        this.pidValues.setKi(ki);
        this.pidValues.setKd(kd);
        this.pidValues.setDt(dt);
        this.changes = true;
    }

    public PidValues getPidValues(){
        this.changes = false;
        return this.pidValues;
    }

    public boolean hasChanges(){
        return this.changes;
    }
}