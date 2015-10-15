package animade.ataner;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.Html;
import android.view.View;

public class AdministracjaDetal extends BaseActivity {
	int administracja_id = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.administracja_detal);

		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			administracja_id = extras.getInt("administracja_id");
		}
		
		HashMap<String, String> lis = Ataner.globalAR_administracja.get(administracja_id);
		
		TextView tTyt = (TextView) findViewById(R.id.bs_t_tyt);
		TextView tBs1 = (TextView) findViewById(R.id.bs_t_bs1);
		TextView tBs2 = (TextView) findViewById(R.id.bs_t_bs2);
		
		tTyt.setText(Html.fromHtml(lis.get("title")));
		tBs1.setText(Html.fromHtml(lis.get("txt1")));
		tBs2.setText(Html.fromHtml(lis.get("txt2")));
		
		String[] aTel = lis.get("tel").split(",");
		String[] aFax = lis.get("fax").split(",");
		String[] aEmail = lis.get("email").split(",");

		LinearLayout telList = (LinearLayout) findViewById(R.id.telList);
		
		// ladowanie btn z tel
		for(int i=0; i<aTel.length; i++){
			if(!aTel[i].equals("")){
				LinearLayout LLnewPoz = (LinearLayout) getLayoutInflater().inflate(R.layout.poz_lis_adm, null);
				LLnewPoz.setTag("tel_"+i);
	
				ImageView box_img = (ImageView) LLnewPoz.findViewById(R.id.box_img);
				TextView box_txt = (TextView) LLnewPoz.findViewById(R.id.box_txt);
				
				box_img.setImageResource(R.drawable.mm_poz12);
				box_txt.setText(aTel[i].trim());
				
				LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
				float d = this.getResources().getDisplayMetrics().density;
				params.setMargins((int)(20*d), 0, (int)(20*d), (int)(5*d));
				
				telList.addView(LLnewPoz, params);
			}
		}

		for(int i=0; i<aFax.length; i++){
			if(!aFax[i].equals("")){
				LinearLayout LLnewPoz = (LinearLayout) getLayoutInflater().inflate(R.layout.poz_lis_adm, null);
				LLnewPoz.setTag("fax_"+i);
	
				ImageView box_img = (ImageView) LLnewPoz.findViewById(R.id.box_img);
				TextView box_txt = (TextView) LLnewPoz.findViewById(R.id.box_txt);
				
				box_img.setImageResource(R.drawable.mm_poz11);
				box_txt.setText(aFax[i].trim());
				
				LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
				float d = this.getResources().getDisplayMetrics().density;
				params.setMargins((int)(20*d), 0, (int)(20*d), (int)(5*d));
				
				telList.addView(LLnewPoz, params);
			}
		}
		
		for(int i=0; i<aEmail.length; i++){
			if(!aEmail[i].equals("")){
				LinearLayout LLnewPoz = (LinearLayout) getLayoutInflater().inflate(R.layout.poz_lis_adm, null);
				LLnewPoz.setTag("mai_"+i);
	
				ImageView box_img = (ImageView) LLnewPoz.findViewById(R.id.box_img);
				TextView box_txt = (TextView) LLnewPoz.findViewById(R.id.box_txt);
				
				box_img.setImageResource(R.drawable.mm_poz9);
				box_txt.setText(aEmail[i].trim());
				
				LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
				float d = this.getResources().getDisplayMetrics().density;
				params.setMargins((int)(20*d), 0, (int)(20*d), (int)(5*d));
				
				telList.addView(LLnewPoz, params);
			}
		}
		
		// pobierania obrazka nagwka
		if(!lis.get("img_big").equals("")){
			ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
			if (networkInfo != null && networkInfo.isConnected()) {
				DownloadBigImg dbi = new DownloadBigImg();
				dbi.addListener(new HelloListener() {
					@Override
				    public void someoneSaidHello() {  }
				});
				dbi.execute(lis.get("img_big"));
			}
		}
	}
	
	public void efKlikUrl(final View view){
		HashMap<String, String> lis = Ataner.globalAR_administracja.get(administracja_id);
		
		Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(lis.get("url")));
		startActivity(browserIntent);
	}
	
	public void dialogClicked(final View view){
		HashMap<String, String> lis = Ataner.globalAR_administracja.get(administracja_id);
		
		String s = (String) view.getTag();
		String num = s.substring(s.length()-1);
		
		if(s.substring(0, s.length()-1).equals("tel_")) {
			String[] aTel = lis.get("tel").split(",");
			String callTo = aTel[Integer.parseInt(num)].replace(" ","");
			
			Intent browserIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+callTo));
			startActivity(browserIntent);
		}

		if(s.substring(0, s.length()-1).equals("fax_")) {
			String[] aFax = lis.get("fax").split(",");
			String callTo = aFax[Integer.parseInt(num)].replace(" ","");
			
			Intent browserIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+callTo));
			startActivity(browserIntent);
		}
		
		if(s.substring(0, s.length()-1).equals("mai_")) {
			String[] aEmail = lis.get("email").split(",");
			Intent intent = new Intent(Intent.ACTION_SEND);
			intent.setType("plain/text");
			intent.putExtra(Intent.EXTRA_EMAIL, new String[] { aEmail[Integer.parseInt(num)] });
			intent.putExtra(Intent.EXTRA_SUBJECT, lis.get("title"));
			startActivity(Intent.createChooser(intent, ""));
		}
	}

	public void ecMap(final View view){
		HashMap<String, String> lis = Ataner.globalAR_administracja.get(administracja_id);
		
		String[] aGPS = lis.get("gps").split(",");
		
		Intent myIntent = new Intent(getApplicationContext(), Map.class);
		myIntent.putExtra("gps_x", aGPS[0]);
		myIntent.putExtra("gps_y", aGPS[1]);
		myIntent.putExtra("tyt", lis.get("adres"));
		startActivity(myIntent);
		overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
	}

	public void ecNrKont(final View view){
		Intent myIntent = new Intent(getApplicationContext(), NumeryKont.class);
		//myIntent.putExtra("administracja_id", view.getId());
		startActivity(myIntent);
		overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
	}
	
	//-----------------------------------------
	// net big

	private class DownloadBigImg extends AsyncTask<String, Integer, Boolean> {
		protected void onProgressUpdate(Integer... progress) {}
		protected void onPostExecute(Boolean result) { }

		List<HelloListener> listeners = new ArrayList<HelloListener>();
	    public void addListener(HelloListener toAdd) { listeners.add(toAdd); }
	    public void sayHello() { for (HelloListener hl : listeners) hl.someoneSaidHello(); }
	    
	    ImageView imgBig;
	    Bitmap bm;
	    
		@Override
		protected Boolean doInBackground(String... urls) {
			try {
				imgBig = (ImageView) findViewById(R.id.headImg);
				
				URL url = new URL(urls[0]);
				bm = BitmapFactory.decodeStream(url.openConnection().getInputStream());
				
				imgBig.getHandler().post(new Runnable() {
					public void run() {
						imgBig.setImageBitmap(bm);
					}
				});
        	} catch (MalformedURLException e) {
        		e.printStackTrace();
        	} catch (IOException e) {
        		e.printStackTrace();
        	}

			sayHello();
			
			return true;
		}
	}
}