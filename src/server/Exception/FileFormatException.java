package server.Exception;

/** Server Application for COMP90015 Project 1
 *  @author Shenglan Yu 808600
 */

@SuppressWarnings("serial")
public class FileFormatException extends Exception {

	public FileFormatException() {
	}
	
	public FileFormatException(String message) {
		super(message);
	}

}
