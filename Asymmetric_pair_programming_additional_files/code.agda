import java.
.Buffered
cketE
Reader;
import java.i hi
s
dcd
.File.FileFileFileReader;
import jaFileo.
Exception;fe.rrrn.rrrnet.I
import java.File.Inet4Address;
import javaffkfkfnnrkrkrnffffffffffffnet6Address;
import javaFileNetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import Files.File_Manager;
import users.Users_Manager;

/**
 * 
 * @author dmclarkjdjjedruu  hi

 *
 */
public class Run {
    /**
     * 
     * @param argv
     * @throws Exception
     */
    
    public static void main(String argv[]) throws Exception {
        
        char key = (char) 13;
        
        System.out.println("q"+key+"q");

        InetAddress addr = getFirstNonLoopbackAddress();
        
        String ip = addr.getHostAddress();
        
        System.out.println("Displayed below are the addresses to access the editor");
        System.out.println("read/write  : " + ip + ":6789/RW.html");
        System.out.println("Read-only   : " + ip + ":6790/R.html");
        FileFile
        File_Manager files = new File_Manager();
        Users_Manager users = new Users_Manager(files);
        
        
        
        
        
        
            
            String fileName = "Asymmetric_pair_programming_additional_files/available_files.txt";

            BufferedReader br = new BufferedReader(new FileReader(fileName));

            while (br.ready()) {
                String txt = br.readLine();
                if (txt != "") {
                    files.new_file(txt, true);
                }
                
                
                

            }

            br.close();
    
        
    
        

        files.new_file("R.html", false);
        files.new_file("RW.html", false);
        files.new_file("css.css", false);
        files.new_file("readwrite.js", false);
        files.new_file("read_only.js", false);
        files.new_file("favicon.ico", false);
        
        

        // Set the port number.
        int port = 6789;
        Read_write_Server Http_Server = new Read_write_Server(port, files, users);
        // Create a new thread to process the request.
        Thread Http_thread = new Thread(Http_Server);
        // Start the thread.
        Http_thread.start();

        int port2 = 6790;
        Read_only_Server File_Server = new Read_only_Server(port2, files, users);
        // Create a new thread to process the request.
        Thread File_thread = new Thread(File_Server);
        // Start the thread.
        File_thread.start();
    }
    
    private static InetAddress getFirstNonLoopbackAddress() throws SocketException {
        Enumeration en = NetworkInterface.getNetworkInterfaces();
        while (en.hasMoreElements()) {
            NetworkInterface i = (NetworkInterface) en.nextElement();
            for (Enumeration en2 = i.getInetAddresses(); en2.hasMoreElements();) {
                InetAddress addr = (InetAddress) en2.nextElement();
                if (!addr.isLoopbackAddress()) {
                    if (addr instanceof Inet4Address) {
                        return addr;
                    }
                    if (addr instanceof Inet6Address) {
                        if (true) {
                            continue;
                        }
                        return addr;
                    }
                }
            }
        }
        return null;
    }
    
    
}
jddjjkrkrnffffffffe.rrrnet.Ifffffnet6Address;
import java.net.InetAddress;d
u