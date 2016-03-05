package Files;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Available_File {

	// private FileInputStream fis = null;
	private boolean fileExists = true;
	private BufferedReader br;
	private String fileName;
	private File file_;
	private Boolean has_changed = false;
	private Boolean has_changed_as_string = true;
	private String file_as_string = "";

	private ArrayList<Command> commands = new ArrayList<Command>();

	private ArrayList<String> lines = new ArrayList<String>();

	private ArrayList<Integer> lines_length = new ArrayList<Integer>();

	public Available_File(String fileName_String) {
		try {

			fileName = fileName_String;

			file_ = new File(fileName);
			br = new BufferedReader(new FileReader(fileName));

			while (br.ready()) {
				String txt = br.readLine();
				// System.out.println("£££££££££££££ " + txt + " ££££££££££££");
				// contains each line of code
				lines.add(txt);
				// ccontends a length of each line of code
				// the +1 is for the new line character picked up by the HTML
				// and JavaScript
				lines_length.add(txt.length() + 1);
			}
			br.close();
		} catch (FileNotFoundException e) {
			fileExists = false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Get_file();
	}

	public String Get_file() {
		if (has_changed_as_string) {
			file_as_string = Get_largest_sequence_ID() + "///";
			
			for (int i = 0; i < lines.size(); i++) {
				System.out.println(lines.get(i) + i);
				file_as_string = file_as_string.concat(lines.get(i)+"\n");

				// file_as_string.concat("<br>");

			}
			has_changed_as_string = false;
		}
		return file_as_string;

	}

	public boolean save_file() throws IOException {
		if (has_changed) {

			FileWriter fw;

			fw = new FileWriter(file_);

			BufferedWriter bw = new BufferedWriter(fw);
			
			for (int i = 0; i < lines.size(); i++) {

				bw.write(lines.get(i));

				bw.newLine();

			}

			bw.close();
			has_changed = false;
			return true;

		}
		return true;

	}

	public String Get_file_name() {

		return fileName;

	}

	public boolean Get_file_Exists() {

		return fileExists;

	}

	public boolean Get_has_changed() {

		return has_changed_as_string;

	}

	public boolean new_character(String command) {

		Command temp = new Command(command);
		commands.add(temp);

//		System.out.println("15_15_15_16 " + temp.get_character_position_string());
//		System.out.println("15_15_15_17 " + temp.get_key_code_string());

		has_changed = true;
		has_changed_as_string = true;

		int total_0 = 0;
		int total_1 = 0;
		int line = 0;
		int character_position = Integer.parseInt(temp.get_character_position_string());
		int key_code = Integer.parseInt(temp.get_key_code_string());
		char key = (char) key_code;
		// loops until it has found which line the character should be inserted
		// into an terminates
		// if it has run out of lines

		while (total_1 <= character_position && line < lines.size()) {
			// System.out.println(total_1);
			// System.out.println(character_position);
			// System.out.println(line);
			// System.out.println(lines.size());
			// System.out.println(total_1 <= character_position);
			// System.out.println(line < lines.size());

			total_1 = total_1 + lines_length.get(line);
			if (character_position < total_1) {

				// insert the character into the line
				String temp2 = lines.get(line);
				StringBuffer sb = new StringBuffer(temp2);
				sb.insert((character_position - total_0), key);
				lines.set(line, sb.toString());
				lines_length.set(line, sb.length() + 1);
				return true;
			}
			total_0 = total_1;
			line++;
		}
		return false;
	}

	public FileInputStream GetFileInputStream() throws IOException {
		save_file();

		FileInputStream fis = null;
		fis = new FileInputStream(Get_file_name());
//		System.out.println("fsdgfdsrfg                 vbfg");
		return fis;

	}

	public boolean close_file() throws IOException {

		return save_file();

	}
	public int Get_commands_size() {
		return commands.size();
		
	}
	public String Get_commands(int sequence_ID) {
		String to_be_sent = "";
		
		for (Command command : commands) {
			
			if (command.get_sequence_ID() > sequence_ID) {

				to_be_sent = to_be_sent.concat(command.toString() + ",,,");
				
			}

		}

		return to_be_sent;

	}

	public int Get_largest_sequence_ID() {
		
	if (commands.size() == 0) {
		return 1;
	}else {
		return commands.get(commands.size() - 1).get_sequence_ID();
	}
		
		
	}
}




















