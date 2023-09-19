package TCPSocket.Server;

import TCPSocket.Server.EchoProtocol;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

/**
 * @Auther: Chandler
 * @Date: 2022/10/20 - 10 - 20 - 15:01
 * @Description: socketserver
 * @version: 1.0
 */
public class TCPEchoServer {

    private static final int BUFSIZE = 32;

    public static void main(String[] args) throws IOException {

        int servPort = 1024;
        int threadPoolSize = 5;

        final ServerSocket servSock = new ServerSocket(servPort);
        final Logger logger = Logger.getLogger("practical");

//        //主动开启线程池
//        for(int i = 0;i < threadPoolSize;i++) {
//            Thread thread = new Thread() {
//                public void run() {
//                    while (true) {
//                        try {
//                            Socket clntSock = servSock.accept();
//                            EchoProtocol.handleEchoClient(clntSock, logger);
//                        } catch (IOException ex) {
//                            logger.log(Level.WARNING, "Client accept failed", ex);
//                        }
//                    }
//                }
//            };
//            thread.start();
//            logger.info("Created and started Thread= "+thread.getName());
//        }


        //使用系统管理调度：Executor
        Executor service = Executors.newCachedThreadPool();

        while (true){
            Socket clntSocket = servSock.accept();
            service.execute(new EchoProtocol(clntSocket,logger));
        }


    }
}


