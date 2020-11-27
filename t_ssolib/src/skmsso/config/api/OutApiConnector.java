package skmsso.config.api;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Request.Builder;
import okhttp3.RequestBody;
import okhttp3.Response;
import skmsso.config.LibConst;
import skmsso.util.SEED;

public class OutApiConnector {

    public OutApiConnector() {}
	public static SEED seed = new SEED();
	
    public static class SkmSSO {
    	private String siteId;      	//api siteId
        private String secretKey;      	//api 시크릿키
        private String mode="";         //api mode
        private String url="";          //api url
        private Map<String, Object> parameter ;    	//api param
        private String token="";        //api 인증 토큰
        private String method="";       //
        private Boolean outLog=false;   //
        
        private Object data;
        private int code;

        public SkmSSO(String siteId, String secretKey) {
        	this.siteId   	= siteId;
            this.secretKey  = secretKey;
        }
        public SkmSSO mode(String mode){
            this.mode = mode;
            return this;
        }
        private SkmSSO url(String url){
            this.url = url;
            return this;
        }
        public SkmSSO parameter(Map<String, Object> p){
            this.parameter = p;
            return this;
        }
        public SkmSSO token(String token){
            this.token = token;
            return this;
        }
        public SkmSSO method(String method){
            this.method = method;
            return this;
        }
        public SkmSSO outLog(Boolean outLog){
            this.outLog = outLog;
            return this;
        }

        public void requestInfo(){
            String str = "";
            str += "\n***************************************************************\n";
            str += "***********OutApiConnector = requestInfo***********\n";
            str += "secretKey --> " + secretKey + "\n";
            str += "token --> " + token + "\n";
            str += "method --> " + method + "\n";
            str += "url --> " + url + "\n";
            if("GET".equals(method))    str += "parameter --> " + parameter+  "\n";
            else                        str += "parameter --> " + parameter.toString() + "\n";
            //log.info(str);
        }
        public void responseInfo(Response response) throws IOException {
            String str = "";
            str += "\n***************************************************************\n";
            str += "***********OutApiConnector = responseInfo***********\n";
            str += "header --> \n";
            str += response+"\n";
            str += "code --> \n";
            str += response.code()+"\n";
            str += "message --> \n";
            str += response.message()+"\n";
            //str += "body --> \n";
            //str += res.body().string()+"\n";
            str += "***************************************************************";
            //log.info(str);
        }

        public int call() throws IOException, Exception {
            if(outLog) requestInfo();

            setUrl();
            Response res = null;
            if(method.equals("GET"))    res = GET(this);
            else                        res = POST(this);

            if(outLog)  responseInfo(res);
            
            System.out.println("############### call() ");
            //Map<String,Object> map = OutApiParse.getDatas(res);
            this.data = OutApiParse.getBody(res);
            this.code = OutApiParse.getCode(res);
            //System.out.println(map);
            return this.code;
        }
        
        private void setUrl() {
        	switch (this.mode) {
			case "A_ACCESS": this.url = LibConst.A_ACCESS;
				break;
			case "A_GET": this.url = LibConst.A_GET;
				break;
			case "A_OUT": this.url = LibConst.A_OUT;
				break;
			case "M_SAVE": this.url = LibConst.M_SAVE;
				break;
			case "M_LIST": this.url = LibConst.M_LIST;
				break;
			default:
				break;
			}
        }
        
        public Object getData() {
        	return this.data;
        }
        public int getCode() {
        	return this.code;
        }
    }

    private static Response GET(SkmSSO b) throws IOException, Exception {
    	String p = objectToParam(b.parameter, b.secretKey);
    	
        Builder builder = new Builder()
                                .url(LibConst.Domain+b.url + p)
                                .method(b.method, null);
        builder.addHeader("site", b.siteId);
        builder.addHeader("secretKey", b.secretKey);
        return send(builder.build());
    }
    
    private static Response POST(SkmSSO b) throws IOException, Exception {
    	String p = objectToParam(b.parameter, b.secretKey);

    	MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = RequestBody.create(mediaType, "");
        Builder builder = new Builder()
                                .url(LibConst.Domain+b.url + p)
                                .method(b.method, body);
        builder.addHeader("Site", b.siteId);
        builder.addHeader("SecretKey", b.secretKey);
        return send(builder.build());
    }
    
    private static String objectToParam(Map<String, Object> parameter, String secret) throws UnsupportedEncodingException, Exception {
    	String p = "?";
    	StringBuilder mapAsString = new StringBuilder("{");
	    for (String key : parameter.keySet()) {
	    	String val = URLEncoder.encode(seed.encrypt((String) parameter.get(key), secret));
    		if(!"?".equals(p)) p = p + "&";
    		
    		p = p + key + "="+val;
	    }
    	return p;
    }
    private static Response send(Request request) throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        Response response = client.newCall(request).execute();
        return response;
    }
}
