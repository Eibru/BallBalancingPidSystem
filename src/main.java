/**
 * Main class of the application
 * Creates and starts the threads
 */
public class main {
    public static void main(String args[]){
        SB_ballPos ballStorageBox = new SB_ballPos();
        SB_platformAngle platformAngleStorageBox = new SB_platformAngle();
        SB_servoPos servoStorageBox = new SB_servoPos();

        CamCom camCom = new CamCom(ballStorageBox);
        PidController pidController = new PidController(ballStorageBox, platformAngleStorageBox);
        InverseKinematics inverseKinematics = new InverseKinematics(platformAngleStorageBox, servoStorageBox);
        ServoCom servoCom = new ServoCom(servoStorageBox);

        camCom.start();
        pidController.start();
        inverseKinematics.start();
        servoCom.start();
    }
}
