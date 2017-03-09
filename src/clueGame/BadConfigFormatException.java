package clueGame;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

@SuppressWarnings("serial")
public class BadConfigFormatException extends Exception {
	private String logFileName = "exceptionLog.txt";
	
	/*
	 * Make an exception with a default message
	 */
	public BadConfigFormatException() {
		super("There was a problem reading a configuration file.");
		outputToFile();
	}
	
	/*
	 * Make an exception with a custom message
	 */
	public BadConfigFormatException(String message) {
		super(message);
		outputToFile();
	}
	
	// try to output the message to a log file
	private void outputToFile() {
		PrintWriter out;
		try {
			out = new PrintWriter(logFileName);
			out.println(this.getMessage());
			out.close();
		} catch (FileNotFoundException e) {
			System.out.println("Cannot open file \"" + logFileName + "\"");
		}
	}
}