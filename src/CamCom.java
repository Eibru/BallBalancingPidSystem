import java.net.DatagramPacket;
import java.net.DatagramSocket;

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
            DatagramSocket socket = new DatagramSocket(5555);
            byte[] buf = new byte[256];

            while(true){
                //Get message from udp server
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                socket.receive(packet);
                packet = new DatagramPacket(buf, buf.length, packet.getAddress(), packet.getPort());
                String data = new String(packet.getData(), 0, packet.getLength());

                //Extract the data
                String[] pos = data.split("\n")[0].split(",");
                BallPos ballPos = new BallPos(Double.parseDouble(pos[0]), Double.parseDouble(pos[1]));

                //Put data into storagebox
                this.storageBox.set(ballPos);

                //System.out.println(ballPos.toString());
            }
        }
        catch(Exception ex) {
            ex.printStackTrace();
        }
    }
}
