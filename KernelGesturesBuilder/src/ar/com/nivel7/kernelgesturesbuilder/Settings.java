package ar.com.nivel7.kernelgesturesbuilder;

import android.app.Activity;
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
}


    