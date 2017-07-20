import java.io.*;
import java.net.*;
import java.util.*;

import Files.File_Manager;
import users.Users_Manager;
/**
 * 
 * @author dmclark
 *
 */
public final class Read_only_Server implements Runnable {
	private ServerSocket sock;
	private Socket client;
	private File_Manager files;
	private Users_Manager users;
	/**
	 * 
	 * @param port
	 * @param files
	 * @param users
	 * @throws Exception
	 */
	public Read_only_Server(int port, File_Manager files, Users_Manager users) throws Exception {
		// Establish the listen socket.
		sock = new ServerSocket(port);
		this.files = files;
		this.users = users;
	}

	private void Run_Server() throws Exception {

		// Process HTTP service requests in an infinite loop.
		while (true) {
			// Listen for a TCP connection request.
			client = sock.accept();

			System.out.println("Received: " + client);

			Read_only_Request request = new Read_only_Request(client, files, users);
			// Create a new thread to process the request.
			Thread thread = new Thread(request);
			// Start the thread.
			thread.start();

		}
	}

	@Override
	public void run() {
		try {
			Run_Server();
		} catch (Exception e) {
			System.out.println(e);
		}

	}

}