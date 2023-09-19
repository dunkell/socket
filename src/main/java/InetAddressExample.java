import java.util.Enumeration;
import java.net.*;


/**
 * @Auther: Chandler
 * @Date: 2022/10/20 - 10 - 20 - 14:30
 * @Description: socketserver
 * @version: 1.0
 */
public class InetAddressExample {
    public static void main(String[] args) {

        try {

            Enumeration<NetworkInterface> interfaceList = NetworkInterface.getNetworkInterfaces();
            if (interfaceList == null){
                System.out.println("--No interfaces found--");
            }else{
                while(interfaceList.hasMoreElements()){
                    NetworkInterface iface = interfaceList.nextElement();
                    System.out.println("Interface " + iface.getName() + ":");
                    Enumeration<InetAddress> addrList = iface.getInetAddresses();
                    if(!addrList.hasMoreElements()){
                        System.out.println("\t(NO addresses for this interface)");
                    }
                    while (addrList.hasMoreElements()){
                        InetAddress address = addrList.nextElement();
                        System.out.print("\tAddress "
                            + ((address instanceof Inet4Address ? "(v4)"
                                : (address instanceof Inet6Address ? "(v6)" : "(?)"))));
                        System.out.println(": " + address.getHostAddress());
                    }
                }
            }
        }catch (SocketException e) {
            System.out.println("Error getting network interfaces:" + e.getMessage());
            //throw new RuntimeException(e);
        }
    }

}
