package UDPNIO.Server;

import java.io.IOException;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;

/**
 * @Auther: Chandler
 * @Date: 2022/10/28 - 10 - 28 - 10:52
 * @Description: UDPNIO.Server
 * @version: 1.0
 */
public class UDPEchoSelectorProtocol implements UDPProtocol {


    static class ClientRecord{//客户端信息类
        public ByteBuffer buffer = ByteBuffer.allocate(11);//客户端发送信息
        public SocketAddress clientAddress;//客户端地址
    }

    @Override
    public void handleRead(SelectionKey key) throws IOException {
        //使用key绑定的客户端信息类和信道
        ClientRecord clientRecord = (ClientRecord) key.attachment();
        DatagramChannel datagramChannel = (DatagramChannel) key.channel();

        //接收客户端信息
        clientRecord.buffer.clear();
        clientRecord.clientAddress = datagramChannel.receive(clientRecord.buffer);
        System.out.println(new String(clientRecord.buffer.array()));

        //将read事件转换为write事件，本次不生效，在下一个操作key中生效
        if(clientRecord.clientAddress != null){
            key.interestOps(SelectionKey.OP_WRITE);
        }
    }

    @Override
    public void handleWrite(SelectionKey key) throws IOException {
        //使用key绑定的客户端信息类和信道
        ClientRecord clientRecord = (ClientRecord) key.attachment();
        DatagramChannel datagramChannel = (DatagramChannel) key.channel();

        //发送信息
        ByteBuffer serverBuffer = ByteBuffer.wrap("serverword".getBytes());
        datagramChannel.send(serverBuffer,clientRecord.clientAddress);

        //将信息改为可读
        key.interestOps(SelectionKey.OP_READ);
    }

}
