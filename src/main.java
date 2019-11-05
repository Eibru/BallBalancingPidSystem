/**
 * Main class of the application
 * Creates and starts the threads
 */
public class main {
    public static void main(String args[]){
        //Setup event handlers
        Event eventBallStorageBox = new Event();
        Event eventPlatformAngleStorageBox = new Event();
        Event eventServoStorageBox = new Event();

        //Setup storage boxes
        SB_ballPos ballStorageBox = new SB_ballPos();
        SB_platformAngle platformAngleStorageBox = new SB_platformAngle();
        SB_servoPos servoStorageBox = new SB_servoPos();

        //Setup threads
        CamCom camCom = new CamCom(eventBallStorageBox, ballStorageBox);
        PidController pidController = new PidController(eventBallStorageBox, ballStorageBox, eventPlatformAngleStorageBox, platformAngleStorageBox);
        InverseKinematics inverseKinematics = new InverseKinematics(eventPlatformAngleStorageBox, platformAngleStorageBox, eventServoStorageBox, servoStorageBox);
        ServoCom servoCom = new ServoCom(eventServoStorageBox, servoStorageBox);

        //Set start angle
        platformAngleStorageBox.setAngle(0,0);
        eventPlatformAngleStorageBox.toggle();

        //Start threads
        camCom.start();
        pidController.start();
        inverseKinematics.start();
        servoCom.start();
    }
}
