package server;

/** Server Application for COMP90015 Project 1
 *  @author Shenglan Yu 808600
 */

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import server.Exception.ClientLostException;
import server.Exception.CloseException;

public class ServerWrapper implements Runnable {

	private DictionaryService service;
	private ServerSocket socket;
	private ArrayList<DictionaryServer> clients;
	
	public ServerWrapper(DictionaryService service,ServerSocket socket) {
		this.service = service;
		this.socket = socket;
		this.clients = new ArrayList<>();
	}

	public void run() {
		try {
			execute();
		} catch (ClientLostException e) {
			e.printStackTrace();
		}
	}
	
	public void execute() throws ClientLostException {
		while(true) {
			try {
			Socket client = socket.accept();
			DictionaryServer server = new DictionaryServer(service,client);
			clients.add(server);
			new Thread(server).start();
			}catch(IOException e) {
				throw new ClientLostException();
			}
		}
	}
	
	public void terminate() throws CloseException {
		try {
		for(DictionaryServer s:clients){
			s.getSocket().close();
		}
		socket.close();
		}catch(IOException g) {
			throw new CloseException("Can't close socket, please try later.");
		}
	}

}
