import Files.File_Manager;david clarkdavid 
import users.Users_Manager;david clark


public class Run {oijgrnhboidrftgmhpiodftgjhtdgyjgfydjtgdfyjdtgyjdtyjetyihmoiertmngormstfgopimrnto;ighmertopighmneroitghmbjnoirfgnmhjboirftggjmnhgoirftgnmhoirftmghbopijsrfmtopighmrtopigmkroptigm'[oristjmhgopirtmghopirtmghpoir,mtophig[mrtpoighkmertopihgkmrpoitgkhm,po[rtgkhp[oirtgkmhp-ortkm,hgpiokrt,pog,brtpo,bprot,mhp'olfg,h[teyjhteyjltgy[jl,[tgyphl,j[pdgyh,j[npot,eyuj[pon,etdyojnm,ytgup;ohjn,mpo;tlyh,mjnp;otymk,pojhnmtyhpo[jnmtyhmnbpodgytmk,jnpotym,khjnpbojmkd,tyojnhmhdtyophmk,ndtgymhjnhhhhhhhhhhhhhhhhhhhhhhhhhpjdtmyhjhhhhhnpomdtgyhpjnmdtgyhpjnmjdtpyhmjnpdtyhmnjopdtgyhmjnpotdmyhpojns
    
    
    public static void main(String argv[]) throws Exception {
        File_Manager files = new File_Manager();
        Users_Manager users = new Users_Manager();
        
        
        files.new_file("code.agda");
        files.new_file("test.html");
        files.new_file("test2.html");
        files.new_file("css.css");
        files.new_file("text_Controller.js");
        files.new_file("read_only.js");
        files.new_file("favicon.ico");
        
        

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
}
