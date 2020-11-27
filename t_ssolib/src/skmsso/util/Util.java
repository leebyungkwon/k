package skmsso.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Util {
	// list<map> 을 json 형태로 변형.
	public static JSONArray convertListToJson(List<HashMap<String, Object>> param) {
		JSONArray jsonArray = new JSONArray();
		for (Map<String, Object> map : param) {
			jsonArray.add(convertMapToJson(map));
		}
		return jsonArray;

	}

	// map 을 json 형태로 변형
	public static JSONObject convertMapToJson(Map<String, Object> param) {
		JSONObject json = new JSONObject();
		for (Map.Entry<String, Object> entry : param.entrySet()) {
			String key = entry.getKey();
			Object value = entry.getValue();
			// json.addProperty(key, value);
			json.put(key, value);
		}
		return json;
	}
	
}
