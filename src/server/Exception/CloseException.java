package server.Exception;

/** Server Application for COMP90015 Project 1
 *  @author Shenglan Yu 808600
 */

@SuppressWarnings("serial")
public class CloseException extends Exception{

	public CloseException() {
		
	}
	
	public CloseException(String message) {
		super(message);
	}

}
