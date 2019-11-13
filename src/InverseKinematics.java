import java.lang.Math;

/**
 * Thread that handles the inverse kinematics of the platform
 * Reads the pitch and roll angles generated from the pid controller and generates the servo angles which would achieve the given pitch and roll.
 */
public class InverseKinematics extends Thread {
    //Storage boxes
    private SB_platformAngle platformStorage;
    private SB_servoPos servoStorage;

    //Events
    private Event eventPlatformAngleStorageBox;
    private Event eventServoStorageBox;

    /**
     * Constructor
     * @param eventPlatformAngleStorageBox event for the platformAngle
     * @param platformStorage storage box to store platform angle
     * @param eventServoStorageBox event for servopos storage box
     * @param servoStorage servo storage box
     */
    public InverseKinematics(Event eventPlatformAngleStorageBox, SB_platformAngle platformStorage, Event eventServoStorageBox, SB_servoPos servoStorage){
        this.platformStorage = platformStorage;
        this.servoStorage = servoStorage;
        this.eventPlatformAngleStorageBox = eventPlatformAngleStorageBox;
        this.eventServoStorageBox = eventServoStorageBox;
    }

    /**
     * Reads the pitch and roll angles from the platform storage box, calculates the servo-angle needed to achieve the given pitch and roll. Puts the result into the servopos storage box
     */
    public void run(){
        double L = 12.0; //Distance between connection points
        double r = 5.1; //servo-arm length

        while(true){
            try {
                //Wait conditionally for the correct state
                this.eventPlatformAngleStorageBox.await(Event.EventState.UP);
            }   catch (InterruptedException e) {}

            double roll = Math.toRadians(platformStorage.getRoll());
            double pitch = Math.toRadians(platformStorage.getPitch());

            //Toggle the event state
            this.eventPlatformAngleStorageBox.toggle();

            double z0 = ((Math.sqrt(3)*L)/6)*Math.sin(pitch)*Math.cos(roll) + (L/2)*Math.sin(roll);
            double z1 = ((Math.sqrt(3)*L)/6)*Math.sin(pitch)*Math.cos(roll) - (L/2)*Math.sin(roll);
            double z2 = ((-Math.sqrt(3)*L)/3)*Math.sin(pitch)*Math.cos(roll);
            double s0 = 155 - Math.toDegrees(Math.asin(z0/r));
            double s1 = 150 - Math.toDegrees(Math.asin(z1/r));
            double s2 = 150 - Math.toDegrees(Math.asin(z2/r));

            s0 = this.checkServoValue(s0);
            s1 = this.checkServoValue(s1);
            s2 = this.checkServoValue(s2);

            try {
                //Wait conditionally for the correct state
                this.eventPlatformAngleStorageBox.await(Event.EventState.DOWN);
            }   catch (InterruptedException e) {}

            //Set servo values
            this.servoStorage.set(s0,s1,s2);

            //Toggle the event state
            this.eventServoStorageBox.toggle();

            //Debug
            //System.out.println("--Kinematics--\n"+s0+", "+s1+", "+s2+"\n");
        }
    }

    /**
     * Checks the value of the servo angle and makes sure it does not exceed the lower or upper bounds
     * @param s servo angle
     * @return servo angle
     */
    private double checkServoValue(double s){
        double newS = s;
        if(s < 100){
            newS = 100;
        } else if(s > 175){
            newS = 175;
        }
        return newS;
    }
}
