package TCPNIO;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * @Auther: Chandler
 * @Date: 2022/10/27 - 10 - 27 - 10:50
 * @Description: TCPNIO
 * @version: 1.0
 */
public class Client {

    public static void main(String[] args) throws IOException {
        int portClient = 2555;
        String addrClient = "127.0.0.1";
        String word = "testwroddd";
        byte[] argument = word.getBytes();

        //建立连接
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress(addrClient, portClient));
        socketChannel.configureBlocking(false);
        while (!socketChannel.finishConnect()) ;
        System.out.println(socketChannel.finishConnect());

        //发送数据
        ByteBuffer writeBUf = ByteBuffer.wrap(argument);
        while (writeBUf.hasRemaining()) {
            socketChannel.write(writeBUf);
        }
        //socketChannel.write(writeBUf);
        System.out.println(1112);


        //接收数据
        ByteBuffer readBuf = ByteBuffer.allocate(argument.length+1);
        while(socketChannel.read(readBuf) >= 0);
        System.out.println("接收数据为：" + new String(readBuf.array()));


        socketChannel.close();

    }
}
