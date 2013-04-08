/*
 * Kernel Gestures Builder 
 * Build Gestures definitions on android kernels that support gestures
 * Kernel feature developed by Tungstwenty
 * http://forum.xda-developers.com/showthread.php?t=1831254
 * 
 * Copyright (C) 2012  Guillermo Joandet

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.

*/

package ar.com.nivel7.kernelgesturesbuilder;

import com.google.analytics.tracking.android.EasyTracker;

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


    