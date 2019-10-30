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
        double z = 8;
        while(true){
            double roll = Math.toRadians(platformStorage.getRoll());
            double pitch = Math.toRadians(platformStorage.getPitch());
            double z0 = (Math.sqrt(3)*L/6)*Math.sin(pitch)*Math.cos(roll) + (L/2)*Math.sin(roll);
            double z1 = (Math.sqrt(3)*L/6)*Math.sin(pitch)*Math.cos(roll) - (L/2)*Math.sin(roll);
            double z2 = (-Math.sqrt(3)*L/3)*Math.sin(pitch)*Math.cos(roll);
            /*double s0 = Math.toDegrees(Math.asin(z0/r)) + 105;
            double s1 = Math.toDegrees(Math.asin(z1/r)) + 90;
            double s2 = Math.toDegrees(Math.asin(z2/r)) + 90;*/
            double s0 = 105-Math.toDegrees(Math.asin(z0/r));
            double s1 = 90-Math.toDegrees(Math.asin(z1/r));
            double s2 = 90-Math.toDegrees(Math.asin(z2/r));
            //double s0 = Math.toDegrees(Math.acos( ((z0*z0) - (r*r))/(2*z0))) + 105;
            //double s1 = Math.toDegrees(Math.acos(((z1*z1) - (r*r))/(2*z1))) + 90;
            //double s2 = Math.toDegrees(Math.acos(((z2*z2) - (r*r))/(2*z2))) + 90;

            System.out.print(s0);
            System.out.print(", ");
            System.out.print(s1);
            System.out.print(", ");
            System.out.println(s2);

            if(s0 < 105){
                s0 = 105;
            } else if(s0 > 170){
                s0 = 170;
            }
            if(s1 < 90){
                s1 = 90;
            } else if(s1 > 155){
                s1 = 155;
            }
            if(s2 < 90){
                s2 = 90;
            } else if(s2 > 155){
                s2 = 155;
            }
            this.servoStorage.set(s0,s1,s2);
        }
    }
}
