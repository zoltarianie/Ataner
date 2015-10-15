package animade.ataner;

import java.util.HashMap;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.text.Html;
import android.view.View;

public class BiuroSprzedazy extends BaseActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.biuro_sprzedazy);

		HashMap<String, String> lis = Ataner.globalAR_sprzedaz.get(0);
		
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

		for(int i=0; i<aTel.length; i++){
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

		for(int i=0; i<aFax.length; i++){
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
		
		for(int i=0; i<aEmail.length; i++){
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
	
	public void efKlikUrl(final View view){
		HashMap<String, String> lis = Ataner.globalAR_sprzedaz.get(0);
		
		Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(lis.get("url")));
		startActivity(browserIntent);
	}
	
	public void dialogClicked(final View view){
		HashMap<String, String> lis = Ataner.globalAR_sprzedaz.get(0);
		
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
}