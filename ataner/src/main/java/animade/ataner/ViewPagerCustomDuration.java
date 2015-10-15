package animade.ataner;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class ViewPagerCustomDuration extends ViewPager {

    public ViewPagerCustomDuration(Context context) {
        super(context);
    }

    public ViewPagerCustomDuration(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
	
	private boolean enabled = true;
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
	    if (TouchImageView.b) {
	        return super.onTouchEvent(event);
	    }
	    return false;
	}
	
	@Override
	public boolean onInterceptTouchEvent(MotionEvent event) {
	    if (TouchImageView.b) {
	
	        try {
	            enabled = super.onInterceptTouchEvent(event);
	        } catch (Exception e) {
	        }
	        return enabled;
	    }
	    return false;
	}
	
	public void setPagingEnabled(boolean enabled) {
	    this.enabled = enabled;
	}

}