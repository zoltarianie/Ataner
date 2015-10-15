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

public class AdresyWspolnot extends BaseActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.adresy_wspolnot);

		LinearLayout ma = (LinearLayout) findViewById(R.id.adresy_wspolnot_viev);
		
		for (int i = 0; i < Ataner.globalAR_adresy.size(); i++) {
			HashMap<String, String> lis = Ataner.globalAR_adresy.get(i);
			
			LinearLayout LLnewPoz = (LinearLayout) getLayoutInflater().inflate(R.layout.poz_lis_adm, null);
			LLnewPoz.setId(i);
			
			if(lis.get("img_small")!=null){
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
			}
			
			TextView box_txt = (TextView) LLnewPoz.findViewById(R.id.box_txt);
			box_txt.setText(lis.get("adres"));
			
			LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
			float d = this.getResources().getDisplayMetrics().density;
			int topMarg = 0;
			if(i==0) topMarg = (int)(10*d);
			params.setMargins((int)(20*d), topMarg, (int)(20*d), (int)(5*d));
			ma.addView(LLnewPoz, params);
		}
	}
	
	public void dialogClicked(final View view){
		HashMap<String, String> lis = Ataner.globalAR_adresy.get(view.getId());
		
		String[] aGPS = lis.get("gps").split(",");
		
		Intent myIntent = new Intent(getApplicationContext(), Map.class);
		myIntent.putExtra("gps_x", aGPS[0]);
		myIntent.putExtra("gps_y", aGPS[1]);
		myIntent.putExtra("tyt", lis.get("adres"));
		startActivity(myIntent);
		overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
	}
}