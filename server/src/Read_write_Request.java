
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

/**
 * 
 * @author dmclark
 *
 */
class Read_write_Request implements Runnable, Observer {

	static String CRLF = "\r\n";

	Socket socket;
	private int sequence_ID;
	private Users_Manager users;
	private static File_Manager files;

	// Constructor

	/**
	 * 
	 * @param socket
	 * @param files2
	 * @param users
	 * @throws Exception
	 */
	public Read_write_Request(Socket socket, File_Manager files2, Users_Manager users) throws Exception {

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

		// System.out.println("3" + fileName_s);

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

			
			Command_response( command_type, tokens_2, os, br);

		} else
		// if (method.equals("GET"))
		{ // Else if the URL contains an F at
			// the beginning is a file request
			File_response(tokens_2, os, br, socket);

		}

	}

	private void Get_File( String file_Name, DataOutputStream os, BufferedReader br) throws IOException {
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

	private void keypress(String command_data, String file_Name, DataOutputStream os, BufferedReader br)
			throws IOException {
		String statusLine = "";
		String contentTypeLine = "";
		String entityBody = "";
		String ContentLength = "";

		if (files.new_character(file_Name, command_data)) {

			statusLine = "HTTP/1.1 200 OK";
			// contentTypeLine = "Content-type: " + contentType(file_Name);
			entityBody = "true";

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

		System.out.println("files.save_file(file_Name)" + files.save_file(file_Name));

		os.close();
		br.close();
		socket.close();

	}

	/**
	 * 
	 * Takes in the commands type and then runs the command that is requested
	 * 
	 * @param command_type
	 *            the type of commands that is being executed
	 * @param tokens_2
	 *            contains the filename
	 * @param os
	 *            the data outputs stream
	 * @param br
	 *            the buffer reader
	 *
	 * 
	 * 
	 * @throws Exception
	 */
	
	private void Command_response(String command_type, StringTokenizer tokens_2, DataOutputStream os,
			BufferedReader br) throws Exception {
		
	
		char[] charBuffer = new char[100];
		// reads the 14th line which contains the command
		// E.G. 17///102///
		br.read(charBuffer, 0, 100);

		StringBuilder stringBuilder = new StringBuilder();
		for (int i = 1; i < charBuffer.length; i++) {
			char temp = charBuffer[i];

			
//			'\u0000' == null
			if (temp != '\u0000') {

				stringBuilder.append(temp);
			}
		}	
		
		String command_data = stringBuilder.toString();
		
		System.out.println("15_15_15_15 " + command_data + " 15_15_15_15 " + command_type);
		if (command_type.contains("get_updates")) {

			String file_Name = tokens_2.nextToken();
			get_updates(command_data, file_Name, os, br);

		} else if (command_type.contains("Get_File")) {
			System.out.println("Get_File");
			String file_Name = tokens_2.nextToken();
			Get_File(file_Name, os, br);

		} else if (command_type.contains("Set_UserName")) {
			System.out.println("Set_UserName");
			String file_Name = tokens_2.nextToken();
			Set_UserName(command_data, file_Name, os, br);

		} else if (command_type.contains("Set_User_Position")) {
			System.out.println("Set_User_Position");
			String file_Name = tokens_2.nextToken();
			Set_User_Position(command_data, file_Name, os, br);

		} else if (command_type.contains("Get_Users_Position")) {
			System.out.println("Get_Users_Position");
			String file_Name = tokens_2.nextToken();
			Get_Users_Position(os, br);

		} else if (command_type.contains("keypress")) {
			System.out.println("keypress");
			String file_Name = tokens_2.nextToken();
			keypress(command_data, file_Name, os, br);

		} else if (command_type.contains("Get_visible_fileS")) {
			System.out.println("Get_visible_fileS");
			String file_Name = tokens_2.nextToken();
			Get_visible_fileS(os, br);

		}

	}

	/**
	 * 
	 * returns a list of all available to edit files as a string separated by
	 * commas
	 * 
	 * @param os
	 *            the data outputs stream
	 * @param br
	 *            the buffer reader
	 * @throws IOException
	 */

	private void Get_visible_fileS(DataOutputStream os, BufferedReader br) throws IOException {

		String statusLine = "";
		String contentTypeLine = "";

		statusLine = "HTTP/1.1 200 OK";
		// contentTypeLine = "Content-type: " + contentType(file_Name);

		// System.out.println("3");

		// Send the status line.

		os.writeBytes(statusLine);
		os.writeBytes(CRLF);
		// Send the content type line.
		os.writeBytes(contentTypeLine);
		os.writeBytes(CRLF + "\n");

		os.writeBytes(files.Get_visible());

		os.close();
		br.close();
		socket.close();

	}

	/**
	 * 
	 * gets positional details of all users including start of selection end of
	 * selection and filename and username and user ID number
	 * 
	 * @param os
	 *            the data outputs stream
	 * @param br
	 *            the buffer reader
	 * 
	 */
	private void Get_Users_Position(DataOutputStream os, BufferedReader br) throws IOException {

		String statusLine = "";
		String contentTypeLine = "";

		statusLine = "HTTP/1.1 200 OK";
		// contentTypeLine = "Content-type: " + contentType(file_Name);

		// System.out.println("3");

		// Send the status line.

		os.writeBytes(statusLine);
		os.writeBytes(CRLF);
		// Send the content type line.
		os.writeBytes(contentTypeLine);
		os.writeBytes(CRLF + "\n");

		os.writeBytes(users.toString());

		os.close();
		br.close();
		socket.close();

	}

	/**
	 * 
	 * processes the command of updating the users cursor position
	 * 
	 * @param command_data
	 *            data that goes along with the command
	 * @param file_Name
	 *            the name of the file that the user is currently on
	 * @param os
	 *            the data outputs stream
	 * @param br
	 *            the buffer reader
	 * @throws IOException
	 */
	private void Set_User_Position(String command_data, String file_Name, DataOutputStream os, BufferedReader br)
			throws IOException {

		String statusLine = "";
		String contentTypeLine = "";

		statusLine = "HTTP/1.1 200 OK";
		// contentTypeLine = "Content-type: " + contentType(file_Name);

		// System.out.println("3");

		// Send the status line.

		os.writeBytes(statusLine);
		os.writeBytes(CRLF);
		// Send the content type line.
		os.writeBytes(contentTypeLine);
		os.writeBytes(CRLF + "\n");

		StringTokenizer command_tokens = new StringTokenizer(command_data, "///");
		// skip new line at the start

		command_tokens.nextToken();
		String ID_S = command_tokens.nextToken();
		StringTokenizer command_tokens_2 = new StringTokenizer(command_tokens.nextToken(), ",");
		String cursor_End_Position = command_tokens_2.nextToken();
		String cursor_Start_Position = command_tokens_2.nextToken();
		String file_N = command_tokens.nextToken();
		System.out.println(
				"###########################" + ID_S + "# #" + cursor_End_Position + "# #" + cursor_Start_Position + "# #" + file_N);

		users.set_User_Position(Integer.parseInt(ID_S), Integer.parseInt(cursor_End_Position),
				Integer.parseInt(cursor_Start_Position), file_N);
		
	
		

		os.close();
		br.close();
		socket.close();

	}

	/**
	 * 
	 * processes the command of setting the user's name and sends the user's ID
	 * back to the user
	 * 
	 * @param command_data
	 *            data that goes along with the command
	 * @param file_Name
	 *            the name of the file that the user is currently on
	 * @param os
	 *            the data outputs stream
	 * @param br
	 *            the buffer reader
	 * 
	 */
	private void Set_UserName(String command, String file_Name, DataOutputStream os, BufferedReader br)
			throws IOException {

		String statusLine = "";
		String contentTypeLine = "";

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

		// adds the user to the list of users and returns the user ID number as
		// a string and then sends that to the client
		os.writeBytes(users.add_User(UserName));

		os.close();
		br.close();
		socket.close();

	}

	/**
	 * allow the client to request any updates to the final currently being
	 * viewed
	 * 
	 * @param command
	 *            data that goes along with the command
	 * @param file_Name
	 *            the name of the file that the user is currently on
	 * @param os
	 *            the data outputs stream
	 * @param br
	 *            the buffer reader
	 * @throws IOException
	 */
	private void get_updates(String command, String file_Name, DataOutputStream os, BufferedReader br)
			throws IOException {
		String statusLine = "";
		String contentTypeLine = "";

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
			System.out.println("fileExists = " + fileExists);
			fis = files.GetFileInputStream(fileName_s);
			System.out.println("fileExists = " + fileExists);
		} catch (FileNotFoundException e) {
			System.out.println("fileExists = " + fileExists);
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
			// " + (char) fis.read() + " ££££££££££££");

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
