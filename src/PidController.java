/**
 * Reads the data from the ballPos storagebox and generates the new servo angles and puts the values into the servoPos storagebox
 */
public class PidController extends Thread{
    SB_ballPos storageBoxBall;
    SB_servoPos storageBoxServo;

    /**
     * Constructor
     * @param storageBoxBall Storagebox for ball position
     * @param storageBoxServo Storagebox for servo angles
     */
    public PidController(SB_ballPos storageBoxBall, SB_servoPos storageBoxServo){
        this.storageBoxBall = storageBoxBall;
        this.storageBoxServo = storageBoxServo;
    }

    public void run(){
        System.out.println("penis");

    }
}
