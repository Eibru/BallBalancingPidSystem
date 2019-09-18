/**
 * Reads servo position and sends it to the python servo controller over TCP
 */
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.*;

public class ServoCom extends Thread {
    private SB_servoPos storageBox;

    /**
     * Constructor
     * @param storageBox the servo storagebox
     */
    public ServoCom(SB_servoPos storageBox){
        this.storageBox = storageBox;
    }

    public void run(){

    }
}
