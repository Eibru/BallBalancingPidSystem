import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * Gets the ballposition from the python CV program and puts it in a storagebox
 */
public class CamCom extends Thread {
    private SB_ballPos storageBox;

    /**
     * Contructor
     * @param storageBox the ballPos storagebox
     */
    public CamCom(SB_ballPos storageBox){
        this.storageBox = storageBox;
    }

    /**
     * Starts communication with the python TCP server
     * Reads the ball position and saves it to the storagebox
     */
    public void run(){
        try {
            Socket socket = new Socket("localhost",5555);
            BufferedReader inputStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            while(true){
                System.out.println(inputStream.readLine());
            }
        }
        catch(Exception ex) {
            ex.printStackTrace();
        }
    }
}
