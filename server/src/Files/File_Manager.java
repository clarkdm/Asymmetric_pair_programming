package Files;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Observable;

/**
 * 
 * @author dmclark
 *
 */
public class File_Manager extends Observable {

	private ArrayList<Available_File> files = new ArrayList<Available_File>();
	private ArrayList<String> names = new ArrayList<String>();

	/**
	 * 
	 */
	public File_Manager() {

	}

	private int get_index(String fileName) {
		boolean found = false;
		int i = 0;
		int size = files.size();
		while (!found && (i < size)) {
			// System.out.println("£££ " + names.get(i).equals(fileName) + "
			// ££££££££££££");
			if (names.get(i).equals(fileName)) {
				found = true;
				// System.out.println("£££ " + files.get(i).Get_file_name() + "
				// ££££££££££££" + i);
				return i;
			}
			i++;
		}
		return -1;

	}
	
	
	
	/**
	 * take in cursor or character position and fileName and returns the line number
	 * @param fileName name of the file being accessed
	 * @param position position cursor position that file
	 * @return the line number
	 */
	public int Get_line_number(String fileName, int position) {
		
		
		return files.get(get_index(fileName)).Get_line_number(position);
		
		
	}
	public String Get_visible() {
		String file_names = "";
		int i = 0;
		int size = files.size();
		while ((i < size)) {
			if (files.get(i).getVisibility()) {
				if (file_names == "") {

					file_names = files.get(i).Get_file_name();

				} else {
					file_names = file_names + "," + files.get(i).Get_file_name();
				}

			}

			i++;
		}
		return file_names;

	}

	public int Get_commands_size(String fileName) {
		return files.get(get_index(fileName)).Get_commands_size();

	}

	public void new_file(String fileName, Boolean visible) {

		Available_File file_ = new Available_File(fileName, visible);
		// System.out.println("£££££££££££££ " + fileName + "
		// ££££££££££££"+file_.Get_file_name()+ "££££££££££££ ");
		files.add(file_);
		names.add(fileName);

	}

	public boolean close_file(String fileName) throws IOException {

		return files.get(get_index(fileName)).close_file();

	}

	public boolean file_exists(String fileName) throws IOException {
		if (get_index(fileName) == -1) {
			return false;
		}
		return true;

	}

	public boolean save_file(String fileName) throws IOException {
		return files.get(get_index(fileName)).save_file();
	}

	public boolean Get_has_changed(String fileName) {
		System.out.println("111");
		return files.get(get_index(fileName)).Get_has_changed();
	}

	public String Get_changed(String fileName, int sequence_ID) {
		System.out.println("1");
		return files.get(get_index(fileName)).Get_commands(sequence_ID);
	}

	public String Get_file(String fileName) {
		return files.get(get_index(fileName)).Get_file();
	}

	public boolean new_character(String fileName, String command) {
		return files.get(get_index(fileName)).new_character(command);

	}

	private ArrayList<String> Get_file_names() {

		return names;

	}

	public FileInputStream GetFileInputStream(String fileName) throws IOException {
		try {
			return files.get(get_index(fileName)).GetFileInputStream();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("£££££££££££££ " + fileName + " ££££££££££££" + " ££££££££££££ ");
			e.printStackTrace();
		}
		return null;
	}

	public int Get_largest_sequence_ID(String fileName) {

		files.get(get_index(fileName)).Get_largest_sequence_ID();

		return 0;

	}
}