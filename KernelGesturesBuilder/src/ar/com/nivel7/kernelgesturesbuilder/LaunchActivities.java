/***
 * Kernel Gestures Builder
 * Build Gestures definitions on android kernels that support gestures
 * Kernel feature developed by Tungstwenty
 * http://forum.xda-developers.com/showthread.php?t=1831254
 *  
 * Portions of code 
  Copyright (c) 2008-2012 CommonsWare, LLC
  Licensed under the Apache License, Version 2.0 (the "License"); you may not
  use this file except in compliance with the License. You may obtain a copy
  of the License at http://www.apache.org/licenses/LICENSE-2.0. Unless required
  by applicable law or agreed to in writing, software distributed under the
  License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS
  OF ANY KIND, either express or implied. See the License for the specific
  language governing permissions and limitations under the License.
  
  From _The Busy Coder's Guide to Android Development_
    http://commonsware.com/Android
*/

package ar.com.nivel7.kernelgesturesbuilder;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

import com.google.analytics.tracking.android.EasyTracker;

public class LaunchActivities extends Activity  {
  AppAdapter adapter=null;
  private ProgressDialog pd = null;
  PackageManager pm = null;
  List<ResolveInfo> launchables = null;
  public Intent main;

  @Override
  public void onCreate(Bundle savedInstanceState) {
	  
    super.onCreate(savedInstanceState);

    setContentView(R.layout.launchactivities);
    
    new ListActivityTask().execute("");
    
  }
  
  private class ListActivityTask extends AsyncTask<String, Void, Object> {
      protected void onPreExecute() {
    	  LaunchActivities.this.pd = ProgressDialog.show(LaunchActivities.this, getString(R.string.title_loading_applications) , getString(R.string.message_please_wait),  true, false);
      }

      protected Object doInBackground(String... args) {
    	  
    	    pm=getPackageManager();
    	    main=new Intent(Intent.ACTION_MAIN, null);
    	        
    	    main.addCategory(Intent.CATEGORY_LAUNCHER);
      
    	    launchables=pm.queryIntentActivities(main, 0);
    	    Collections.sort(launchables,
    	                     new ResolveInfo.DisplayNameComparator(pm)); 

    	    return "";
      }

      protected void onPostExecute(Object result) {
          if (pd != null) {
        	  pd.dismiss();
          }
          adapter=new AppAdapter(pm, launchables);
          ListView activities_list = (ListView) findViewById(R.id.activities_list);
          activities_list.setAdapter(adapter);
          // setListAdapter(adapter);
          activities_list.setOnItemClickListener(new OnItemClickListener() {
              public void onItemClick(AdapterView<?> arg0, View v, int position,
                      long id) {
            	  
            	    ResolveInfo launchable=adapter.getItem(position);
            	    ActivityInfo activity=launchable.activityInfo;
            	    String action = null;
            	    int gesturenumber = 0;
            	    String FILENAME = null;
            		FileOutputStream fos;
            	    
            	    gesturenumber = KernelGesturesBuilder.getGesturenumber();
            	    action="am start -n "+activity.applicationInfo.packageName+"/"+activity.name+"\n"; 
            	    
            	    FILENAME = "gesture-"+gesturenumber+".sh";
            		try {
            			fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);
            			fos.write(action.getBytes());
            			fos.close();
            		} catch (FileNotFoundException e) {
            			e.printStackTrace();
            		} catch (IOException e) {
            			e.printStackTrace();
            		}
            	    
            		LaunchActivities.this.finish();
            	    
              
              }
          });
          
      }
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
  

  class AppAdapter extends ArrayAdapter<ResolveInfo> {
    private PackageManager pm=null;
    
    AppAdapter(PackageManager pm, List<ResolveInfo> apps) {
      super(LaunchActivities.this, R.layout.launchactivityrow, apps);
      this.pm=pm;
    }
    
    @Override
    public View getView(int position, View convertView,
                          ViewGroup parent) {
      if (convertView==null) {
        convertView=newView(parent);
      }
      
      bindView(position, convertView);
      
      return(convertView);
    }
    
    private View newView(ViewGroup parent) {
      return(getLayoutInflater().inflate(R.layout.launchactivityrow, parent, false));
    }
    
    private void bindView(int position, View row) {
      TextView label=(TextView)row.findViewById(R.id.label);
      
      label.setText(getItem(position).loadLabel(pm));
      
      ImageView icon=(ImageView)row.findViewById(R.id.icon);
      icon.setImageDrawable(getItem(position).loadIcon(pm));
    }
  }
}
