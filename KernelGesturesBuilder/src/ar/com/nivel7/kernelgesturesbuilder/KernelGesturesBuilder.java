package ar.com.nivel7.kernelgesturesbuilder;

import android.os.Bundle;
import android.app.Activity;
// import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;

public class KernelGesturesBuilder extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(new MTView(this));
		// setContentView(R.layout.activity_kernel_gestures_builder);
	}

	/*
	 * @Override public boolean onCreateOptionsMenu(Menu menu) {
	 * getMenuInflater().inflate(R.menu.activity_kernel_gestures_builder, menu);
	 * return true; }
	 */
}