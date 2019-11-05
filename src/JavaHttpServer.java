import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.*;
import java.lang.reflect.Field;
import java.net.InetSocketAddress;

public class JavaHttpServer extends Thread{
    public JavaHttpServer(SB_pidValues storageBoxPid) {
        System.out.println("Http server started");
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
            server.createContext("/test", new EventHandler(storageBoxPid));
            server.setExecutor(null);
            server.start();
        } catch (Exception ex){
           ex.printStackTrace();
        }
    }

    static class EventHandler implements HttpHandler{
        private SB_pidValues storageBoxPid;

        public EventHandler(SB_pidValues storageBoxPid){
            this.storageBoxPid = storageBoxPid;
        }

        @Override
        public void handle(HttpExchange t){
            try {
                if(t.getRequestMethod().equalsIgnoreCase("POST")){
                    InputStreamReader reader = new InputStreamReader(t.getRequestBody());
                    int data;
                    StringBuilder sb = new StringBuilder();
                    while((data = reader.read()) != -1){
                        sb.append((char)data);
                    }
                    reader.close();
                    t.sendResponseHeaders(200, 0);
                    String[] s = sb.toString().split(":");

                    if(s[0].equalsIgnoreCase("PID")){

                        s = s[1].split("\\[")[1].split("\\]")[0].split(",");

                        double kp = Double.parseDouble(s[0].split("Kp=")[1]);
                        double ki = Double.parseDouble(s[1].split("Ki=")[1]);
                        double kd = Double.parseDouble(s[2].split("Kd=")[1]);
                        double dt = Double.parseDouble(s[3].split("DT=")[1]);
                        this.storageBoxPid.setPidValues(kp,ki,kd,dt);

                        //Debug
                        System.out.println(sb);
                    } else if(s[0] == "setpoints"){
                        s = s[1].split("\\[")[1].split("\\]")[0].split(",");

                        double setpointX = Double.parseDouble(s[0].split("x=")[1]);
                        double setpointY = Double.parseDouble(s[0].split("y=")[1]);
                        this.storageBoxPid.setSetpoints(setpointX,setpointY);
                    }
                } else {
                    File file = new File(".\\http\\index.html");
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));

                    String line;
                    String response = "";
                    while((line = bufferedReader.readLine()) != null){
                        response += line;
                    }

                    t.sendResponseHeaders(200, response.length());
                    OutputStream os = t.getResponseBody();
                    os.write(response.getBytes());
                    os.close();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

    }
}
