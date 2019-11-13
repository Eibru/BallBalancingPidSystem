import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 * Thread that gets the pid values and the setpoint from the python web server.
 */
public class WebCom extends Thread{
    private SB_setpoint sb_setpoint;
    private SB_pidValues sb_pidValues;

    /**
     * Constructor
     * @param sb_setpoint setpoint storagebox
     * @param sb_pidValues pidvalues storagebox
     */
    public WebCom(SB_setpoint sb_setpoint, SB_pidValues sb_pidValues){
        this.sb_setpoint = sb_setpoint;
        this.sb_pidValues = sb_pidValues;
    }

    /**
     * Communicates with the web server using UDP
     * Reads pid values and setpoint and writes the values into the storageboxes
     */
    public void run(){
        try{
            DatagramSocket socket = new DatagramSocket(5557);
            byte[] buf = new byte[256];
            while(true){
                //Get message from udp server
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                socket.receive(packet);
                packet = new DatagramPacket(buf, buf.length, packet.getAddress(), packet.getPort());
                String data = new String(packet.getData(), 0, packet.getLength());
                try {
                    if (data.toLowerCase().contains("pid")) {
                        data = data.split(":")[1];
                        String[] s = data.split("\n")[0].split(",");
                        PidValues pidValues = new PidValues(Double.parseDouble(s[0]), Double.parseDouble(s[1]), Double.parseDouble(s[2]), Double.parseDouble(s[3]));
                        this.sb_pidValues.setPidValues(pidValues);
                    } else if (data.toLowerCase().contains("setpoint")) {
                        data = data.split(":")[1];
                        String[] s = data.split("\n")[0].split(",");
                        Setpoint setpoint = new Setpoint(Double.parseDouble(s[0]), Double.parseDouble(s[1]));
                        this.sb_setpoint.setSetpoint(setpoint);
                    }
                } catch (Exception ex){
                    //Data from udp is creating the error
                    ex.printStackTrace();
                }
            }
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
