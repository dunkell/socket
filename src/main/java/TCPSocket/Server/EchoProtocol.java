package TCPSocket.Server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @Auther: Chandler
 * @Date: 2022/10/26 - 10 - 26 - 11:47
 * @Description: socketserver
 * @version: 1.0
 */
public class EchoProtocol implements Runnable{

    private static final int BUFSIZE = 32;
    private static final String TIMELIMIT = "10000";
    private static final String TIMELIMITPROP = "Timelimit";
    private static int timelimit;
    private Socket clntSock;
    private Logger logger;

    public EchoProtocol(Socket clntSock,Logger logger){
        this.clntSock = clntSock;
        this.logger = logger;

        timelimit = Integer.parseInt(System.getProperty(TIMELIMITPROP,TIMELIMIT));
    }

    public static void handleEchoClient(Socket clntSock, Logger logger){
        try{
            InputStream in = clntSock.getInputStream();
            OutputStream out = clntSock.getOutputStream();

            int recvMsgSize;
            int totalBytesEchoed = 0;

            byte[] echoBuffer = new byte[BUFSIZE];
            long endTime = System.currentTimeMillis() + timelimit;
            int timeBoundMillis = timelimit; //剩余时间

            /*
            setSoTimeout设置read超时时间
            超时会抛出异常java.net.SocketTimeoutException
             */
            clntSock.setSoTimeout(timeBoundMillis);
            while((timeBoundMillis > 0) &&
                    ((recvMsgSize = in.read(echoBuffer))!=-1)){
                out.write(echoBuffer,0,recvMsgSize);
                totalBytesEchoed +=recvMsgSize;
                timeBoundMillis = (int)(endTime - System.currentTimeMillis());
                clntSock.setSoTimeout(timeBoundMillis);
            }

            logger.info("Client " + clntSock.getRemoteSocketAddress()
            + ", echoed" + totalBytesEchoed + " bytes.");

        }catch (IOException e){
            logger.log(Level.WARNING,"Exception in echo protocol",e);
        }finally {
            try{
                clntSock.close();
            }catch (IOException ex){
            }
        }
    }

    public void run(){
        handleEchoClient(clntSock,logger);
    }

}
