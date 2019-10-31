/**
 * Main class of the application
 * Creates and starts the threads
 */
public class main {
    public static void main(String args[]){
        // create a shared Event handler class
        Event eventBallStorageBox = new Event();
        Event eventPlatformAngleStorageBox = new Event();
        Event eventServoStorageBox = new Event();

        SB_ballPos ballStorageBox = new SB_ballPos();
        SB_platformAngle platformAngleStorageBox = new SB_platformAngle();
        SB_servoPos servoStorageBox = new SB_servoPos();

        CamCom camCom = new CamCom(eventBallStorageBox, ballStorageBox);
        PidController pidController = new PidController(eventBallStorageBox, ballStorageBox, eventPlatformAngleStorageBox, platformAngleStorageBox);
        InverseKinematics inverseKinematics = new InverseKinematics(eventPlatformAngleStorageBox, platformAngleStorageBox, eventServoStorageBox, servoStorageBox);
        ServoCom servoCom = new ServoCom(eventServoStorageBox, servoStorageBox);

        camCom.start();
        pidController.start();
        inverseKinematics.start();
        servoCom.start();
    }
}
