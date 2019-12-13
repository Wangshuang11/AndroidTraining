package org.turings.turings.index.gw.CharRobort;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class RobotMessage {
	//private  String APIKEY= "577173e62a2ff6627b62e94d663b449c";  不限次数
    private String APIKEY= "6158df7624bf4e429db50c0dde66c77d";
	private StringBuffer sb;
	private jsonData jsData;
	
	/** ����ͼ�������ƽ̨�ӿ� 
	*/ 
	public  void main(String userStr) throws IOException {

	    String INFO = URLEncoder.encode(userStr, "utf-8");
	    String getURL = "http://www.tuling123.com/openapi/api?key=" + APIKEY + "&info=" + INFO;
	    URL getUrl = new URL(getURL);
	    HttpURLConnection connection = (HttpURLConnection) getUrl.openConnection();
	    connection.connect(); 

	    // ȡ������������ʹ��Reader��ȡ 
	    BufferedReader reader = new BufferedReader(new InputStreamReader( connection.getInputStream(), "utf-8"));
	    sb = new StringBuffer();
	    String line = "";
	    while ((line = reader.readLine()) != null) { 
	        sb.append(line); 
	    } 
	    reader.close(); 
	    // �Ͽ����� 
	    connection.disconnect();
	}
	
	public String getString()
	{
		Gson gson = new Gson();
		jsData = new jsonData();
		jsData = gson.fromJson(sb.toString(), jsonData.class);
		return jsData.getText();
	}
}
