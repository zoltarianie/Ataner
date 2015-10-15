package animade.ataner;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.BitmapFactory;
import android.view.View;

public class Galeria extends BaseActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.galeria);
		
		LinearLayout ma = (LinearLayout) findViewById(R.id.galeria_viev);

		for (int i = 0; i < Ataner.globalAR_galeria.size(); i++) {
			HashMap<String, String> lis = Ataner.globalAR_galeria.get(i);
			
			//JSONArray aListImage = Ataner.aListImage.get(i);
			JSONArray aListThumb = Ataner.aListThumb.get(i);
			
			/*
			Log.d("kuba", a1.length()+"!");
			Log.d("kuba", a2.length()+"!");
			Log.d("kuba", "-------------------------");
			
			for (int i = 0; i < recs.length(); ++i) {
			    JSONObject rec = recs.getJSONObject(i);
			    int id = rec.getInt("id");
			    String loc = rec.getString("loc");
			}
			*/
			
			String Thumb = "";
			try {
				JSONObject rec = aListThumb.getJSONObject(0);
				Thumb = rec.getString("thumb");
			} catch (JSONException e1) {
				e1.printStackTrace();
			}
			
			LinearLayout LLnewPoz = (LinearLayout) getLayoutInflater().inflate(R.layout.poz_lis_adm, null);
			LLnewPoz.setId(i);
			
			if(!Thumb.equals("")){
				ImageView box_img = (ImageView) LLnewPoz.findViewById(R.id.box_img);
				String fileName = Thumb.substring(Thumb.lastIndexOf("/")+1, Thumb.lastIndexOf("."));
				File file = getBaseContext().getFileStreamPath(fileName);
				if(file.exists()) {
					try {
						box_img.setImageBitmap(BitmapFactory.decodeStream(openFileInput(fileName)));
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					}
				}
			}
			
			TextView box_txt = (TextView) LLnewPoz.findViewById(R.id.box_txt);
			box_txt.setText(lis.get("title"));
			
			LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
			float d = this.getResources().getDisplayMetrics().density;
			int topMarg = 0;
			if(i==0) topMarg = (int)(10*d);
			params.setMargins((int)(20*d), topMarg, (int)(20*d), (int)(5*d));
			ma.addView(LLnewPoz, params);
		}
	}

	public void dialogClicked(final View view){
		ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
		if (networkInfo != null && networkInfo.isConnected()) {
			Intent myIntent = null;
			myIntent = new Intent(this, GaleriaDetal.class);
			myIntent.putExtra("galeria_id", view.getId());
			startActivity(myIntent);
			overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
		} else {
			Intent myIntent = null;
			myIntent = new Intent(this, DialogActivity.class);
			myIntent.putExtra("txt", "Aby przeglada galerie musisz mie poczenie z internetem.");
			startActivity(myIntent);
			overridePendingTransition(R.anim.dialog_a_in, R.anim.dialog_a_out);
		}
	}
}