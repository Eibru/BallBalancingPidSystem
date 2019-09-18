public class PidController extends Thread{
    SB_ballPos storageBoxBall;
    SB_servoPos storageBoxServo;

    public PidController(SB_ballPos storageBoxBall, SB_servoPos storageBoxServo){
        this.storageBoxBall = storageBoxBall;
        this.storageBoxServo = storageBoxServo;
    }

    public void run(){
        System.out.println("penis");

    }
}
