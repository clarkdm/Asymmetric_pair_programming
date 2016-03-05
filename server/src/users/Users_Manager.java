package users;

import java.util.ArrayList;

public class Users_Manager {
	private ArrayList<user> users = new ArrayList<user>();

	private int next_ID = 123456;
	
	
	public Users_Manager() {
		
		
		
	}
	
	
	public int get_User_Position(int ID) {
		int Cursor_Position;

		Cursor_Position = get_User_by_ID(ID).get_Cursor_Position();

		return Cursor_Position;
	}

	public void set_User_Position(int ID, int cursor_Position) {
		int Cursor_Position = cursor_Position;

		get_User_by_ID(ID).set_Cursor_Position(Cursor_Position);

	}

	@Override
	public String toString() {
		// get all users names and IDs and Cursor_Position and File as string
		return get_Users();

	}

	public String get_Users() {
		// get all users names and IDs and Cursor_Position and File as string
		String temp = "";

		int index = 0;
		int size = users.size() - 1;
		while (index < size) {

			users.get(index).toString();

			index++;
		}

		return temp;
	}

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

	public String add_User(String user) {

		user temp;
		
		temp = new user(next_ID, user);
		
		
		next_ID++;	
		users.add(temp);
		
		return temp.get_UserID()+"";

	}
}
