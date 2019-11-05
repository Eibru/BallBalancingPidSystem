import java.lang.Math;

public class InverseKinematics extends Thread {
    private SB_platformAngle platformStorage;
    private SB_servoPos servoStorage;

    // create a shared Event handler class
    private Event eventPlatformAngleStorageBox;
    private Event eventServoStorageBox;


    public InverseKinematics(Event eventPlatformAngleStorageBox, SB_platformAngle platformStorage, Event eventServoStorageBox, SB_servoPos servoStorage){
        this.platformStorage = platformStorage;
        this.servoStorage = servoStorage;
        this.eventPlatformAngleStorageBox = eventPlatformAngleStorageBox;
        this.eventServoStorageBox = eventServoStorageBox;
    }

    public void run(){
        //double L = 8.6;
        //double r = 7.7;
        double L = 12.0;
        double r = 5.1;
        while(true){
            try {
                // wait conditionally for the correct state
                eventPlatformAngleStorageBox.await(Event.EventState.UP);
            }   catch (InterruptedException e) {
            }

            double roll = Math.toRadians(platformStorage.getRoll());
            double pitch = Math.toRadians(platformStorage.getPitch());
            eventPlatformAngleStorageBox.toggle();
            double z0 = ((Math.sqrt(3)*L)/6)*Math.sin(pitch)*Math.cos(roll) + (L/2)*Math.sin(roll);
            double z1 = ((Math.sqrt(3)*L)/6)*Math.sin(pitch)*Math.cos(roll) - (L/2)*Math.sin(roll);
            double z2 = ((-Math.sqrt(3)*L)/3)*Math.sin(pitch)*Math.cos(roll);
            double s0 = 155 - Math.toDegrees(Math.asin(z0/r));
            double s1 = 150 - Math.toDegrees(Math.asin(z1/r));
            double s2 = 150 - Math.toDegrees(Math.asin(z2/r));

            //Debug
            //System.out.println("--Kinematics--\n"+s0+", "+s1+", "+s2+"\n");

            /*if(s0 < 105){
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
            }*/

            if(s0 < 100){
                s0 = 100;
            } else if(s0 > 180){
                s0 = 180;
            }
            if(s1 < 100){
                s1 = 100;
            } else if(s1 > 175){
                s1 = 175;
            }
            if(s2 < 100){
                s2 = 100;
            } else if(s2 > 175){
                s2 = 175;
            }

            try {
                // wait conditionally for the correct state
                eventPlatformAngleStorageBox.await(Event.EventState.DOWN);
            }   catch (InterruptedException e) {
            }
            this.servoStorage.set(s0,s1,s2);

            eventServoStorageBox.toggle();
        }
    }
}
