package UDPNIO.Server;

import java.io.IOException;
import java.nio.channels.SelectionKey;

/**
 * @Auther: Chandler
 * @Date: 2022/10/28 - 10 - 28 - 10:52
 * @Description: UDPNIO.Server
 * @version: 1.0
 */
public interface UDPProtocol {

    //选择器可用功能
    void handleRead(SelectionKey key)throws IOException;
    void handleWrite(SelectionKey key)throws IOException;

}
