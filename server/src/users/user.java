package users;

public class user {

	private String UserName;

	private int UserID;

	private int Cursor_Position;
	
	private String file;

	public user(int ID, String Name) {
		set_UserID(ID);
		set_UserName(Name);

	}

	public int get_Cursor_Position() {
		return Cursor_Position;
	}

	public void set_Cursor_Position(int cursor_Position) {
		Cursor_Position = cursor_Position;
	}

	public int get_UserID() {
		return UserID;
	}

	private void set_UserID(int userID) {
		UserID = userID;
	}

	public String get_UserName() {
		return UserName;
	}

	public void set_UserName(String userName) {
		UserName = userName;
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	
	public String toString() {
		// get users names and IDs and Cursor_Position and File as string
		return UserID + "," + UserName + "," + Cursor_Position + "," + file;
	}
	
	
}
