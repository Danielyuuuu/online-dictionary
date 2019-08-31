package application.Exception;

/** Client Application for COMP90015 Project 1
 *  @author Shenglan Yu 808600
 */


@SuppressWarnings("serial")
public class BadCommunicationException extends Exception {

	public BadCommunicationException(String name) {
		super(name);
	}

}
