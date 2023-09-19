package TCPSocket.Client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;

/**
 * @Auther: Chandler
 * @Date: 2022/10/20 - 10 - 20 - 14:48
 * @Description: socketclient
 * @version: 1.0
 */
public class TCPEchoClient {
    public static void main(String[] args) throws IOException, InterruptedException {

        String server = "127.0.0.1";

        String input = "helloworld";
        byte[] data = input.getBytes();

        int  servPort = 1024;

        Socket socket = new Socket(server,servPort);

        //开启十秒暂停，模拟长时间占用服务器
        Thread.sleep(5000);

        System.out.println("Connected to Server...sending echo string");

        InputStream in = socket.getInputStream();
        OutputStream out = socket.getOutputStream();

        out.write(data);

        int totalBytesRcvd = 0;
        int bytesRcvd;
        while(totalBytesRcvd < data.length){
            if ((bytesRcvd = in.read(data,totalBytesRcvd,
                    data.length - totalBytesRcvd)) == -1)
                throw new SocketException("Connection closed prematurely");
            totalBytesRcvd += bytesRcvd;
        }

        System.out.println("Reveived: " + totalBytesRcvd);

        socket.close();
    }
}
