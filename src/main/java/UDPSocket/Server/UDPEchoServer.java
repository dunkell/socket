package UDPSocket.Server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;


/**
 * @Auther: Chandler
 * @Date: 2022/10/21 - 10 - 21 - 11:18
 * @Description: socketserver
 * @version: 1.0
 */
public class UDPEchoServer {

    private static final int ECHOMAX = 255;

    public static void main(String[] args) throws IOException {

//        if(args.length != 1){
//            throw new IllegalArgumentException("Parameter(s): <Port>");
//        }
//
//        int servPort = Integer.parseInt(args[0]);
        int servPort = 2048;

        DatagramSocket socket = new DatagramSocket(servPort);
        DatagramPacket packet = new DatagramPacket(new byte[ECHOMAX],ECHOMAX);

        while (true){
            socket.receive(packet);
            System.out.println(packet);
            System.out.println("Handling client at " + packet.getAddress()
                    + " on port " + packet.getPort());

            socket.send(packet);
            packet.setLength(ECHOMAX);
        }
    }
}
