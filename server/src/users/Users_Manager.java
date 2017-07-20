package users;

import java.util.ArrayList;

import Files.File_Manager;

/**
 * 
 * @author dmclark
 *
 */
public class Users_Manager {
	private ArrayList<user> users = new ArrayList<user>();

	private int next_ID = 123456;
	private File_Manager files;
	// /**
	// *
	// *
	// *
	// * @param ID
	// * the ID of the user who you want the cursor position of
	// *
	// * @return the end of the users
	// */
	// public int get_User_End_Position(int ID) {
	// int Cursor_Position;
	//
	// Cursor_Position = get_User_by_ID(ID).get_Cursor_End_Position();
	//
	// return Cursor_Position;
	// }

	public Users_Manager(File_Manager files) {
		this.files = files;
	}

	/**
	 * sets the new start and end position of this user's cursor
	 *
	 * @param ID
	 *            the user ID number
	 * @param cursor_End_Position
	 *            the end point of the users selection or cursor position if no
	 *            selection made
	 * @param cursor_Start_Position
	 *            the start point of the users selection or cursor position if
	 *            no selection made
	 * @param file
	 */
	public void set_User_Position(int ID, int cursor_End_Position, int cursor_Start_Position, String file) {

		get_User_by_ID(ID).set_Cursor_Position(cursor_End_Position, cursor_Start_Position, file);

	}

	/**
	 * get all users names and IDs and Cursor_Position and File as string
	 * 
	 * @return get all users names and IDs and Cursor_Position and File as
	 *         string
	 */
	@Override
	public String toString() {
		// get all users names and IDs and Cursor_Position and File as string
		return get_Users();

	}

	/**
	 * get all users names and IDs and Cursor_Position and filename and currently number as
	 *         string
	 * @return get all users names and IDs and Cursor_Position and filename and currently number as
	 *         string
	 */
	public String get_Users() {
		// get all users names and IDs and Cursor_Position and File as string
		String temp = "";

		int index = 0;
		int size = users.size() - 1;
		System.out.println("get_Users - size " + size);
		
		while (index <= size) {
		

			
			
			if (users.get(index).getFile() != "") {
				
			
			int line = files.Get_line_number(users.get(index).getFile(), users.get(index).get_Cursor_End_Position());
			
			
			if (temp == "") {

				temp = users.get(index).toString() + "," + line;

			} else {
				temp = temp + "///" + users.get(index).toString() + "," + line;
			}
		}else {
			if (temp == "") {

				temp = users.get(index).toString();

			} else {
				temp = temp + "///" + users.get(index).toString();
			}
		}

			index++;
		}

		return temp;
	}

	/**
	 * 
	 * Take the user ID number and returns an instance of the user class with
	 * the matching user ID to allow it to be updated or to retrieve data
	 * 
	 * @param ID
	 *            users ID number
	 * @return the instance of the user class with the correct userid
	 */
	public user get_User_by_ID(int ID) {
		user user = null;

		boolean found = false;
		int i = 0;
		int size = users.size();
		while (!found && (i < size)) {

			if (users.get(i).get_UserID() == ID) {
				found = true;
				user = users.get(i);

			}
			i++;
		}

		return user;
	}

	/**
	 * Adds a new user to the system takes in the username and generates a user
	 * ID number and returns it as a string
	 * 
	 * @param user
	 * @return user ID number as a string
	 */
	public String add_User(String user) {

		user temp;

		temp = new user(next_ID, user);

		next_ID++;
		users.add(temp);

		return temp.get_UserID() + "";

	}
}
