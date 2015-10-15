/*******************************************************************************
 * Copyright 2011-2013 Sergey Tarasevich
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package animade.ataner;

// import com.google.analytics.tracking.android.EasyTracker;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

/**
 * @author Sergey Tarasevich (nostra13[at]gmail[dot]com)
 */
public abstract class BaseActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public void onStart() {
		super.onStart();
		//EasyTracker.getInstance().activityStart(this); // Add this method.
	}

	@Override
	public void onStop() {
		super.onStop();
		//EasyTracker.getInstance().activityStop(this); // Add this method.
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_activity_a1, menu);
		return true;
	}

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	Intent myIntent = null;
    	
        if(item.getItemId() == android.R.id.home){
        	myIntent = new Intent(getApplicationContext(), Ataner.class);
        }
        if(item.getItemId() == R.id.menu_poz1) {
        	myIntent = new Intent(getApplicationContext(), Aktualnosci.class);
        }
        if(item.getItemId() == R.id.menu_poz2) {
        	myIntent = new Intent(getApplicationContext(), Administracja.class);
        }
        if(item.getItemId() == R.id.menu_poz3) {
        	myIntent = new Intent(getApplicationContext(), AdresyWspolnot.class);
        }
        if(item.getItemId() == R.id.menu_poz4) {
        	myIntent = new Intent(getApplicationContext(), BiuroSprzedazy.class);
        }
        if(item.getItemId() == R.id.menu_poz5) {
        	myIntent = new Intent(getApplicationContext(), Galeria.class);
        }
        if(item.getItemId() == R.id.menu_poz8) {
        	myIntent = new Intent(getApplicationContext(), NumeryKont.class);
        }
        if(item.getItemId() == R.id.menu_poz0) {
	    	myIntent = new Intent(getApplicationContext(), Ataner.class);
        }
        if(item.getItemId() == R.id.menu_poz10) {
	    	myIntent = new Intent(getApplicationContext(), Logowanie.class);
	    }
        
        startActivity(myIntent);
		overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
		
        return super.onOptionsItemSelected(item);
    }
}
