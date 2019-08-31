package server.Exception;

@SuppressWarnings("serial")
public class ClientLostException extends Exception{

	public ClientLostException(String message) {
		super(message);
	}
	
	public ClientLostException() {
		
	}

}
