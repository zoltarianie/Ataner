package animade.ataner;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.BitmapFactory;
import android.view.View;

public class Administracja extends BaseActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.administracja);

		// --------------------------------------------------
		// rysowanie aktualnosci
		
		LinearLayout ma = (LinearLayout) findViewById(R.id.administracja_viev);
		
		for (int i = 0; i < Ataner.globalAR_administracja.size(); i++) {
			HashMap<String, String> lis = Ataner.globalAR_administracja.get(i);
			
			LinearLayout LLnewPoz = (LinearLayout) getLayoutInflater().inflate(R.layout.poz_lis_adm, null);
			LLnewPoz.setId(i);
			
			ImageView box_img = (ImageView) LLnewPoz.findViewById(R.id.box_img);
			String fileName = lis.get("img_small").substring(lis.get("img_small").lastIndexOf("/")+1, lis.get("img_small").lastIndexOf("."));
			File file = getBaseContext().getFileStreamPath(fileName);
			if(file.exists()) {
				try {
					box_img.setImageBitmap(BitmapFactory.decodeStream(openFileInput(fileName)));
				} catch (FileNotFoundException e) {
					e.printStackTrace();
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
		Intent myIntent = null;
		myIntent = new Intent(this, AdministracjaDetal.class);
		myIntent.putExtra("administracja_id", view.getId());
		startActivity(myIntent);
		overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
	}

	//-----------------------------------------
	// net
	/*
	private class DownloadWebpageText extends AsyncTask<String, Integer, Boolean> {
		@Override
		protected Boolean doInBackground(String... urls) {
			try {
				for (int i = 0; i < Ataner.globalAR_administracja.size(); i++) {
					HashMap<String, String> lis = Ataner.globalAR_administracja.get(i);
					pobierz(lis.get("img_small"), i);
				}
				
				return true;
			} catch (IOException e) {
				return null;
			}
		}

		protected void onProgressUpdate(Integer... progress) {}
		protected void onPostExecute(Boolean result) { }
		
		private void pobierz(String myurl, int id) throws IOException {
			String fileName = myurl.substring(myurl.lastIndexOf("/")+1, myurl.lastIndexOf("."));
			File file = getBaseContext().getFileStreamPath(fileName);

			try {
				ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
				NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
				if (networkInfo != null && networkInfo.isConnected() && !file.exists()) {
					URL url = new URL(myurl);
					Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
					FileOutputStream out = openFileOutput(fileName, Context.MODE_PRIVATE);
					bmp.compress(Bitmap.CompressFormat.PNG, 90, out);
					out.flush();
					out.close();
				}
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} 

			file = getBaseContext().getFileStreamPath(fileName);
			
			if(file.exists()){
			    class OneShotTask implements Runnable {
			        String fileName;
			        OneShotTask(String s) { fileName = s; Log.d("kuba","z"); }
			        public void run() {
				    	LinearLayout ma = (LinearLayout) findViewById(R.id.administracja_viev);
						ImageView ii = (ImageView) ma.findViewWithTag(fileName);
						try {
							ii.setImageBitmap(BitmapFactory.decodeStream(openFileInput(fileName)));
						} catch (FileNotFoundException e) {
							e.printStackTrace();
						}
			        }
			    }
			    
			    LinearLayout ma = (LinearLayout) findViewById(R.id.administracja_viev);
			    ImageView ii = (ImageView) ma.findViewWithTag(fileName);
			    ii.getHandler().post(new OneShotTask(fileName));
			}
		}
	}	
	*/
}