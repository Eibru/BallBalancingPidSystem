/**
 * Reads servo position and sends it to the python servo controller over TCP
 */
public class ServoCom extends Thread {
    private SB_servoPos storageBox;

    public ServoCom(SB_servoPos storageBox){
        this.storageBox = storageBox;
    }

    public void run(){

    }
}
