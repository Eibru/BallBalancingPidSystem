/**
 * Main class of the application
 * Creates and starts the threads
 */
public class main {
    public static void main(String args[]){
        SB_servoPos servoStorageBox = new SB_servoPos();
        SB_ballPos ballStorageBox = new SB_ballPos();

        CamCom camCom = new CamCom(ballStorageBox);
        PidController pidController = new PidController(ballStorageBox, servoStorageBox);
        ServoCom servoCom = new ServoCom(servoStorageBox);

        camCom.run();
        pidController.run();
        servoCom.run();
    }
}
