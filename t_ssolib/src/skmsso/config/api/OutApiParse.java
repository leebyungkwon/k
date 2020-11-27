package skmsso.config.api;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONTokener;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import okhttp3.Response;

public class OutApiParse {

    public static int getCode(Response res){
        int code = res.code();
        return code;
    }

    public static String getData(Response res, String getStr) throws IOException {
        String ret = "";
        switch (getStr) {
            case "Location":
                String r = res.headers().get("Location");
                ret = r.split("/")[r.split("/").length-1];
                break;
            default:
                ret = res.body().string();
                break;
        }
        return ret;
    }

	public static Map<String, Object> getDatas(Response res) throws IOException, JSONException {
		Map<String, Object> rs = new HashMap<String, Object>();
		
		JSONArray jsonArray = new JSONArray(getData(res,""));
		rs.put("code", getCode(res));
		rs.put("data", jsonArray);
		return rs;
	}
	public static Object getBody(Response res) throws JSONException, IOException, ParseException {
		String str = getData(res,"");
		Object object = new JSONTokener(str).nextValue();
		if(object instanceof JSONArray){    
			Gson gson = new Gson(); 
			List<Map<String, Object>> map = null;
			map = gson.fromJson(str, new TypeToken<List<Map<String, Object>>>() {}.getType());
			return map;
		}else{
			JSONParser parser = new JSONParser();
			Object obj = parser.parse(str);
			JSONObject json = (JSONObject) obj;
			return json;
		}

	}
}
