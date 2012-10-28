package ar.com.nivel7.kernelgesturesbuilder;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;


public class KernelGesturesBuilder extends Activity {

	private MTView KernelGesturesMTView;
	private SharedPreferences sharedPrefs;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
		KernelGesturesMTView = new MTView(this);
		KernelGesturesMTView.setGridcolumns(Integer.parseInt(sharedPrefs.getString("grid_columns", "3")));
		KernelGesturesMTView.setGridrows(Integer.parseInt(sharedPrefs.getString("grid_rows", "5")));
		setContentView(KernelGesturesMTView);
		
		
	}

	@Override
	public void onNewIntent(Intent intent) {
		KernelGesturesMTView.setGridcolumns(Integer.parseInt(sharedPrefs.getString("grid_columns", "3")));
		KernelGesturesMTView.setGridrows(Integer.parseInt(sharedPrefs.getString("grid_rows", "5")));
		setContentView(KernelGesturesMTView);
		KernelGesturesMTView.redrawGrid();
	}
	
	  @Override public boolean onCreateOptionsMenu(Menu menu) {
	  getMenuInflater().inflate(R.menu.activity_kernel_gestures_builder, menu);
	  return true; }
	 
	
      @Override
	  public boolean onOptionsItemSelected(MenuItem item)
	  {
	        switch (item.getItemId())
	        {
	 	    case R.id.menu_settings:
	        	startActivity(new Intent(this, Settings.class ));
	        	return true;
	        default:
	            return super.onOptionsItemSelected(item);
	        }
	   }
	        
}