package TCPNIO.Server;

import java.io.IOException;
import java.nio.channels.SelectionKey;

/**
 * @Auther: Chandler
 * @Date: 2022/10/26 - 10 - 26 - 17:43
 * @Description: TCPNIO.Server
 * @version: 1.0
 */
public interface TCPProtocol {
    void handleAccept(SelectionKey key)throws IOException;
    void handleRead(SelectionKey key)throws  IOException;
    void handleWrite(SelectionKey key)throws IOException;
}
