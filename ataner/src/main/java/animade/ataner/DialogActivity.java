package animade.ataner;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

/**
 * <h3>Dialog Activity</h3>
 *
 * <p>This demonstrates the how to write an activity that looks like
 * a pop-up dialog.</p>
 */
public class DialogActivity extends Activity {
    /**
     * Initialization of the Activity after it is first created.  Must at least
     * call {@link android.app.Activity#setContentView setContentView()} to
     * describe what is to be displayed in the screen.
     */
    @Override
	protected void onCreate(Bundle savedInstanceState) {
        // Be sure to call the super class.
        super.onCreate(savedInstanceState);

        //requestWindowFeature(Window.FEATURE_LEFT_ICON);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        
        String tresc = "";
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			tresc = extras.getString("txt");
		}
		
		// See assets/res/any/layout/dialog_activity.xml for this
        // view layout definition, which is being set here as
        // the content of our screen.
        setContentView(R.layout.dialog_activity);
        getWindow().setTitle("Komunikat");

     // getWindow().setFeatureDrawableResource(Window.FEATURE_LEFT_ICON, android.R.drawable.ic_dialog_alert);
        
        TextView kom = (TextView)findViewById(R.id.text);
		kom.setText(tresc);
        
    }

	public void efCloseDialog(final View view){
		finish();
	}
}
