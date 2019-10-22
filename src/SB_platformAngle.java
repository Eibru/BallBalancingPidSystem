public class SB_platformAngle {
    private double pitch;
    private double roll;

    public void setAngle(double pitch, double roll){
        this.pitch = pitch;
        this.roll = roll;
    }

    public double getPitch(){
        return this.pitch;
    }

    public double getRoll(){
        return this.roll;
    }
}
