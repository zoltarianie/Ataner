package animade.ataner;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

/**
 * @author Sergey Tarasevich (nostra13[at]gmail[dot]com)
 */
public class GaleriaDetal extends BaseActivity {

	int gal_id = 0;

	private static final String STATE_POSITION = "STATE_POSITION";
	protected ImageLoader imageLoader = ImageLoader.getInstance();
	
	DisplayImageOptions options;

	ViewPagerCustomDuration pager;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.galeria_detal);
		
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			gal_id = extras.getInt("galeria_id");
		}
		
	  	JSONArray aListImage = Ataner.aListImage.get(gal_id);
	  	String[] imageUrls = new String[aListImage.length()];
	  	
		for (int i = 0; i < aListImage.length(); i++) {
			try {
				JSONObject rec = aListImage.getJSONObject(i);
				imageUrls[i] = rec.getString("image");
			} catch (JSONException e1) {
				e1.printStackTrace();
			}
		}

		options = new DisplayImageOptions.Builder()
			.showImageForEmptyUri(R.drawable.ic_empty)
			.showImageOnFail(R.drawable.ic_error)
			.resetViewBeforeLoading()
			.cacheOnDisc()
			.imageScaleType(ImageScaleType.EXACTLY)
			.bitmapConfig(Bitmap.Config.RGB_565)
			.displayer(new FadeInBitmapDisplayer(300))
			.build();

		pager = (ViewPagerCustomDuration) findViewById(R.id.pager);
		pager.setAdapter(new ImagePagerAdapter(imageUrls));
		pager.setCurrentItem(0);
		//pager.setPageTransformer(true, new ZoomOutPageTransformer());
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		outState.putInt(STATE_POSITION, pager.getCurrentItem());
	}

	private class ImagePagerAdapter extends PagerAdapter {

		private String[] images;
		private LayoutInflater inflater;

		ImagePagerAdapter(String[] images) {
			this.images = images;
			inflater = getLayoutInflater();
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			((ViewPager) container).removeView((View) object);
		}

		@Override
		public void finishUpdate(View container) {
		}

		@Override
		public int getCount() {
			return images.length;
		}

		@Override
		public Object instantiateItem(ViewGroup view, int position) {
			View imageLayout = inflater.inflate(R.layout.item_pager_image, view, false);
			ImageView imageView = (ImageView) imageLayout.findViewById(R.id.image);
			final ProgressBar spinner = (ProgressBar) imageLayout.findViewById(R.id.loading);

			imageLoader.displayImage(images[position], imageView, options, new SimpleImageLoadingListener() {
				@Override
				public void onLoadingStarted(String imageUri, View view) {
					spinner.setVisibility(View.VISIBLE);
				}

				@Override
				public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
					String message = null;
					switch (failReason.getType()) {
						case IO_ERROR:
							message = "Input/Output error";
							break;
						case DECODING_ERROR:
							message = "Image can't be decoded";
							break;
						case NETWORK_DENIED:
							message = "Downloads are denied";
							break;
						case OUT_OF_MEMORY:
							message = "Out Of Memory error";
							break;
						case UNKNOWN:
							message = "Unknown error";
							break;
					}
					Toast.makeText(GaleriaDetal.this, message, Toast.LENGTH_SHORT).show();

					spinner.setVisibility(View.GONE);
				}

				@Override
				public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
					spinner.setVisibility(View.GONE);
				}
			});

			((ViewPager) view).addView(imageLayout, 0);
			return imageLayout;
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view.equals(object);
		}

		@Override
		public void restoreState(Parcelable state, ClassLoader loader) {
		}

		@Override
		public Parcelable saveState() {
			return null;
		}

		@Override
		public void startUpdate(View container) {
		}
	}
}
/*
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;

public class GaleriaDetal extends Activity {
	
	int gal_id = 0;
    LinearLayout myGallery;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.galeria_detal);

		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			gal_id = extras.getInt("galeria_id");
		}
		
		// adowanie miniaturek
		DownloadWebpageText dwt = new DownloadWebpageText();
		dwt.addListener(new HelloListener() {
			@Override
		    public void someoneSaidHello() {  }
		});
		dwt.execute("p");	
		
		// Ladowanie pierwszego duzego zdiecia
		String ferstImg = "";
		JSONArray aListImage = Ataner.aListImage.get(gal_id);
		try {
			JSONObject rec = aListImage.getJSONObject(0);
			ferstImg = rec.getString("image");
		} catch (JSONException e) {
			e.printStackTrace();
		}
			
		DownloadBigImg dbi = new DownloadBigImg();
		dbi.addListener(new HelloListener() {
			@Override
		    public void someoneSaidHello() {  }
		});
		dbi.execute(ferstImg);
    }
	
	protected void sekliknol(int id) {
		try {
			JSONArray aListImage = Ataner.aListImage.get(gal_id);
			JSONObject rec = aListImage.getJSONObject(id);
			
			// �adowanie duzego obrazka 
			DownloadBigImg dbi = new DownloadBigImg();
			dbi.addListener(new HelloListener() {
				@Override
			    public void someoneSaidHello() {  }
			});
			dbi.execute(rec.getString("image"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	//-----------------------------------------
	// net big

	private class DownloadBigImg extends AsyncTask<String, Integer, Boolean> {
		protected void onProgressUpdate(Integer... progress) {}
		protected void onPostExecute(Boolean result) {
			RelativeLayout dell1 = (RelativeLayout) findViewById(R.id.progress_box);
			dell1.getHandler().post(new Runnable() {
			    public void run() {
			    	RelativeLayout dell1 = (RelativeLayout) findViewById(R.id.progress_box);
			    	dell1.setVisibility(View.GONE);
			    }
			});
		}

		List<HelloListener> listeners = new ArrayList<HelloListener>();
	    public void addListener(HelloListener toAdd) { listeners.add(toAdd); }
	    public void sayHello() { for (HelloListener hl : listeners) hl.someoneSaidHello(); }
	    
	    ImageView imgBig;
	    
		@Override
		protected Boolean doInBackground(String... urls) {
			RelativeLayout dell1 = (RelativeLayout) findViewById(R.id.progress_box);
			dell1.getHandler().post(new Runnable() {
			    public void run() {
			    	RelativeLayout dell1 = (RelativeLayout) findViewById(R.id.progress_box);
			    	dell1.setVisibility(View.VISIBLE);
			    }
			});
			
			try {
				imgBig = (ImageView) findViewById(R.id.imgBig);
        	  	
				URL url = new URL(urls[0]);
				Bitmap bm = BitmapFactory.decodeStream(url.openConnection().getInputStream());
				
				class OneShotTask implements Runnable {
					Bitmap bm;
			        OneShotTask(Bitmap s) { bm = s; }
			        public void run() {
			        	imgBig.setImageBitmap(bm);
			        }
			    }
				imgBig.getHandler().post(new OneShotTask(bm));
				
        	} catch (MalformedURLException e) {
        		e.printStackTrace();
        	} catch (IOException e) {
        		e.printStackTrace();
        	}

			sayHello();
			
			return true;
		}
	}
	
	//-----------------------------------------
	// net miniaturki

  	View insertPhoto(String myurl) throws MalformedURLException, IOException{
		URL url = new URL(myurl);
		Bitmap bm = BitmapFactory.decodeStream(url.openConnection().getInputStream());
		
		LinearLayout layout = new LinearLayout(getApplicationContext());
		layout.setLayoutParams(new LayoutParams(250, 250));
		layout.setGravity(Gravity.CENTER);
		
		ImageView imageView = new ImageView(getApplicationContext());
		imageView.setLayoutParams(new LayoutParams(220, 220));
		imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
		imageView.setImageBitmap(bm);
		
		layout.addView(imageView);
		return layout;
  	}
  	
	private class DownloadWebpageText extends AsyncTask<String, Integer, Boolean> {
		protected void onProgressUpdate(Integer... progress) {}
		protected void onPostExecute(Boolean result) { }

		List<HelloListener> listeners = new ArrayList<HelloListener>();
	    public void addListener(HelloListener toAdd) { listeners.add(toAdd); }
	    public void sayHello() { for (HelloListener hl : listeners) hl.someoneSaidHello(); }
	    
	    LinearLayout myGallery;
	    
		@Override
		protected Boolean doInBackground(String... urls) {			
			try {
				myGallery = (LinearLayout)findViewById(R.id.mygallery);
        	  	JSONArray aListThumb = Ataner.aListThumb.get(gal_id);
				
				for (int i = 0; i < aListThumb.length(); i++) {
					String Thumb = "";
					try {
						JSONObject rec = aListThumb.getJSONObject(i);
						Thumb = rec.getString("thumb");
					} catch (JSONException e1) {
						e1.printStackTrace();
					}
					
					if(!Thumb.equals("")){
						class OneShotTask implements Runnable {
							View imgMin;
					        OneShotTask(View s) { imgMin = s; }
					        public void run() {
						    	myGallery.addView(imgMin);
					        }
					    }
						View vImgMin = insertPhoto(Thumb, i);
						myGallery.getHandler().post(new OneShotTask(vImgMin));
					}
				}
				
        	} catch (MalformedURLException e) {
        		e.printStackTrace();
        	} catch (IOException e) {
        		e.printStackTrace();
        	}

			sayHello();
			
			return true;
		}

	  	View insertPhoto(String myurl, int id) throws MalformedURLException, IOException{
			URL url = new URL(myurl);
			Bitmap bm = BitmapFactory.decodeStream(url.openConnection().getInputStream());
			
			LinearLayout layout = new LinearLayout(getApplicationContext());
			layout.setLayoutParams(new LayoutParams(250, 250));
			layout.setGravity(Gravity.CENTER);
			layout.setId(id);
			
			ImageView imageView = new ImageView(getApplicationContext());
			imageView.setLayoutParams(new LayoutParams(220, 220));
			imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
			imageView.setImageBitmap(bm);
			
			layout.setOnClickListener(new OnClickListener() {
				@Override
	            public void onClick(View v) {
					sekliknol(v.getId());
	            }
	        });
			
			layout.addView(imageView);
			return layout;
	  	}
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
    	
        switch (item.getItemId()) {
        case android.R.id.home:
        	myIntent = new Intent(getApplicationContext(), Ataner.class);
        	break;
        case R.id.menu_poz1:
        	myIntent = new Intent(getApplicationContext(), Aktualnosci.class);
        	break;
        case R.id.menu_poz2:
        	myIntent = new Intent(getApplicationContext(), Administracja.class);
        	break;
        case R.id.menu_poz3:
        	myIntent = new Intent(getApplicationContext(), AdresyWspolnot.class);
        	break;
        case R.id.menu_poz4:
        	myIntent = new Intent(getApplicationContext(), BiuroSprzedazy.class);
        	break;
        case R.id.menu_poz5:
        	myIntent = new Intent(getApplicationContext(), Galeria.class);
        	break;
        case R.id.menu_poz8:
        	myIntent = new Intent(getApplicationContext(), NumeryKont.class);
        	break;
	    case R.id.menu_poz0:
	    	myIntent = new Intent(getApplicationContext(), Ataner.class);
	    	break;
        }
        
        startActivity(myIntent);
		overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
		
        return super.onOptionsItemSelected(item);
    }
}
*/