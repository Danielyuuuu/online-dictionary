package server;

/** Server Application for COMP90015 Project 1
 *  @author Shenglan Yu 808600
 */

import java.net.*;
import java.nio.charset.StandardCharsets;

import org.json.JSONObject;

import java.io.*;

public class DictionaryServer implements Runnable{
	
	private DictionaryService dictionaryService;
	private Socket socket;
	
	public DictionaryServer(DictionaryService service,Socket socket) {
		this.dictionaryService = service;
		this.socket = socket;
		
	}

	public void execute() {
		try {
				BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				OutputStreamWriter out = new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8);
				String content;
					while((content = br.readLine()) != null) {
			
						JSONObject request = new JSONObject(content);
			
					JSONObject result = parseExecution(request);
				
					out.write(result.toString()+'\n');
					out.flush();
					}
			
					br.close();
					out.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public JSONObject parseExecution(JSONObject request) {
		JSONObject result = null;
		String task = request.getString("Task");
		if(task.equals("Search") ){
			result = dictionaryService.search(request.getString("Key"));
		}else if(task.equals("Add")) {
			result = dictionaryService.add(request.getString("Key"),request.getString("Value"));
		}else if(task.equals("Remove")) {
			result = dictionaryService.remove(request.getString("Key"));
		}
		return result;
	}
	
	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	public DictionaryService getDictionaryService() {
		return dictionaryService;
	}

	public void setDictionaryService(DictionaryService dictionaryService) {
		this.dictionaryService = dictionaryService;
	}
	
	public void run() {
		execute();
	}
}
