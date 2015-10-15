package animade.ataner;

import java.util.HashMap;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.view.View;

public class Aktualnosci extends BaseActivity {
	
    @Override
	protected void onCreate(Bundle savedInstanceState) {	
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aktualnosci);
		
		// --------------------------------------------------
		// rysowanie aktualnosci
		
		LinearLayout ma = (LinearLayout) findViewById(R.id.aktualnosci_viev);
		for (int i = 0; i < Ataner.globalAR_news.size(); i++) {
			HashMap<String, String> lis = Ataner.globalAR_news.get(i);
			
			LinearLayout LLnewPoz = (LinearLayout) getLayoutInflater().inflate(R.layout.poz_lis_akt, null);
			LLnewPoz.setId(i);

			TextView TV_data = (TextView) LLnewPoz.findViewById(R.id.TV_data);
			TV_data.setText(lis.get("date"));

			TextView TV_tresc = (TextView) LLnewPoz.findViewById(R.id.TV_tresc);
			TV_tresc.setText(lis.get("title"));
			
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
		myIntent = new Intent(getApplicationContext(), AktualnosciDetal.class);
		myIntent.putExtra("news_id", view.getId());
		startActivity(myIntent);
		overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
	}
}



