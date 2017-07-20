package Files;

import java.util.StringTokenizer;
/**
 * 
 * @author dmclark
 *
 */
public class Command implements Comparable<Command>  {
	private String command;
	private int sequence_ID;
	private String character_position_string;
	private String key_code_string;
	private String command_s;
/**
 * @author dmclark
 * @param command
 */
	public Command(String command) {
		/**
		 * 
		 */
		
		this.command = command;
		System.out.println("hi 4#"+ command + "#hi 5#" + this.command);
		StringTokenizer command_tokens = new StringTokenizer(command, "///");
		
		
		// skip new line at the start
		command_tokens.nextToken();

		
//		System.out.println("1####");
		sequence_ID = Integer.parseInt(command_tokens.nextToken());
//		System.out.println("2###");
		character_position_string = command_tokens.nextToken();
		
		key_code_string = command_tokens.nextToken();
//		System.out.println("hi 6  "+ sequence_ID + " " + character_position_string + " " + key_code_string + "  hi 7  ");
		command_s = "///" + sequence_ID + "///" + character_position_string + "///" + key_code_string;
		
	}

	public String get_command() {
//		System.out.println("2");
		return command;

	}

	public int get_sequence_ID() { 
		/**
		 * 
		 */
		return sequence_ID;

	}

	public String get_character_position_string() {
		return character_position_string;

	}

	public String get_key_code_string() {
		return key_code_string;

	}

	@Override
	public String toString() {
		
		
		
		return command_s;

	}
	@Override
	public int compareTo(Command other) {

		if (sequence_ID < other.get_sequence_ID()) {
			return -1;
		}else if (sequence_ID > other.get_sequence_ID()) {
			return 1;
		}
		
		
		return 0;

	}

}
	