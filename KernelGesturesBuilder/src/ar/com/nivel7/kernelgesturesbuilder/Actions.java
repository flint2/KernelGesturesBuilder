package ar.com.nivel7.kernelgesturesbuilder;

import com.google.analytics.tracking.android.EasyTracker;

import android.app.ListActivity;
import android.os.Bundle;
public class Actions extends ListActivity {

	  @Override
	  public void onCreate(Bundle savedInstanceState) {
		  
	    super.onCreate(savedInstanceState);

	    setContentView(R.layout.actions);
	    
	    
	    
	  }
	  
	  @Override
	  public void onStart() {
	    super.onStart();
	    EasyTracker.getInstance().activityStart(this); 
	  }

	  @Override
	  public void onStop() {
	    super.onStop();
	    EasyTracker.getInstance().activityStop(this);
	  }
	  
}
