package server;

/** Server Application for COMP90015 Project 1
 *  @author Shenglan Yu 808600
 */

import java.io.*;

import org.json.JSONObject;

import server.Exception.FileFormatException;

public class BasicDictionaryService implements DictionaryService{

	private JSONObject dictionary;
	
	public BasicDictionaryService(String inFile) throws FileNotFoundException, FileFormatException {
		
		this.dictionary = new JSONObject();
		
		try(BufferedReader bf = 
				new BufferedReader(new FileReader(inFile))){
			String line;
			while((line = bf.readLine()) != null ) {
				String pairs[] = line.split(":");
				dictionary.put(pairs[0], pairs[1]);
			}		
		}catch(FileNotFoundException e) {
			throw new FileNotFoundException("Please give valid dictionary path");
		}catch(IOException f) {
			throw new FileFormatException("Unreadable File Format");
		}
	}
	
	public JSONObject search(String input) {
		JSONObject result = new JSONObject();
		if(dictionary.has(input)) {
			result.put("Result","Success" );
			result.put("Description",input+':'+dictionary.getString(input) );
			return result;
		}else {
			result.put("Result", "Failure");
			result.put("Description","Key not exist" );
			return result;
		}
	}

	public JSONObject remove(String input) {
		JSONObject result = new JSONObject();
		if(dictionary.has(input)) {
			dictionary.remove(input);
			result.put("Result","Success" );
			result.put("Description",input+" removed" );
			return result;
		}else {
			result.put("Result", "Failure");
			result.put("Description","Key not exist" );
			return result;
		}
	}

	public JSONObject add(String key,String meaning) {
		
		JSONObject result = new JSONObject();
		if(dictionary.has(key) || meaning == null ) {
			result.put("Result", "Failure");
			result.put("Description","Duplicate Key" );
			return result;
		}else {
			dictionary.put(key, meaning);
			result.put("Result", "Success");
			result.put("Description",key+" added" );
			return result;
		}
	}

	public JSONObject getDictionary() {
		return dictionary;
	}

	public void setDictionary(JSONObject dictionary) {
		this.dictionary = dictionary;
	}
	

}
