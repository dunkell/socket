package TCPNIO;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

/**
 * @Auther: Chandler
 * @Date: 2022/10/27 - 10 - 27 - 10:50
 * @Description: TCPNIO
 * @version: 1.0
 */
public class Server {

    private static final int port = 2555;

    public static void main(String[] args) throws IOException {

        String word = "serverword";
        byte[] argument = word.getBytes();

        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.socket().bind(new InetSocketAddress(port));
        SocketChannel socketChannel;

        while (true) {

            //建立连接
            socketChannel = serverSocketChannel.socket().accept().getChannel();
            socketChannel.configureBlocking(false);

            //接收数据
            System.out.println(11111);
            ByteBuffer readBuf = ByteBuffer.allocate(argument.length);
            readBuf.clear();
            System.out.println(readBuf.position());
            //while ((socketChannel.read(readBuf)) >= 0);
            int totallength = 0;
            int byteRcvd;
            while (totallength < argument.length){

                System.out.println(readBuf.position());
                System.out.println(new String(readBuf.array()));
                if((byteRcvd = socketChannel.read(readBuf)) == -1){
                    throw new RuntimeException("shuchu");
                }
                totallength += byteRcvd;
            }
            System.out.println("接收的数据为：" + new String(readBuf.array()));

            //返回数据
            ByteBuffer writeBuf = ByteBuffer.wrap(argument);
            while(writeBuf.hasRemaining()){
                socketChannel.write(writeBuf);
            }
            System.out.println("响应的数据为：" + new String(writeBuf.array()));

            readBuf.clear();
            writeBuf.clear();
        }
    }
}
