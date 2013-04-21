package ar.com.nivel7.kernelgesturesbuilder;

import java.util.ArrayList;
import java.util.List;

import com.google.analytics.tracking.android.EasyTracker;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ListView;
public class Actions extends ListActivity /* implements
OnItemClickListener */ {

	public static final String[] titles = new String[] { "Strawberry",
        "Banana", "Orange", "Mixed" };

public static final String[] descriptions = new String[] {
        "It is an aggregate accessory fruit",
        "It is the largest herbaceous flowering plant", "Citrus Fruit",
        "Mixed Fruits" };

 public static final Integer[] images = { /* R.drawable.straw,
        R.drawable.banana, R.drawable.orange, R.drawable.mixed */ };
        

	ListView listView;
    List<ActionsRowItem> rowItems;
	  
      @Override
	  public void onCreate(Bundle savedInstanceState) {
		  
	    super.onCreate(savedInstanceState);

	    setContentView(R.layout.actions);
	    
	    rowItems = new ArrayList<ActionsRowItem>();
        for (int i = 0; i < titles.length; i++) {
            ActionsRowItem item = new ActionsRowItem(images[i], titles[i], descriptions[i]);
            rowItems.add(item);
        }
 
        listView = (ListView) findViewById(R.id.list);
        CustomListViewAdapter adapter = new CustomListViewAdapter(this,
                R.layout.actionsrow , rowItems);
        listView.setAdapter(adapter);
//        listView.setOnItemClickListener(this);
	    
	  }
      
      /*
      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position,
              long id) {
          Toast toast = Toast.makeText(getApplicationContext(),
              "Item " + (position + 1) + ": " + rowItems.get(position),
              Toast.LENGTH_SHORT);
          toast.setGravity(Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0);
          toast.show();
      }
      */
      
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
