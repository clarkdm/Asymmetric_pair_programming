package users;

/**
 *    
 *  Stores  user information and use your cursor information
 *    
 * @author dmclark
 *
 */
public class user {

	private String UserName;

	private int UserID;

	private int Cursor_End_Position;
	private int Cursor_Start_Position;

	private String file;

	/**
	 * In order to create a user you need an ID number and the username
	 * 
	 * @param ID
	 *            ID-number
	 * @param Name
	 *            Name/username
	 */
	public user(int ID, String Name) {
		set_UserID(ID);
		set_UserName(Name);
		
		Cursor_End_Position = -1;
		Cursor_Start_Position = -1;
		file = "";
	}

	/**
	 * 
	 * @return the end point of the users selection or cursor position if no
	 *         selection made
	 */
	public int get_Cursor_End_Position() {
		return Cursor_End_Position;
	}

	/**
	 * 
	 * @param cursor_End_Position
	 *            the end of the selection or the cursors position if no text
	 *            selected
	 */
	public void set_Cursor_End_Position(int cursor_End_Position) {
		Cursor_End_Position = cursor_End_Position;
	}

	/**
	 * 
	 * @return the start point of the users selection or cursor position if no
	 *         selection made
	 */
	public int get_Cursor_Start_Position() {
		return Cursor_Start_Position;
	}

	/**
	 * 
	 * @param cursor_Start_Position
	 *            the start of the selection or the cursors position if no text
	 *            selected
	 */
	public void set_Cursor_Start_Position(int cursor_Start_Position) {
		Cursor_Start_Position = cursor_Start_Position;
	}

	/**
	 * 
	 * @param cursor_End_Position
	 *            the end of the selection or the cursors position if no text
	 *            selected
	 * @param cursor_Start_Position
	 *            the start of the selection or the cursors position if no text
	 *            selected
	 * @param file2 
	 */
	public void set_Cursor_Position(int cursor_End_Position, int cursor_Start_Position, String file2) {
		
		
		System.out.println("###########################" +file2 + " " + cursor_End_Position  + " " + cursor_Start_Position);
		file = file2;
		Cursor_End_Position = cursor_End_Position;
		Cursor_Start_Position = cursor_Start_Position;
	}

	/**
	 * 
	 * @return the users ID number
	 */
	public int get_UserID() {
		return UserID;
	}

	/**
	 * 
	 * @param userID
	 *            the users ID number
	 */
	private void set_UserID(int userID) {
		UserID = userID;
	}

	/**
	 * 
	 * @return tthe user's name/username
	 */
	public String get_UserName() {
		return UserName;
	}

	/**
	 * 
	 * @param userName
	 *            the users name/username
	 */
	public void set_UserName(String userName) {
		UserName = userName;
	}

	/**
	 * 
	 * @return the name of the file the user is currently viewing
	 */
	public String getFile() {
		return file;
	}

	/**
	 * 
	 * @param file
	 *            the name of the file the user is currently viewing
	 */
	public void setFile(String file) {
		this.file = file;
	}

	/**
	 * concatenate's the users ID number username cursor start position cursor
	 * end position and the name of the file currently being viewed
	 * 
	 * @return UserID + "," + UserName + "," + Cursor_Start_Position + "," +
	 *         Cursor_End_Position + "," + file
	 */
	public String toString() {
		// get users names and IDs and Cursor_Position and File as string
		return UserID + "," + UserName + "," + Cursor_Start_Position + "," + Cursor_End_Position + "," + file;
	}

}
