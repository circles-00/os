import java.io.IOException;
import java.net.*;

public class UDPClient extends Thread {
    private String serverName;
    private int serverPort;

    private DatagramSocket socket;
    private InetAddress inetAddress;
    private String message;
    private byte [] buf;

    public UDPClient(String serverName, int serverPort, String message){
        this.serverName = serverName;
        this.serverPort = serverPort;
        this.message = message;

        try{
            this.socket = new DatagramSocket();
            this.inetAddress = InetAddress.getByName(serverName);
        } catch (SocketException | UnknownHostException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void run() {
        buf = message.getBytes();
        DatagramPacket packet = new DatagramPacket(buf,buf.length,this.inetAddress,this.serverPort);
        try {
            socket.send(packet);
            packet = new DatagramPacket(buf,buf.length,inetAddress,serverPort);
            socket.receive(packet);
            System.out.println(new String(packet.getData(),0,packet.getLength()));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        UDPClient client = new UDPClient("194.149.135.49",9753,"196011");
        client.start();
    }

}
