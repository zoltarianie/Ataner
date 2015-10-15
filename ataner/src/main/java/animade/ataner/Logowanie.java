package animade.ataner;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;

public class Logowanie extends BaseActivity {
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.logowanie);
		
		File file = getBaseContext().getFileStreamPath("login");
		if(file.exists()){
			try {
				BufferedReader in = new BufferedReader(new FileReader(file));
				String line = "";
				int l=0;
				while ((line = in.readLine()) != null) {
					if(l==0){
						EditText uname = (EditText)findViewById(R.id.username);
						uname.setText(line);
					}
					if(l==1){
						EditText pword = (EditText)findViewById(R.id.password);
						pword.setText(line);
					}
					l++;
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} 
		}

		RelativeLayout dell1 = (RelativeLayout) findViewById(R.id.progress_box);
		dell1.setVisibility(View.GONE);
	}
	
	public void efLoguj(final View view){
        postLoginData();
	}
    
    public void postLoginData() {
		RelativeLayout dell1 = (RelativeLayout) findViewById(R.id.progress_box);
		dell1.setVisibility(View.VISIBLE);
		
    	EditText uname = (EditText)findViewById(R.id.username);
    	String username = uname.getText().toString();
    	EditText pword = (EditText)findViewById(R.id.password);
    	String password = pword.getText().toString();
    	
    	// zapisywanie do pliku (Internal Storage)
		FileOutputStream outputStream;
		try {
			outputStream = openFileOutput("login", Context.MODE_PRIVATE);
			outputStream.write(username.getBytes());
			outputStream.write(System.getProperty("line.separator").getBytes());
			outputStream.write(password.getBytes());
			outputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// brak netu
    	ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
		if (networkInfo != null && networkInfo.isConnected()) {
		} else {
			Intent myIntent = null;
			myIntent = new Intent(this, DialogActivity.class);
			myIntent.putExtra("txt", "Aby si zalogowa musisz mie poczenie z internetem.");
			startActivity(myIntent);
			overridePendingTransition(R.anim.dialog_a_in, R.anim.dialog_a_out);
			
			return;
		}
		
		// cos tam z policja
    	try {
	        Class strictModeClass=Class.forName("android.os.StrictMode");
	        Class strictModeThreadPolicyClass=Class.forName("android.os.StrictMode$ThreadPolicy");
	        Object laxPolicy = strictModeThreadPolicyClass.getField("LAX").get(null);
	        Method method_setThreadPolicy = strictModeClass.getMethod( "setThreadPolicy", strictModeThreadPolicyClass );
	        method_setThreadPolicy.invoke(null,laxPolicy);
	    } catch (Exception e) {

	    }

    	HttpClient httpclient = new DefaultHttpClient();
    	HttpPost httppost = new HttpPost("https://mieszkancy.ataner.pl/portal/142.html?service=login");
    	
    	try {        	
        	List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
        	nameValuePairs.add(new BasicNameValuePair("username", username));
        	nameValuePairs.add(new BasicNameValuePair("password", password));
        	httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
        	
        	// Execute HTTP Post Request
        	HttpResponse response = httpclient.execute(httppost);
            //int status = response.getStatusLine().getStatusCode();
            HttpEntity httpEntity = response.getEntity();
            String  str = inputStreamToString(httpEntity.getContent()).toString();
            
            if (str.indexOf("Wyloguj") != -1) {
    			Intent myIntent = null;
    			myIntent = new Intent(this, LogowanieDetal.class);
    			myIntent.putExtra("l", username);
    			myIntent.putExtra("p", password);
    			startActivity(myIntent);
    			overridePendingTransition(R.anim.dialog_a_in, R.anim.dialog_a_out);
            }else{
    			Intent myIntent = null;
    			myIntent = new Intent(this, DialogActivity.class);
    			myIntent.putExtra("txt", "Logowanie nie powiodo si wprowa jeszcze raz dane i sprbuj ponownie.");
    			startActivity(myIntent);
    			overridePendingTransition(R.anim.dialog_a_in, R.anim.dialog_a_out);
            }
        } catch (ClientProtocolException e) {
        	e.printStackTrace();
        } catch (IOException e) {
        	e.printStackTrace();
        }
    	
    	dell1.setVisibility(View.GONE);
    }
        
    private StringBuilder inputStreamToString(InputStream is) {
    	String line = "";
    	StringBuilder total = new StringBuilder();
    	// Wrap a BufferedReader around the InputStream
    	BufferedReader rd = new BufferedReader(new InputStreamReader(is));
    	// Read response until the end
    	try {
        	while ((line = rd.readLine()) != null) { 
            	total.append(line); 
            }
        } catch (IOException e) {
        	e.printStackTrace();
        }
    	// Return full string
    	return total;
    }
}