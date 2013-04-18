/***
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

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import com.google.analytics.tracking.android.EasyTracker;

public class LauchActivities extends ListActivity {
  AppAdapter adapter=null;
  private ProgressDialog pd = null;
  PackageManager pm = null;
  List<ResolveInfo> launchables = null;
  
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.launchactivities);

    this.pd = ProgressDialog.show(this, "Loading Applications...", "Please Wait...",  true, false);
    
    new ListActivityTask().execute("");
    
    adapter=new AppAdapter(pm, launchables);
    
    	
  }
  
  private class ListActivityTask extends AsyncTask<String, Void, Object> {
      protected Object doInBackground(String... args) {
      
          pm=getPackageManager();
          Intent main=new Intent(Intent.ACTION_MAIN, null);
              
          main.addCategory(Intent.CATEGORY_LAUNCHER);

          launchables=pm.queryIntentActivities(main, 0);
          
          Collections.sort(launchables,
                           new ResolveInfo.DisplayNameComparator(pm)); 
      
          return "";
      }

      protected void onPostExecute(Object result) {
    	  
          if (LauchActivities.this.pd != null) {
        	  LauchActivities.this.pd.dismiss();
          }
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
  
  @Override
  protected void onListItemClick(ListView l, View v,
                                 int position, long id) {
    ResolveInfo launchable=adapter.getItem(position);
    ActivityInfo activity=launchable.activityInfo;
    ComponentName name=new ComponentName(activity.applicationInfo.packageName,
                                         activity.name);
    Intent i=new Intent(Intent.ACTION_MAIN);
    
    i.addCategory(Intent.CATEGORY_LAUNCHER);
    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
    i.setComponent(name);
    
    startActivity(i);    
  }
  
  class AppAdapter extends ArrayAdapter<ResolveInfo> {
    private PackageManager pm=null;
    
    AppAdapter(PackageManager pm, List<ResolveInfo> apps) {
      super(LauchActivities.this, R.layout.row, apps);
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
      return(getLayoutInflater().inflate(R.layout.row, parent, false));
    }
    
    private void bindView(int position, View row) {
      TextView label=(TextView)row.findViewById(R.id.label);
      
      label.setText(getItem(position).loadLabel(pm));
      
      ImageView icon=(ImageView)row.findViewById(R.id.icon);
      icon.setImageDrawable(getItem(position).loadIcon(pm));
    }
  }
}
