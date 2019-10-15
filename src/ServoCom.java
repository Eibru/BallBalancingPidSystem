import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * Reads servo position and sends it to the python servo controller over TCP
 */

public class ServoCom extends Thread {
    private SB_servoPos storageBox;

    /**
     * Constructor
     * @param storageBox the servo storagebox
     */
    public ServoCom(SB_servoPos storageBox){
        this.storageBox = storageBox;
    }

    /**
     * Sends the servo positions to the python udp server
     */
    public void run(){
        try {
            DatagramSocket socket = new DatagramSocket();
            InetAddress address = InetAddress.getByName("localhost");

            while (true) {
                byte[] buf = (this.storageBox.get().toString() + "\n").getBytes();
                DatagramPacket packet = new DatagramPacket(buf, buf.length, address, 5556);
                socket.send(packet);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
