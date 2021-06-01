import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class TCPClient extends Thread {
    private String serverAddress;
    private int serverPort;
    private Socket socket;

    public TCPClient(String serverAddress, int serverPort) throws IOException {
        this.serverAddress = serverAddress;
        this.serverPort = serverPort;
        this.socket = new Socket(serverAddress, serverPort);
    }

    @Override
    public void run() {
        this.send_data();
        this.receive_data();
    }

    private void send_data() {
        new Thread() {
            @Override
            public void run() {
                Scanner scanner = null;
                PrintWriter writer = null;
                try {
                    writer = new PrintWriter(socket.getOutputStream());
                    scanner = new Scanner(System.in);

                    while (true) {
                        String line = scanner.nextLine();
                        writer.println(line);
                        writer.flush();
                        System.out.println();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private void receive_data() {
        new Thread() {
            @Override
            public void run() {
                try {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    while (true)
                        System.out.println(reader.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    public static void main(String[] args) {
        try {
            TCPClient client = new TCPClient("194.149.135.49", 9753);
            client.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

