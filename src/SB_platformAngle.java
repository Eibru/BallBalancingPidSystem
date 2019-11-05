public class SB_platformAngle {
    private double pitch;
    private double roll;

    public synchronized void setAngle(double pitch, double roll){
        this.pitch = pitch;
        this.roll = roll;
    }

    public synchronized double getPitch(){
        return this.pitch;
    }

    public synchronized double getRoll(){
        return this.roll;
    }
}
