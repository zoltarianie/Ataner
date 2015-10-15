package animade.ataner;

// import com.google.analytics.tracking.android.EasyTracker;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import android.os.Bundle;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.SupportMapFragment;

public class Map extends FragmentActivity {
	
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
	protected void onCreate(Bundle savedInstanceState) {
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.map);
		
		Bundle extras = getIntent().getExtras();
		
		Double gps_x = Double.parseDouble(extras.getString("gps_x"));
		Double gps_y = Double.parseDouble(extras.getString("gps_y"));
		String tyt = extras.getString("tyt");
		
		GoogleMap googleMap;
		googleMap = ((SupportMapFragment)(getSupportFragmentManager().findFragmentById(R.id.map))).getMap();
		LatLng latLng = new LatLng(gps_x, gps_y);
		googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
		googleMap.addMarker(new MarkerOptions().position(latLng).title(tyt).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));//.snippet("This is my spot!")
		googleMap.getUiSettings().setCompassEnabled(true);
		googleMap.getUiSettings().setZoomControlsEnabled(true);
		googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 13));
		
		setTitle(tyt);
	}

	public void ecNrKont(final View view){
		Intent myIntent = new Intent(getApplicationContext(), NumeryKont.class);
		startActivity(myIntent);
		overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
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