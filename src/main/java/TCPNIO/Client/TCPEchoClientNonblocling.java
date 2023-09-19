package TCPNIO.Client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * @Auther: Chandler
 * @Date: 2022/10/26 - 10 - 26 - 17:40
 * @Description: TCPNIO.Client
 * @version: 1.0
 */

public class TCPEchoClientNonblocling {

    public static void main(String[] args) throws IOException {
        String server = "127.0.0.1";
        String word = "helloworld";
        byte[] argument = word.getBytes();

        int servPort = 2044;

        //开启信道并设置为不阻塞状态
        SocketChannel clntChan = SocketChannel.open();
        clntChan.configureBlocking(false);

        //启动与服务器的连接并反复轮询，直到完成
        if (!clntChan.connect(new InetSocketAddress(server, servPort))) {
            while (!clntChan.finishConnect()) {
                System.out.print(".");
            }
        }

        ByteBuffer writeBuf = ByteBuffer.wrap(argument);
        ByteBuffer readBuf = ByteBuffer.allocate(argument.length);

        int totalBytesRcvd = 0;//已接收长度
        int bytesRcvd;//下一个接收的比特
        while (totalBytesRcvd < argument.length) {
            //写入writeBuf中，无法保证写入多少，循环写入
            if (writeBuf.hasRemaining()) {
                clntChan.write(writeBuf);
            }
            //socketChannel.read(buf)从socketChannel读取数据到buf，返回读取的字节数，返回-1表示末尾
            if ((bytesRcvd = clntChan.read(readBuf)) == -1) {
                throw new SocketException("Connection closed prematurely");
            }
            totalBytesRcvd += bytesRcvd;
            System.out.print(".");
        }

        System.out.println("Received: " +
                new String(readBuf.array(), 0, totalBytesRcvd));
        clntChan.close();

    }

}

