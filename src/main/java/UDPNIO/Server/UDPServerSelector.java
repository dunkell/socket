package UDPNIO.Server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;

/**
 * @Auther: Chandler
 * @Date: 2022/10/28 - 10 - 28 - 10:53
 * @Description: UDPNIO.Server
 * @version: 1.0
 */
public class UDPServerSelector {

    private static final int port = 2999;//端口号
    private static final int TIMEOUT = 3000;//等待时间


    public static void main(String[] args) throws IOException {

        //开启选择器和信道
        Selector selector = Selector.open();
        DatagramChannel datagramChannel = DatagramChannel.open();
        datagramChannel.configureBlocking(false);//设置信道不阻塞
        //信道绑定端口
        datagramChannel.socket().bind(new InetSocketAddress(port));
        //注册信道，客户端信息类到选择器中，设定接收的初始事件为read事件
        datagramChannel.register(selector, SelectionKey.OP_READ,new UDPEchoSelectorProtocol.ClientRecord());

        UDPProtocol protocol =  new UDPEchoSelectorProtocol();

        while (true){
            //如果没有新的时间出现，等待并打印*
            if(selector.select(TIMEOUT) == 0){
                System.out.print("*");
                continue;
            }

            //新事件出现，为read事件
            Iterator<SelectionKey> keyIterator = selector.selectedKeys().iterator();
            while (keyIterator.hasNext()){
                SelectionKey key = keyIterator.next();
                if(key.isReadable()){
                    //处理read事件并改为write事件
                    protocol.handleRead(key);
                }
                if(key.isValid() &&key.isWritable()){
                    //处理write事件并给位read事件
                    protocol.handleWrite(key);
                }

                //移除已处理事件
                keyIterator.remove();

            }
        }
    }
}
