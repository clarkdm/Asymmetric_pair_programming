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

/**
 * 
 * @author dmclark
 *
 */
public class Available_File {

	// private FileInputStream fis = null;
	private boolean fileExists = true;
	private BufferedReader br;
	private String fileName;
	private File file_;
	private Boolean has_changed = false;
	private Boolean has_changed_as_string = true;
	private String file_as_string = "";

	private Boolean visible = false;

	private ArrayList<Command> commands = new ArrayList<Command>();

	private ArrayList<String> lines = new ArrayList<String>();

	private ArrayList<Integer> lines_length = new ArrayList<Integer>();

	/**
	 * 
	 * @param fileName_String
	 */
	public Available_File(String fileName_String, Boolean visible) {
		try {
			this.visible = visible;
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
				lines_length.add(txt.length());

			}
			for (int i = 1; i < lines_length.size(); i++) {

				lines_length.set(i, lines_length.get(i) + 1);

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

	/**
	 * 
	 * take in cursor or character position and returns the line number
	 * 
	 * 
	 * @param position
	 *            cursor position that file
	 * @return the line number
	 */
	public int Get_line_number(int position) {

		// System.out.println("15_15_15_16 " +
		// temp.get_character_position_string());
		// System.out.println("15_15_15_17 " + temp.get_key_code_string());

		has_changed = true;
		has_changed_as_string = true;

		int total_1 = 0;
		int line = 0;
		// System.out.println("Get_line_number - position " + position);
		// System.out.println("Get_line_number - " + line);
		// System.out.println("Get_line_number - " + total_1);
		total_1 = lines_length.get(line);

		// loops until it has found which line
		while (total_1 <= position && line < lines.size()) {
			// System.out.println("Get_line_number line- " + line);
			// System.out.println("Get_line_number line- " + lines.get(line));
			// System.out.println("Get_line_number total_1- " + total_1);

			if (position <= total_1) {

				return line + 1;

			}
			total_1 = total_1 + lines_length.get(line);

			line++;
		}

		return line;

	}

	public String Get_file() {
		if (has_changed_as_string) {
			file_as_string = Get_largest_sequence_ID() + "///";

			for (int i = 0; i < lines.size(); i++) {
				System.out.println(lines.get(i) + i);
				file_as_string = file_as_string.concat(lines.get(i) + "\n");

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

		// System.out.println("15_15_15_16 " +
		// temp.get_character_position_string());
		// System.out.println("15_15_15_17 " + temp.get_key_code_string());

		has_changed = true;
		has_changed_as_string = true;

		int total_0 = 0;
		int total_1 = 0;
		int line = 0;

		String character_position_s = temp.get_character_position_string();
		String[] character_position_s_array = character_position_s.split(",");

		int character_position_end = Integer.parseInt(character_position_s_array[0]);
		int character_position_start = Integer.parseInt(character_position_s_array[1]);
		int key_code = Integer.parseInt(temp.get_key_code_string());
		char key = (char) key_code;
		// loops until it has found which line the character should be inserted
		// into an terminates
		// if it has run out of lines
		if (character_position_end == character_position_start) {

			while (total_1 <= character_position_end && line < lines.size()) {

				total_1 = total_1 + lines_length.get(line);
				if (character_position_end < total_1) {

					// insert the character into the line
					String temp2 = lines.get(line);
					StringBuffer sb = new StringBuffer(temp2);
					sb.insert((character_position_end - total_0), key);
					lines.set(line, sb.toString());
					lines_length.set(line, sb.length() + 1);
					return true;
				}
				total_0 = total_1;
				line++;
			}
			return false;
		} else {

			// #########################################################################################
			// #########################################################################################
			// If key is pressed while multiple characters are selected
			int start_line = 0;
			int end_line = 0;

			int start_character = 0;
			int end_character = 0;

			while (total_1 <= character_position_end && line < lines.size()) {

				total_1 = total_1 + lines_length.get(line);
				if (character_position_end < total_1) {
					end_line = line;
					if (end_line == 0) {
						end_character = character_position_end - total_0;
					}else {
						end_character = character_position_end - total_0 - 1;
					}
					
					
					
					
					
					int total_3 = 0;
					int total_4 = 0;
					line = 0;
					while (total_4 <= character_position_start && line < lines.size()) {
						total_4 = total_4 + lines_length.get(line);
						if (character_position_start < total_4) {
							start_line = line;
							System.out.println("character_position_start - total_3 - " + character_position_start + " - " + total_3);
							if (start_line == 0) {
								start_character = character_position_start - total_3;
							}else {
								start_character = character_position_start - total_3 - 1;
							}
							
							System.out.println("start_character + end_character - " + start_character + " - " + end_character);
							System.out.println("start_line + end_line - " + start_line + " - " + end_line);
							if (start_line == end_line) {
								System.out.println("new_character - start_line == end_line " );
								String temp2 = lines.get(line);
								StringBuffer sb = new StringBuffer(temp2);
								sb.delete(start_character, end_character);
								sb.insert(start_character, key);
								lines.set(line, sb.toString());
								if (start_line == 0) {
									lines_length.set(line, sb.length());
								} else {
									lines_length.set(line, sb.length() + 1);
								}

								return true;
							} else {
								System.out.println("new_character - start_line != end_line " );
								StringBuffer start_sb = null;
								for (int i = start_line; i <= (end_line); i++) {
									System.out.println("int i = start_line; i < (end_line); i++ " + i + " " + end_line);
									if (i == start_line) {
										System.out.println("new_character - i == start_line");
										String temp2 = lines.get(i);
										start_sb = new StringBuffer(temp2);
										start_sb.delete(start_character, temp2.length());
										start_sb.insert(start_character, key);
										lines.set(i, start_sb.toString());
										
									
									} else if (i == end_line) {
										System.out.println("new_character - i == end_line");
										String temp2 = lines.get(i);
										StringBuffer sb = new StringBuffer(temp2);
										sb.delete(0, end_character);
										start_sb.append(sb.toString());
										lines.set(start_line, start_sb.toString());
										lines.remove(i);
										lines_length.remove(i);
										i = i -1; 
										end_line = end_line - 1;
										if (start_line == 0) {
											lines_length.set(i, start_sb.length());
										} else {
											lines_length.set(i, start_sb.length() + 1);
										}
										System.out.println("new_character - i == end_line" + lines.get(i).length() + " " + end_character + " " + sb.toString() );
										return true;
									} else {
										System.out.println("new_character - else");
										lines.remove(i);
										
										lines_length.remove(i);
										i = i -1; 
										end_line = end_line - 1;

									}

								}

							}

							return true;

						}
						total_3 = total_4;
						line++;
					}

				}
				total_0 = total_1;
				line++;

			}
		}
		return true;
	}

	public FileInputStream GetFileInputStream() throws IOException {
		save_file();

		FileInputStream fis = null;
		fis = new FileInputStream(Get_file_name());
		// System.out.println("fsdgfdsrfg vbfg");
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
		} else {
			return commands.get(commands.size() - 1).get_sequence_ID();
		}

	}

	public Boolean getVisibility() {
		return visible;
	}

	public void setVisibility(Boolean visible) {
		this.visible = visible;
	}
}
