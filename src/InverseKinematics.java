import java.lang.Math;

public class InverseKinematics extends Thread {
    private SB_platformAngle platformStorage;
    private SB_servoPos servoStorage;

    public InverseKinematics(SB_platformAngle platformStorage, SB_servoPos servoStorage){
        this.platformStorage = platformStorage;
        this.servoStorage = servoStorage;
    }

    public void run(){
        double L = 8.6;
        double r = 7.7;
        while(true){
            double roll = Math.toRadians(platformStorage.getRoll());
            double pitch = Math.toRadians(platformStorage.getPitch());
            double z0 = (Math.sqrt(3)*L/6)*Math.sin(pitch)*Math.cos(roll) + (L/2)*Math.sin(roll);
            double z1 = (Math.sqrt(3)*L/6)*Math.sin(pitch)*Math.cos(roll) - (L/2)*Math.sin(roll);
            double z2 = (-Math.sqrt(3)*L/3)*Math.sin(pitch)*Math.cos(roll);
            double s0 = Math.asin(z0/r);
            double s1 = Math.asin(z1/r);
            double s2 = Math.asin(z2/r);
            this.servoStorage.set(s0,s1,s2);
        }
    }
}
