package skmsso;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import skmsso.config.api.OutApiConnector;
import skmsso.config.api.OutApiConnector.SkmSSO;

public class SsoConnection {
	public static void main(String[] args) throws IOException, Exception {
	
		System.out.println("########## run");
		Map<String, Object> p = new HashMap<String, Object>();
        p.put("birth", "801011");
        p.put("di", "cccccddddd");
        p.put("ci", "dddddccccc");
        p.put("safeKey", "cccccccc");
        p.put("memId", "test2");
        p.put("memNm", "테스터2");
        p.put("phone", "01000000001");
         
        SkmSSO api = new OutApiConnector.SkmSSO("SVC000", "1234567898765432");
        api.mode("M_SAVE");
        api.parameter(p);
        api.method("POST");  

        int status = api.call();
        
        System.out.println("########### result DATA ####" );
        System.out.println(api.getCode());
        
        if(status == 200) {
        	Map<String, Object> rData = (Map<String, Object>) api.getData();
	        System.out.println(rData.get("ci"));
	        System.out.println(rData.get("di"));	
        }else {
        	System.out.println("########## 오류발생 :: " +  api.getCode());
        	Map<String, Object> rData =  (Map<String, Object>) api.getData();
        	System.out.println(rData);
        }
        
        
	   	/* 
        
		Map<String, Object> p = new HashMap<String, Object>();
        p.put("di", "aaaaaaaaaa");
        p.put("ci", "bbbbbbbbbb");

		skmsso.config.api.OutApiConnector.setApi api = new OutApiConnector.setApi("SVC020","ECDM39K2JNM23U1A");
        api.mode("GET");
        api.parameter(p);
        api.method("GET");
        */
        

		Map<String, Object> p1 = new HashMap<String, Object>();
        p1.put("di", "cccccddddd");
        p1.put("ci", "dddddccccc");
        SkmSSO api1 = new OutApiConnector.SkmSSO("SVC000","1234567898765432");
        api1.mode("M_LIST");
        api1.parameter(p1);
        api1.method("GET");
        int status1 = api1.call();
        System.out.println("########### result DATA ####" );
        System.out.println(api1.getCode());
        if(status1 == 200) {
        	List<Map<String, Object>> rData =  (List<Map<String, Object>>) api1.getData();
            for(int i=0;i<rData.size();i++) {
            	System.out.println(rData.get(i));
            }	
        }else {
        	System.out.println("########## 오류발생 :: " +  api1.getCode());
        	Map<String, Object> rData =  (Map<String, Object>) api.getData();
        	System.out.println(rData);
        }
        
	}
}
