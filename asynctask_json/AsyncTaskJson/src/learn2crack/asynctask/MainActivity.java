package learn2crack.asynctask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import learn2crack.asynctask.library.JSONParser;



public class MainActivity extends Activity {
	TextView uid;
	TextView name1;
	TextView email1;
	Button Btngetdata;
	Button Btnpostdata;
	//URL to get JSON Array
	
	public static String sensordata = "switch on the light for testin";
	private static String url = "http://10.0.2.2/JSONNE";
	public static String openmtcurl = "http://192.168.178.30:4000/m2m/applications/HomeGatewayId/containers/HomeContainer/contentInstances";
	//JSON Node Names 
	private static final String TAG_USER = "user";
	private static final String TAG_ID = "id";
	private static final String TAG_NAME = "name";
	private static final String TAG_EMAIL = "email";
	
	JSONArray user = null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      
        if (android.os.Build.VERSION.SDK_INT > 9) {
        	StrictMode.ThreadPolicy policy = 
        	        new StrictMode.ThreadPolicy.Builder().permitAll().build();
        	StrictMode.setThreadPolicy(policy);
        	}
        
        setContentView(R.layout.activity_main);
        Btnpostdata = (Button)findViewById(R.id.postdata);
        Btngetdata = (Button)findViewById(R.id.getdata);
        Btngetdata.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View view) {
		         new JSONParse().execute();

				
			}
		});
        
        //post call
         Btnpostdata.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View view) {
				JSONParser jParser = new JSONParser();
				Log.i("info", "you are using testing function");
                
	    		// Getting JSON from URL
	    		JSONObject jsonPost= jParser.postJSONToUrl(openmtcurl,sensordata);
				//Toast.makeText(this,"post is being clicked", "Clicked on image",
		                //Toast.LENGTH_LONG).show();
			}
		});
        
    }


    
    private class JSONParse extends AsyncTask<String, String, JSONObject> {
    	 private ProgressDialog pDialog;
    	@Override
        protected void onPreExecute() {
            super.onPreExecute();
             uid = (TextView)findViewById(R.id.uid);
			 name1 = (TextView)findViewById(R.id.name);
			 email1 = (TextView)findViewById(R.id.email);
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Getting Data ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
            
    	}
    	
    	@Override
        protected JSONObject doInBackground(String... args) {
    		JSONParser jParser = new JSONParser();

    		// Getting JSON from URL
    		//JSONObject jsonPost= jParser.postJSONToUrl(openmtcurl,sensordata);
    		JSONObject json = jParser.getJSONFromUrl(url);
    		return json;
    	}
    	 @Override
         protected void onPostExecute(JSONObject json) {
    		 pDialog.dismiss();
    		 try {
    				// Getting JSON Array
    				user = json.getJSONArray(TAG_USER);
    				JSONObject c = user.getJSONObject(0);
    				
    				// Storing  JSON item in a Variable
    				String id = c.getString(TAG_ID);
    				String name = c.getString(TAG_NAME);
    				String email = c.getString(TAG_EMAIL);
    				
    				
    				//Set JSON Data in TextView
    				uid.setText(id);
    				name1.setText(name);
    				email1.setText(email);

    			
    		} catch (JSONException e) {
    			e.printStackTrace();
    		}

    		 
    	 }
    }
    
}
