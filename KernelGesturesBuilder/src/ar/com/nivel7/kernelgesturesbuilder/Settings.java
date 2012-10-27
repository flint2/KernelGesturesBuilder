package ar.com.nivel7.kernelgesturesbuilder;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;


public class Settings extends Activity    {
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Display the fragment as the main content.
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new SettingsFragment())
                .commit();
    }
	
	@Override
	protected void onDestroy() {
		startActivity(new Intent(this, KernelGesturesBuilder.class ));
		super.onDestroy();
	}

}


    