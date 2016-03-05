import Files.File_Manager;
import users.Users_Manager;


public class Run {
	
	
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
