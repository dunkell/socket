package TCPNIO.Server;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * @Auther: Chandler
 * @Date: 2022/10/26 - 10 - 26 - 18:02
 * @Description: TCPNIO.Server
 * @version: 1.0
 */
public class EchoSelectorProtocol implements TCPProtocol{

    private int bufSize;//I/Obuffer的大小

    public EchoSelectorProtocol(int bufSize){
        this.bufSize = bufSize;
    }


    @Override
    public void handleAccept(SelectionKey key) throws IOException {
        SocketChannel clntChan = ((ServerSocketChannel) key.channel()).accept();
        clntChan.configureBlocking(false);
        clntChan.register(key.selector(),SelectionKey.OP_READ, ByteBuffer.allocate(bufSize));
        System.out.println(key.isReadable());
    }

    @Override
    public void handleRead(SelectionKey key) throws IOException {
        SocketChannel clntChan = (SocketChannel) key.channel();
        ByteBuffer buf = (ByteBuffer) key.attachment();
        long bytesRead = clntChan.read(buf);
        if(bytesRead == -1){
            clntChan.close();
        }else if(bytesRead >0){
            key.interestOps(SelectionKey.OP_READ | SelectionKey.OP_WRITE);
        }

    }

    @Override
    public void handleWrite(SelectionKey key) throws IOException {

        ByteBuffer buf = (ByteBuffer) key.attachment();
        buf.flip();
        SocketChannel clntChan = (SocketChannel) key.channel();
        clntChan.write(buf);
        if(!buf.hasRemaining()){
            key.interestOps(SelectionKey.OP_READ);
        }
        buf.compact();
    }
}
