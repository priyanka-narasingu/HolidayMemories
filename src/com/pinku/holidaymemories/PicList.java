package com.pinku.holidaymemories;



import java.util.List;





import android.R;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import android.os.StrictMode.ThreadPolicy;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class PicList extends ListActivity {
	
	Picture[] pic;
	Picture p= new Picture();
    private void getData() {
    	
      pic=p.getPictureList().clone();
    }
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		StrictMode.setThreadPolicy(ThreadPolicy.LAX);
	
		getData();
        setListAdapter(new MyAdapter(this, pic));
        getWindow().getDecorView().setBackgroundColor(Color.BLACK);
        
   	}
	@Override
    public void onListItemClick(ListView l, View v, int position, long id) {
          Picture s = (Picture) getListAdapter().getItem(position);
          //Toast.makeText(getApplicationContext(), s.get("ImageName") + " selected",
          //               Toast.LENGTH_LONG).show();
          Intent intent=new Intent(PicList.this, UpdateDescption.class);
          intent.putExtra("Description", s.get("Description"));
          intent.putExtra("ImageName", s.get("ImageName"));
          startActivity(intent);
          
    }

}
