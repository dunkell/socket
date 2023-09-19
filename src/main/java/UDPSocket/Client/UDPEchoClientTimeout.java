package UDPSocket.Client;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.*;

/**
 * @Auther: Chandler
 * @Date: 2022/10/21 - 10 - 21 - 11:00
 * @Description: socketclient
 * @version: 1.0
 */
public class UDPEchoClientTimeout {

    private static final int TIMEOUT = 3000;
    private static final int MAXTRIES = 5;

    public static void main(String[] args) throws IOException {

//        if ((args.length < 2) || (args.length > 3)) {
//            throw new IllegalArgumentException("Parameter(s): <Server> <Word> [<Port>]");
//        }

        //InetAddress serverAdderss = InetAddress.getByName(args[0]);
        InetAddress serverAdderss = InetAddress.getLocalHost();
        //广播
        //InetAddress serverAdderss = InetAddress.getByName("255.255.255.255");



        //byte[] bytesTOSend = args[1].getBytes();
        String word = "helloworld";
        byte[] bytesTOSend = word.getBytes();


        //int servPort = (args.length == 3) ? Integer.parseInt(args[2]) : 7;

        int servPort = 2048;

        DatagramSocket socket = new DatagramSocket();

        socket.setSoTimeout(TIMEOUT);

        DatagramPacket sendPacket = new DatagramPacket(bytesTOSend, bytesTOSend.length, serverAdderss, servPort);

        DatagramPacket receivePacket = new DatagramPacket(new byte[bytesTOSend.length], bytesTOSend.length);

        int tries = 0;

        boolean receiveResponse = false;

        do {
            socket.send(sendPacket);
            try {
                socket.receive(receivePacket);

                if (!receivePacket.getAddress().equals((serverAdderss))) {
                    throw new IOException("Received packet from an unknown source");
                }
                receiveResponse = true;

            } catch (InterruptedIOException e) {
                tries += 1;
                System.out.println("Time out, " + (MAXTRIES - tries) + " more tries...");
            }
        } while ((!receiveResponse) && (tries < MAXTRIES));
        if (receiveResponse) {
            System.out.println("Received: " + new String(receivePacket.getData()));
        } else {
            System.out.println("No response -- giving up");
        }
        socket.close();

    }
}
