import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * Reads servo position and sends it to the python servo controller over TCP
 */

public class ServoCom extends Thread {
    private SB_servoPos storageBox;

    // create a shared Event handler class
    private Event eventServoStorageBox;

    /**
     * Constructor
     * @param storageBox the servo storagebox
     */
    public ServoCom(Event eventServoStorageBox, SB_servoPos storageBox){
        this.storageBox = storageBox;
        this.eventServoStorageBox = eventServoStorageBox;
    }

    /**
     * Sends the servo positions to the python udp server
     */
    public void run(){
        long endTime = 0;
        long startTime = 0;
        long cycleTime = 0;
        try {
            DatagramSocket socket = new DatagramSocket();
            InetAddress address = InetAddress.getByName("localhost");

            while (true) {
                try {
                    // wait conditionally for the correct state
                    eventServoStorageBox.await(Event.EventState.UP);
                }   catch (InterruptedException e) {
                }
                byte[] buf = (this.storageBox.get().toString() + "\n").getBytes();
                eventServoStorageBox.toggle();
                DatagramPacket packet = new DatagramPacket(buf, buf.length, address, 5556);
                socket.send(packet);

                //Test cycletime
                /*endTime = System.currentTimeMillis();
                cycleTime = endTime - startTime;
                System.out.println(cycleTime);
                startTime = System.currentTimeMillis();*/
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
