import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class WebCom extends Thread{
    private SB_setpoint sb_setpoint;
    private SB_pidValues sb_pidValues;

    public WebCom(SB_setpoint sb_setpoint, SB_pidValues sb_pidValues){
        this.sb_setpoint = sb_setpoint;
        this.sb_pidValues = sb_pidValues;
    }

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

                if(data.indexOf("pid") != -1){
                    data = data.split(":")[1];
                    String[] s = data.split("\n")[0].split(",");
                    PidValues pidValues = new PidValues(Double.parseDouble(s[0]), Double.parseDouble(s[1]), Double.parseDouble(s[2]), Double.parseDouble(s[3]));
                    this.sb_pidValues.setPidValues(pidValues);
                } else if(data.indexOf("setpoint") != -1){
                    data = data.split(":")[1];
                    String[] s = data.split("\n")[0].split(",");
                    Setpoint setpoint = new Setpoint(Double.parseDouble(s[0]), Double.parseDouble(s[1]));
                    this.sb_setpoint.setSetpoint(setpoint);
                }
            }
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
