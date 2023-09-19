package TCPNIO.Server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;

/**
 * @Auther: Chandler
 * @Date: 2022/10/26 - 10 - 26 - 17:48
 * @Description: TCPNIO.Server
 * @version: 1.0
 */
public class TCPServerSelector {

    private static final int BUFSIZE = 256;
    private static final int TIMEOUT = 3000;

    public static void main(String[] args) throws IOException {

        Selector selector = Selector.open();
        ServerSocketChannel listnChannel = ServerSocketChannel.open();
        listnChannel.socket().bind(new InetSocketAddress(2044));
        listnChannel.configureBlocking(false);//必须注册为不阻塞的
        //用信道注册选择器
        listnChannel.register(selector, SelectionKey.OP_ACCEPT);

        TCPProtocol protocol = new EchoSelectorProtocol(BUFSIZE);

        while(true){

            //超时没有新的行动，输出.
            if(selector.select(TIMEOUT) == 0){
                System.out.print(".");
                continue;
            }

            //
            Iterator<SelectionKey> keyIter = selector.selectedKeys().iterator();
            while(keyIter.hasNext()){
                SelectionKey key = keyIter.next();
                if(key.isAcceptable()){
                    //System.out.println(111);
                    protocol.handleAccept(key);
                }
                if(key.isReadable()){
                    //System.out.println(222);
                    protocol.handleRead(key);
                }
                if(key.isValid() && key.isWritable()){
                    //System.out.println(333);
                    protocol.handleWrite(key);
                }
                keyIter.remove();
            }
        }
    }
}
