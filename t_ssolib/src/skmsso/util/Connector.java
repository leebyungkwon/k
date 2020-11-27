package skmsso.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Connector {
	public String get(String strUrl) {
		String msg = "";
		try {
			URL url = new URL(strUrl);
			HttpURLConnection con = (HttpURLConnection) url.openConnection(); 
			con.setConnectTimeout(5000); //������ ����Ǵ� Timeout �ð� ����
			con.setReadTimeout(5000); 	 // InputStream �о� ���� Timeout �ð� ����
			con.setRequestMethod("GET");
			con.setDoOutput(false); 

			StringBuilder sb = new StringBuilder();
			
			if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {
				BufferedReader br = new BufferedReader(
						new InputStreamReader(con.getInputStream(), "utf-8"));
				String line;
				while ((line = br.readLine()) != null) {
					sb.append(line).append("\n");
				}
				br.close();
				msg = sb.toString();
			} else {
				msg = con.getResponseMessage();
			}
		} catch (Exception e) {
			System.err.println(e.toString());
		}finally {
			return msg;
		}

	}
}
