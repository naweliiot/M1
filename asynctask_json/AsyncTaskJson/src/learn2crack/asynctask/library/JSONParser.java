package learn2crack.asynctask.library;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.zip.GZIPInputStream;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;
import android.widget.Toast;

import android.util.Log;

public class JSONParser {

	static InputStream is = null;
	static JSONObject jObj = null;
	static String json = "";
	
	static JSONObject jPost = null;
	
	static String postdata = "";

	// constructor
	public JSONParser() {

	}

	public JSONObject getJSONFromUrl(String url) {

		// Making HTTP request
		try {
			// defaultHttpClient
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(url);

			HttpResponse httpResponse = httpClient.execute(httpPost);
			HttpEntity httpEntity = httpResponse.getEntity();
			is = httpEntity.getContent();			

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is, "iso-8859-1"), 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			is.close();
			json = sb.toString();
		} catch (Exception e) {
			Log.e("Buffer Error", "Error converting result " + e.toString());
		}

		// try parse the string to a JSON object
		try {
			jObj = new JSONObject(json);
		} catch (JSONException e) {
			Log.e("JSON Parser", "Error parsing data " + e.toString());
		}

		// return JSON String
		return jObj;

	}
	
	public JSONObject postJSONToUrl (String url,String realdata) {
        
		try {
		
			
		
        	HttpClient httpclient = new DefaultHttpClient();
        	
            HttpPost httppost = new HttpPost("http://192.168.178.30:4000/m2m/applications/HomeGatewayId/containers/HomeContainer/contentInstances");
            JSONObject jInnerObject = new JSONObject();
            JSONObject jOuterObject = new JSONObject();
            JSONObject jmidddleObject = new JSONObject();
            
            
        	
            // JSON data:
        	jInnerObject.put("$t", realdata);
        	jInnerObject.put("contentType", "application/json");
        	
        	jmidddleObject.put("content", jInnerObject);
        	
        	jOuterObject.put("contentInstance", jmidddleObject); 
        	
        	postdata = jOuterObject.toString();
        	
        	StringEntity se = new StringEntity(postdata);
        	httppost.setEntity(se);
        	Log.i("info", httppost.getURI().toString());

        	HttpResponse httpresponse = httpclient.execute(httppost);
        	
        	
        	
        } catch (Exception e) {
        	Log.e("JSON Parser", "Error posting data " + e.toString());
		}
		try {
			jPost = new JSONObject(postdata);
		} catch (JSONException e) {
			Log.e("JSON Parser", "Error returning to data " + e.toString());
		}

		// return JSON String
		return jPost;
	}
}
