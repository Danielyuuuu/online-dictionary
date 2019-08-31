package application;

/** Client Application for COMP90015 Project 1
 *  @author Shenglan Yu 808600
 */


import java.net.*;
import java.nio.charset.StandardCharsets;
import java.io.*;

import org.json.*;

import application.Exception.BadCommunicationException;
import application.Exception.CloseException;
import application.Exception.ServerInvalidException;

public class DictionaryClient {
	private Socket socket;
	private OutputStreamWriter out;
	private BufferedReader br;
	private long time;
	
	public DictionaryClient() {	
	}
	
	public void launch(String server,int port) throws ServerInvalidException, BadCommunicationException {
		try{
			Socket socket = new Socket(server,port);
			this.socket = socket;
			this.time = System.currentTimeMillis();
		}catch(Exception e) {
			throw new ServerInvalidException("Can't connect ,check address and port.");
		}
		try {
		this.out = new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8);
		this.br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		}catch(Exception g) {
			throw new BadCommunicationException("Stream Error,maybe connection lost, connect again");
		}
	}
	
	public JSONObject request(JSONObject request) throws BadCommunicationException {
		try {
			if((System.currentTimeMillis() - this.time)<= 60000) {
				this.time = System.currentTimeMillis();
				out.write(request.toString()+'\n');
				out.flush();
				String content =br.readLine();
				JSONObject result = new JSONObject(content);
				return result;
			}else {
				this.terminate();
				throw new BadCommunicationException("Stream Error,maybe connection lost,connect again");
			}
		}catch(Exception e) {
			throw new BadCommunicationException("Stream Error,maybe connection lost,connect again");
		}
	}
	
	public void terminate() throws CloseException {
		try {
			out.close();
			br.close();
			socket.close();
		}catch(Exception e) {
			throw new CloseException("Can't close socket, please try later");
		}
	}

	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}
}
