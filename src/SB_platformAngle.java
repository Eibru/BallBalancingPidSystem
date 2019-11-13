/**
 * Class that acts like a simple storagebox for storing the angle of the platform.
 */
public class SB_platformAngle {
    private double pitch;
    private double roll;

    /**
     * Set pitch and roll angles
     * @param pitch pitch angle
     * @param roll roll angle
     */
    public synchronized void setAngle(double pitch, double roll){
        this.pitch = pitch;
        this.roll = roll;
    }

    /**
     * Get pitch angle
     * @return the pitch angle
     */
    public synchronized double getPitch(){
        return this.pitch;
    }

    /**
     * Get roll angle
     * @return the roll angle
     */
    public synchronized double getRoll(){
        return this.roll;
    }
}
