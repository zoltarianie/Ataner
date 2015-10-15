package animade.ataner;

import java.util.HashMap;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.text.Html;
import android.view.View;

public class AktualnosciDetal extends BaseActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {	
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);	
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aktualnosci_detal);
		
		int news_id = 0;
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			news_id = extras.getInt("news_id");
		}
		
		HashMap<String, String> lis = Ataner.globalAR_news.get(news_id);

		TextView news_title = (TextView) findViewById(R.id.news_title);
		news_title.setText(Html.fromHtml(lis.get("title")));
		
		TextView news_txt = (TextView) findViewById(R.id.news_txt);
		news_txt.setText(Html.fromHtml(lis.get("contents")));

		/*
		news_txt.setText(Html.fromHtml(lis.get(2)));
		float d = this.getResources().getDisplayMetrics().density;
		String sCss = ""
				+ "\n body {padding:0; margin: 0; text-align:left; font-size:14px; color: #09428f; font-family: HelveticaNeue-Light;}"
				+ "\n hr.separator {color: #4f91df; height: 2px; background: #4f91df; clear: both;  height: 1px; margin: 0px; border: none; float: none;}"
				+ "\n div.section  {display:block; padding:20px 30px;}"
				+ "\n strong {font-family:HelveticaNeue-Bold;}";
		String text = "<html><head>"
		          + "<style type='text/css'>"
		         // + sCss 
		          + "</style></head>"
		          + "<body>"
		          + Ataner.globalAR_sprzedaz.get(1)
		          + "</body></html>";
		tBs1.loadData(text, "text/html", "UTF-8");
		*/
	}
	
	public void dialogClicked(final View view){
		int news_id = 0;
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			news_id = extras.getInt("news_id");
		}
		
		HashMap<String, String> lis = Ataner.globalAR_news.get(news_id);
		Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(lis.get("url")));
		startActivity(browserIntent);
	}
}