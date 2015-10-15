package animade.ataner;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.RelativeLayout;

public class Ataner extends BaseActivity {

	public static ArrayList<HashMap<String, String>> globalAR_news = null;
	public static ArrayList<HashMap<String, String>> globalAR_sprzedaz = null;
	public static ArrayList<HashMap<String, String>> globalAR_administracja = null;
	public static ArrayList<HashMap<String, String>> globalAR_adresy = null;
	public static ArrayList<HashMap<String, String>> globalAR_galeria = null;
	public static List<JSONArray> aListImage = new ArrayList<JSONArray>();
	public static List<JSONArray> aListThumb = new ArrayList<JSONArray>();
	public boolean isConect = false;
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ataner);
		
		viewShow();
	}
	
	public void viewShow(){
		ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
		if (networkInfo != null && networkInfo.isConnected()) {
			isConect = true;
			DownloadWebpageText dwt = new DownloadWebpageText();
			dwt.addListener(new HelloListener() {
				@Override
			    public void someoneSaidHello() {  }
			});
			dwt.execute("p");
		} else {
			przeparsujDane();			

	    	RelativeLayout dell1 = (RelativeLayout) findViewById(R.id.progress_box);
	    	dell1.setVisibility(View.GONE);
	    	dell1.removeAllViews();
	    	((RelativeLayout)dell1.getParent()).removeView(dell1);
		}
	}
	
	public void dialogClicked(final View view){
		Intent myIntent = null;
		
		if(view.getTag().equals("aktualnosci")){
			myIntent = new Intent(getApplicationContext(), Aktualnosci.class);
		}
		if(view.getTag().equals("administracja")){
			myIntent = new Intent(getApplicationContext(), Administracja.class);
		}
		if(view.getTag().equals("adresy_wspolnot")){
			myIntent = new Intent(getApplicationContext(), AdresyWspolnot.class);
		}
		if(view.getTag().equals("biuro_sprzedazy")){
			myIntent = new Intent(getApplicationContext(), BiuroSprzedazy.class);
		}
		if(view.getTag().equals("galeria")){
			myIntent = new Intent(getApplicationContext(), Galeria.class);
		}
		if(view.getTag().equals("logowanie")){
			myIntent = new Intent(getApplicationContext(), Logowanie.class);
		}
		
		startActivity(myIntent);
		overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
	}

	//-----------------------------------------
	// parsowanie JOSONA
	public void przeparsujDane() {
		globalAR_news			= pobierzDane("news", new String[]{"date", "title", "contents", "url"});
		globalAR_sprzedaz		= pobierzDane("sprzedaz", new String[]{"title", "txt1", "txt2", "tel", "fax", "url", "email", "gps"});
		globalAR_administracja	= pobierzDane("administracja", new String[]{"title", "tel", "fax", "email", "gps", "img_small", "img_big", "txt1", "txt2", "adres"});
		globalAR_adresy			= pobierzDane("adresy", new String[]{"img_small","adres","gps","adres","bank","konto1","konto2"});
		globalAR_galeria		= pobierzDane("galeria", new String[]{"title"});//,"listImage","listThumb"
		
		RelativeLayout dell1 = (RelativeLayout) findViewById(R.id.progress_box);
		if(dell1.getHandler() != null){
			dell1.getHandler().post(new Runnable() {
			    public void run() {
			    	RelativeLayout dell1 = (RelativeLayout) findViewById(R.id.progress_box);
			    	dell1.setVisibility(View.GONE);
			    	dell1.removeAllViews();
			    	((RelativeLayout)dell1.getParent()).removeView(dell1);
			    }
			});
		}
	}

	private ArrayList<HashMap<String, String>> pobierzDane(String filName, String[] pola) {
		BufferedReader in = null;
		File file = getBaseContext().getFileStreamPath(filName);
		if(file.exists()){
			try {
				in = new BufferedReader(new FileReader(file));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}else{
			InputStream in_s = null;
			if(filName=="news") 			in_s = getResources().openRawResource(R.raw.news);
			if(filName=="sprzedaz") 		in_s = getResources().openRawResource(R.raw.sprzedaz);
			if(filName=="administracja")	in_s = getResources().openRawResource(R.raw.administracja);
			if(filName=="adresy")			in_s = getResources().openRawResource(R.raw.adresy);
			if(filName=="galeria")			in_s = getResources().openRawResource(R.raw.galeria);
			in = new BufferedReader(new InputStreamReader(in_s));
		} 
		
		String line = "";
		ArrayList<HashMap<String, String>> returnItems = new ArrayList<HashMap<String, String>>();
		
		try {
			while ((line = in.readLine()) != null) {
				try {
					JSONArray ja = new JSONArray(line);
			        for (int i = 0; i < ja.length(); i++) {
			        	JSONObject jo = (JSONObject) ja.get(i);
			            HashMap<String, String> listItems = new HashMap<String, String>();
	            		if(filName=="galeria"){
	            			JSONArray listImage = jo.getJSONArray("listImage");
	            			JSONArray listThumb = jo.getJSONArray("listThumb");
	            			aListImage.add(listImage);
	            			aListThumb.add(listThumb);

	            			String Thumb = "";
	            			try {
	            				JSONObject rec = listThumb.getJSONObject(0);
	            				Thumb = rec.getString("thumb");
	            			} catch (JSONException e1) {
	            				e1.printStackTrace();
	            			}
	            			
	            			pobierzMiniature(Thumb);
	            		}
	            		 
			            for(int p = 0; p<pola.length; p++){
			            	try {
				            	listItems.put(pola[p], jo.getString(pola[p]));
				            	if(pola[p]=="img_small"){
				            		pobierzMiniature(jo.getString(pola[p]));
				            	}
			            	} catch (JSONException e) {
						        e.printStackTrace();
						    }
			            }
			            returnItems.add(listItems);
			        }
        		} catch (JSONException e) {
        			e.printStackTrace();
        		}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return(returnItems);
	}

	private void pobierzMiniature(String myurl) {
		if(isConect) {
			String fileName = myurl.substring(myurl.lastIndexOf("/")+1, myurl.lastIndexOf("."));
			File file = getBaseContext().getFileStreamPath(fileName);
	
			if (!file.exists()) {
				try {
					URL url = new URL(myurl);
					Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
					FileOutputStream out = openFileOutput(fileName, Context.MODE_PRIVATE);
					bmp.compress(Bitmap.CompressFormat.PNG, 90, out);
					out.flush();
					out.close();
				} catch (MalformedURLException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	//-----------------------------------------
	// net
		
	private class DownloadWebpageText extends AsyncTask<String, Integer, Boolean> {

		List<HelloListener> listeners = new ArrayList<HelloListener>();

	    public void addListener(HelloListener toAdd) {
	        listeners.add(toAdd);
	    }
	    
	    public void sayHello() {
	        for (HelloListener hl : listeners)
	            hl.someoneSaidHello();
	    }
	    
		@Override
		protected Boolean doInBackground(String... urls) {
			try {
				String ver = pobieszVerZPliku();
				pobierz("data.php?var=version", "version");
				if(!pobieszVerZPliku().equals(ver)){
					pobierz("data.php?var=news", 			"news");
					pobierz("data.php?var=sprzedaz",		"sprzedaz");
					pobierz("data.php?var=administracja",	"administracja");
					pobierz("data.php?var=adresy",			"adresy");
					pobierz("data.php?var=galeria",			"galeria");
				}
			} catch (IOException e) {
				
			}
			
			przeparsujDane();
			sayHello();
			return true;
			
		}
		
		private String pobieszVerZPliku() {
			String verRet = "";

			File file = getBaseContext().getFileStreamPath("version");		
			try {
				BufferedReader in = new BufferedReader(new FileReader(file));
				verRet = in.readLine();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			return verRet;
		}

		protected void onProgressUpdate(Integer... progress) {}
		protected void onPostExecute(Boolean result) { }
		
		private void pobierz(String myurl, String filName) throws IOException {
			InputStream is = null;
			try {
				URL url = new URL("http://www.ataner.pl/_app/"+myurl);
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setReadTimeout(10000 /* milliseconds */);
				conn.setConnectTimeout(15000 /* milliseconds */);
				conn.setRequestMethod("GET");
				conn.setDoInput(true);
				// Starts the query
				conn.connect();
				is = conn.getInputStream();
				
				BufferedReader in = new BufferedReader(new InputStreamReader(is, "UTF-8"));
				String line = "";
				
				// zapisywanie do pliku (Internal Storage)
				FileOutputStream outputStream;
				try {
					outputStream = openFileOutput(filName, Context.MODE_PRIVATE);
					while ((line = in.readLine()) != null) {
						outputStream.write(line.getBytes());
					}
					outputStream.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			} finally {
				if (is != null) {
					is.close();
				} 
			}
		}
	}
}

interface HelloListener {
    public void someoneSaidHello();
}
