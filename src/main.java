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

        //Used for testing
        ServoPos sp = new ServoPos(90.0,51.9,22.7);
        servoStorageBox.set(sp);

        camCom.start();
        pidController.start();
        servoCom.start();
    }
}
