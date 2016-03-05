
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
//import java.nio.file.Files;
import java.util.Observable;
import java.util.Observer;
//import java.nio.file.Files;
import java.util.StringTokenizer;

import Files.File_Manager;
import users.Users_Manager;

class Read_only_Request implements Runnable, Observer {

	static String CRLF = "\r\n";

	Socket socket;

	private int sequence_ID;

	private Users_Manager users;
	private static File_Manager files;

	// Constructor
	public Read_only_Request(Socket socket, File_Manager files2, Users_Manager users) throws Exception {

		this.socket = socket;
		files = files2;
		files.addObserver(this);
		this.users = users;
		// System.out.println(files.GetFile("code.agda"));
		// System.out.println("£££££££££££££
		// hnfsdgrthwr4t5nhg09845jhg98uhtn9phg8j4w58hjgn94r58thgjg9rwtjnhgp9wnrt5gu");

	}

	// Implement the run() method of the Runnable interface.
	public void run() {
		try {

			processRequest();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	private void processRequest() throws Exception {
		// Random randomGenerator = new Random();
		// Thread.sleep(5000 + randomGenerator.nextInt(1000));
		// Get a reference to the socket's input and output streams.
		InputStream is = socket.getInputStream();
		DataOutputStream os = new DataOutputStream(socket.getOutputStream());
		// Set up input stream filters.

		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		ArrayList<String> br_ArrayList = new ArrayList<String>();

		// Get the request line of the HTTP request message.
		String requestLine = br.readLine();
		// Display the request line.
		System.out.println();
		System.out.println("111111" + requestLine);

		// Get and display the header lines.
		String headerLine = br.readLine();
		System.out.println("222222" + headerLine);
		// while ((headerLine = br.readLine()).length() != 0) {
		// System.out.println(headerLine);
		// }

		// Extract the filename from the request line.
		// Extract the filename from the request line.
		StringTokenizer tokens = new StringTokenizer(requestLine, " ");

		String method = tokens.nextToken(); // gets the method, which will be
											// "POST" "GET"

		StringTokenizer tokens_2 = new StringTokenizer(tokens.nextToken(), "/");
		// String fileName_s = tokens_2.nextToken();//
		// +"/"+tokens_2.nextToken();
		// Prepend a "." so that file request is within the current directory.

		System.out.println("3" + method + " " + method.equals("POST"));

		if (method.equals("POST")) {
			// If the URL contains a C at the beginning
			// its commands to the executed
			String command_type = "";
			boolean command_found = false;

			while (command_found == false) {
				System.out.println("HTRHFDSRTGHRTH      H");
				String temp_line = br.readLine();
				if (temp_line.contains("command")) {
					command_type = temp_line;

					System.out.println((br_ArrayList.size() + 1) + "command_type / " + command_type);
					command_found = true;
				}
				System.out.println((br_ArrayList.size() + 1) + " / " + temp_line);
				br_ArrayList.add(temp_line);

			}

			boolean body_found = false;

			while (body_found == false) {

				String temp_line = br.readLine();
				if (temp_line.contains("Language")) {
					body_found = true;
				}
				System.out.println((br_ArrayList.size() + 1) + " / " + temp_line);
				System.out.println(" 1234/ " + br_ArrayList.add(temp_line));

			}

			char[] charBuffer = new char[128];
			StringBuilder stringBuilder = new StringBuilder();

			// reads the 14th line which contains the command
			// E.G. 17///102///
			br.read(charBuffer, 0, 128);
			stringBuilder.append(charBuffer);
			String command = stringBuilder.toString();

			System.out.println("15_15_15_15 " + command + " " + command_type.contains("get_updates"));

			if (command_type.contains("get_updates")) {

				String file_Name = tokens_2.nextToken();
				get_updates(command, file_Name, os, br);

			} else if (command_type.contains("Get_File")) {
				System.out.println("Get_File");
				String file_Name = tokens_2.nextToken();
				Get_File(command, file_Name, os, br);
				
			} else if (command_type.contains("Set_UserName")) {
				System.out.println("Set_UserName");
				String file_Name = tokens_2.nextToken();
				Set_UserName(command, file_Name, os, br);
			}

		} else // if (method.equals("GET"))
		{
			// Else if the URL contains an F at
			// the beginning is a file request
			File_response(tokens_2, os, br, socket);

		}

	}
	
	
	private void Set_UserName(String command, String file_Name, DataOutputStream os, BufferedReader br)
			throws IOException {
		String statusLine = "";
		String contentTypeLine = "";

		String ContentLength = "";
		

		statusLine = "HTTP/1.1 200 OK";
		// contentTypeLine = "Content-type: " + contentType(file_Name);

		// System.out.println("3");

		// Send the status line.

		os.writeBytes(statusLine);
		os.writeBytes(CRLF);
		// Send the content type line.
		os.writeBytes(contentTypeLine);
		os.writeBytes(CRLF + "\n");
	
		
			
			StringTokenizer command_tokens = new StringTokenizer(command, "///");
			// skip new line at the start
			

			command_tokens.nextToken();
			String UserName = command_tokens.nextToken();
			
			

			
			os.writeBytes(users.add_User(UserName));
			
			os.close();
			br.close();
			socket.close();
		


	}
	
	
	

	private void Get_File(String command, String file_Name, DataOutputStream os, BufferedReader br) throws IOException {
		String statusLine = "";
		String contentTypeLine = "";
		String entityBody = "";
		String ContentLength = "";

		if (files.file_exists(file_Name)) {

			statusLine = "HTTP/1.1 200 OK";
			// contentTypeLine = "Content-type: " + contentType(file_Name);

			entityBody = files.Get_file(file_Name);

		} else {
			statusLine = "HTTP/1.1 404";
			contentTypeLine = " Not Found";
			entityBody = "<HTML>" + "<HEAD><TITLE>Not Found</TITLE></HEAD>" + file_Name
					+ "<BODY>        Not Found</BODY></HTML>";
		}

		// Send the status line.

		os.writeBytes(statusLine);
		os.writeBytes(CRLF);
		// Send the content type line.
		os.writeBytes(contentTypeLine);
		os.writeBytes(CRLF + "\n");
		os.writeBytes(entityBody);
		// Send a blank line to indicate the end of the header lines.

		System.out.println(files.save_file(file_Name));

		os.close();
		br.close();
		socket.close();

	}

	private void get_updates(String command, String file_Name, DataOutputStream os, BufferedReader br)
			throws IOException {
		String statusLine = "";
		String contentTypeLine = "";

		String ContentLength = "";
		System.out.println("_____________________________________________________ " + " ££££££££££££1");

		statusLine = "HTTP/1.1 200 OK";
		// contentTypeLine = "Content-type: " + contentType(file_Name);

		// System.out.println("3");

		// Send the status line.

		os.writeBytes(statusLine);
		os.writeBytes(CRLF);
		// Send the content type line.
		os.writeBytes(contentTypeLine);
		os.writeBytes(CRLF + "\n");
		System.out.println("_____________________________________________________ " + files.Get_has_changed(file_Name)
				+ " ££££££££££££2");
		if (files.Get_has_changed(file_Name)) {
			System.out.println("hi 1 " + command);
			StringTokenizer command_tokens = new StringTokenizer(command, "///");
			// skip new line at the start
			System.out.println("hi 2 ");

			command_tokens.nextToken();
			String sequence_ID_s = command_tokens.nextToken();
			System.out.println("hi 3  " + sequence_ID_s);
			sequence_ID = Integer.parseInt(sequence_ID_s);

			System.out.println("hi 4  " + files.Get_commands_size(file_Name));

			System.out.println("_____________________________________________________ "
					+ files.Get_changed(file_Name, sequence_ID) + " ££££££££££££4");
			os.writeBytes(files.Get_changed(file_Name, sequence_ID));
			sequence_ID = files.Get_largest_sequence_ID(file_Name);
			os.close();
			br.close();
			socket.close();
		} else {
			System.out.println("_____________________________________________________ " + " ££££££££££££3");

			os.close();
			br.close();
			socket.close();

		}

		// while (true) {
		// try {
		// Thread.sleep(1000);
		// } catch (InterruptedException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// os.writeBytes(files.Get_changed(file_Name, sequence_ID));
		// sequence_ID = files.Get_largest_sequence_ID(file_Name);
		//
		// }

		// Send a blank line to indicate the end of the header lines.

		// System.out.println(files.save_file(file_Name));

	}

	private static void Command_response(StringTokenizer tokens_2, DataOutputStream os, BufferedReader br,
			Socket socket) throws Exception {

		File fileName = new File(tokens_2.nextToken());
		// File fileName = new File("ajax.html");

		// Open the requested file.
		FileInputStream fis = null;
		boolean fileExists = true;
		try {
			fis = new FileInputStream(fileName);
		} catch (FileNotFoundException e) {
			fileExists = false;
		}

		// Construct the response message.
		String statusLine = "";
		String contentTypeLine = "";
		String entityBody = "";
		String ContentLength = "";

		if (fileExists) {
			statusLine = "HTTP/1.1 200 OK";
			contentTypeLine = "Content-type: " + contentType(fileName.toString());
			ContentLength = "Content-Length:" + fis.available();
		} else {
			statusLine = "HTTP/1.1 404";
			contentTypeLine = " Not Found";
			entityBody = "<HTML>" + "<HEAD><TITLE>Not Found</TITLE></HEAD>" + fileName
					+ "<BODY>        Not Found</BODY></HTML>";

		}

		// Send the entity body.
		if (fileExists) {

			// Send the status line.
			os.writeBytes(statusLine);
			os.writeBytes(CRLF);
			// Send the content type line.
			os.writeBytes(contentTypeLine);
			os.writeBytes(CRLF);

			os.writeBytes(ContentLength);

			os.writeBytes(CRLF + "\n");
			sendBytes(fis, os);
			// Send a blank line to indicate the end of header lines.
			fis.close();
			os.writeBytes(CRLF);
		} else {
			// Send the status line.

			os.writeBytes(statusLine);
			os.writeBytes(CRLF);
			// Send the content type line.
			os.writeBytes(contentTypeLine);
			os.writeBytes(CRLF + "\n");
			os.writeBytes(entityBody);
			// Send a blank line to indicate the end of the header lines.

		}
		// System.out.println("############# " + statusLine + contentTypeLine +
		// " ##########");
		// Close streams and socket.
		os.close();
		br.close();
		socket.close();

	}

	private static void File_response(StringTokenizer tokens_2, DataOutputStream os, BufferedReader br, Socket socket)
			throws Exception {
		String fileName_s = tokens_2.nextToken();
		// File fileName = new File(fileName_s);
		// File fileName = new File("ajax.html");

		// Open the requested file.
		FileInputStream fis = null;
		boolean fileExists = true;
		try {
			// System.out.println("fileExists = " + fileExists);
			fis = files.GetFileInputStream(fileName_s);
			// System.out.println("fileExists = " + fileExists);
		} catch (FileNotFoundException e) {
			// System.out.println("fileExists = " + fileExists);
			fileExists = false;
		}
		// String File_String = files.GetFile(fileName_s);
		// Construct the response message.
		String statusLine = "";
		String contentTypeLine = "";
		String entityBody = "";
		String ContentLength = "";

		if (fileExists) {
			statusLine = "HTTP/1.1 200 OK";
			contentTypeLine = "Content-type: " + contentType(fileName_s);
			ContentLength = "Content-Length:" + fis.available();
		} else {
			statusLine = "HTTP/1.1 404";
			contentTypeLine = " Not Found";
			entityBody = "<HTML>" + "<HEAD><TITLE>Not Found</TITLE></HEAD>" + fileName_s
					+ "<BODY>        Not Found</BODY></HTML>";

		}

		// Send the entity body.
		if (fileExists) {

			// Send the status line.
			os.writeBytes(statusLine);
			os.writeBytes(CRLF);
			// Send the content type line.
			os.writeBytes(contentTypeLine);
			os.writeBytes(CRLF);

			os.writeBytes(ContentLength);

			os.writeBytes(CRLF + "\n");

			// System.out.println("qqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq
			// " + fileName_s + " ££££££££££££");

			sendBytes(fis, os);
			// Send a blank line to indicate the end of header lines.
			fis.close();

			os.writeBytes(CRLF);
		} else {
			// Send the status line.

			os.writeBytes(statusLine);
			os.writeBytes(CRLF);
			// Send the content type line.
			os.writeBytes(contentTypeLine);
			os.writeBytes(CRLF + "\n");
			os.writeBytes(entityBody);
			// Send a blank line to indicate the end of the header lines.

		}
		// System.out.println("############# " + statusLine + contentTypeLine +
		// " - " + fileName_s + " ##########");
		// Close streams and socket.
		os.close();
		br.close();
		socket.close();

	}

	private static void sendBytes(FileInputStream fis, OutputStream os) throws Exception {
		// Construct a 1K buffer to hold bytes on their way to the socket.
		byte[] buffer = new byte[1024];
		int bytes = 0;
		// Copy requested file into the socket's output stream.
		while ((bytes = fis.read(buffer)) != -1) {
			os.write(buffer, 0, bytes);
		}
	}

	private static String contentType(String fileName) {
		if (fileName.endsWith(".htm") || fileName.endsWith(".html")) {
			return "text/html";
		}
		if (fileName.endsWith(".gif")) {
			return "image/gif";
		}
		if (fileName.endsWith(".jpeg")) {
			return "image/jpeg";
		}
		if (fileName.endsWith(".png")) {
			return "image/png";
		}
		if (fileName.endsWith(".js")) {
			return "application/javascript";
		}
		if (fileName.endsWith(".css")) {
			return "text/css";
		}
		return "application/octet-stream";
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub

	}

}
