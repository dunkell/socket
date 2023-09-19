package UDPNIO.Client;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

/**
 * @Auther: Chandler
 * @Date: 2022/10/28 - 10 - 28 - 10:51
 * @Description: UDPNIO.Client
 * @version: 1.0
 */
public class UDPChannelEchoClient {

    private static final int port = 2999;
    private static final String word = "clientword";

    public static void main(String[] args) throws IOException {

        InetSocketAddress inetSocketAddress = new InetSocketAddress("127.0.0.1",port);
        ByteBuffer sendBuffer = ByteBuffer.wrap(word.getBytes());
        ByteBuffer receiveBuffer = ByteBuffer.allocate(11);


        DatagramChannel datagramChannel = DatagramChannel.open();

        //发送数据
        datagramChannel.send(sendBuffer,inetSocketAddress);

        //接收数据
        datagramChannel.receive(receiveBuffer);
        System.out.println(new String(receiveBuffer.array()));




    }
}
