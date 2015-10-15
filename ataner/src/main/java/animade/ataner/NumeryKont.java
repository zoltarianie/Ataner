package animade.ataner;

import java.util.HashMap;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.content.pm.ActivityInfo;
import android.text.Html;

public class NumeryKont extends BaseActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.numery_kont);

		// --------------------------------------------------
		// rysowanie aktualnosci
		
		LinearLayout ma = (LinearLayout) findViewById(R.id.numery_kont_viev);
		
		for (int i = 0; i < Ataner.globalAR_adresy.size(); i++) {
			HashMap<String, String> lis = Ataner.globalAR_adresy.get(i);
			
			LinearLayout LLnewPoz = (LinearLayout) getLayoutInflater().inflate(R.layout.poz_lis_nk, null);
			LLnewPoz.setId(i);
			
			TextView box_txt = (TextView) LLnewPoz.findViewById(R.id.nkt1);
			String s = "<b>"+lis.get("adres")+"<br>"+lis.get("bank")+"</b><br>"+lis.get("konto1")+"<br>"+lis.get("konto2").replaceFirst("\n", "<br>")+"";
			box_txt.setText(Html.fromHtml(s));
			
			LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
			float d = this.getResources().getDisplayMetrics().density;
			params.setMargins((int)(20*d), 0, (int)(20*d), (int)(5*d));
			ma.addView(LLnewPoz, params);
		}
	}
}