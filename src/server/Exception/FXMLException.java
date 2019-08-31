package server.Exception;

/** Server Application for COMP90015 Project 1
 *  @author Shenglan Yu 808600
 */

@SuppressWarnings("serial")
public class FXMLException extends Exception{

	public FXMLException() {
		
	}
	
	public FXMLException(String message) {
		super(message);
	}

}
