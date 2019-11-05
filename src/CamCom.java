import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 * Gets the ballposition from the python CV program and puts it in a storagebox
 */
public class CamCom extends Thread {
    private SB_ballPos storageBox;
    private Event eventBallStorageBox;

    /**
     * Contructor
     * @param storageBox the ballPos storagebox
     */
    public CamCom(Event eventBallStorageBox, SB_ballPos storageBox){
        this.storageBox = storageBox;
        this.eventBallStorageBox = eventBallStorageBox;
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

                try {
                    // wait conditionally for the correct state
                    eventBallStorageBox.await(Event.EventState.DOWN);
                }   catch (InterruptedException e) {
                }
                //Put data into storagebox
                this.storageBox.set(ballPos);
                eventBallStorageBox.toggle();

                System.out.println("--CamCom--\n"+ballPos.toString());
            }
        }
        catch(Exception ex) {
            ex.printStackTrace();
        }
    }
}
