package com.pinku.holidaymemories;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.PublicKey;

import com.pinku.pic2.R;

import android.R.string;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class UpdateDescption extends Activity {
String key;
Boolean useIcon=false;
String description;
String location;

private Context context;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_updatedetails);
		final ImageView imageView = (ImageView)findViewById(R.id.imageView1);
		key=getIntent().getExtras().get("ImageName").toString();
		 if (useIcon) {
	    	   Resources res = context.getResources();
	          int imageResource = res.getIdentifier("drawable/"+key, "drawable", context.getPackageName());
	          Bitmap bitmap = BitmapFactory.decodeResource(res, imageResource);
	          imageView.setImageBitmap(bitmap);
	       } else {
	    	   new AsyncTask<String, Void, Bitmap>(){
				 @Override
				 protected Bitmap doInBackground(String... arg) {
					return downloadImage(arg[0]);
				 }
	    		 @Override
	             protected void onPostExecute(Bitmap bitmap) {
	    			 imageView.setImageBitmap(bitmap);
	             }
	    	   }.execute(key);
	       }
		 description=getIntent().getExtras().getString("Description").toString();
		 EditText descEditText = (EditText)findViewById(R.id.editText1);
		 descEditText.setText(description);
		 ImageButton updateButton = (ImageButton)findViewById(R.id.imgupdate);
		 ImageButton deleButton=(ImageButton)findViewById(R.id.imgdelete);
		 ImageButton locationbButton=(ImageButton)findViewById(R.id.imglocation);
		 locationbButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Picture picture=new Picture();
				Picture p1 = picture.getProduct(key);
				location=p1.get("Location").toString();
				Uri uri=Uri.parse("geo:"+location);
				Intent intent= new  Intent(Intent.ACTION_VIEW,uri);
				startActivity(intent);
				
			}
		});
		 deleButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				deletePicture();
				
			}
		});
		 updateButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
			updateDescription();
			
				
			}
			
			
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.update_descption, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	public static Bitmap downloadImage(String key) {
        Bitmap bmImg = null;
        try {
        	URL url = new URL("http://10.10.0.213/WCFServicePriyanka/images/"+key);
            HttpURLConnection conn= (HttpURLConnection)url.openConnection();
            conn.setDoInput(true);
            conn.connect();
            InputStream is = conn.getInputStream();
            bmImg = BitmapFactory.decodeStream(is);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return bmImg;
    } 
	
public void updateDescription()
{
	
	Picture picture= new Picture();
	Picture p1 = picture.getProduct(key);
	EditText edd= (EditText) findViewById(R.id.editText1);

	p1.put("Description", edd.getText().toString());
	p1.update(p1);
	Toast.makeText(UpdateDescption.this,"Updated", Toast.LENGTH_LONG).show();
	
}
public void deletePicture()
{
	Picture picture= new Picture();
	Picture p1 = picture.getProduct(key);
	p1.delete(key);
	Toast.makeText(UpdateDescption.this,"Deleted", Toast.LENGTH_LONG).show();
}

	
}
