package server;

import org.json.JSONObject;

public interface DictionaryService {
	public JSONObject search(String input);
	public JSONObject remove(String input);
	public JSONObject add(String key,String meaning);
}
