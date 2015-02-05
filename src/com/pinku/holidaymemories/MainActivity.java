package com.pinku.holidaymemories;

import java.io.File;
import java.io.FileInputStream;

import com.pinku.pic2.R;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;


public class MainActivity extends Activity implements OnClickListener{
	ImageView image;
	ImageButton b;
	
	String imagename;
	String picname;
	String description;
	String picloc;
	EditText descripEditText;
	Location location=new Location();
	File file = null;
	Uri uri = null;
	boolean supply = true;
	
	
	final static int CAPTURE_IMAGE_REQUEST_CODE = 101;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		b = (ImageButton) findViewById(R.id.BtnUpload);
		ImageButton capButton= (ImageButton)findViewById(R.id.BtnCapture);
		descripEditText=(EditText)findViewById(R.id.editText1);
		ImageButton albumButton=(ImageButton)findViewById(R.id.BtnAlbum);
		albumButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent=new Intent(MainActivity.this,PicList.class);
				startActivity(intent);
				
			}
		});
		
		
		
		capButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				capturePhoto();
				
			}
		});
		b.setOnClickListener(this);
		image = (ImageView) findViewById(R.id.imageView1);
	}

	
	public void capturePhoto() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (supply) {
        	imagename=("PIC_" +String.valueOf(System.currentTimeMillis()) + ".jpg");
            file = new File("/storage/sdcard1/Pictures", imagename);
            
            
            
            uri = Uri.fromFile(file);
            picname=uri.toString();
            picname=picname.substring(6,picname.length());
            intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, uri);
        }
        startActivityForResult(intent, CAPTURE_IMAGE_REQUEST_CODE);
    }
	
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAPTURE_IMAGE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
            	Bitmap bmp = null;
           	    if (data == null) {
           	    	try {
       		            FileInputStream fis = new FileInputStream(file);
       		            bmp = BitmapFactory.decodeStream(fis);
           	    	} catch (Exception e) {
           	    	}
                } else {
                	uri = data.getData();
            		bmp = (Bitmap) data.getExtras().get("data"); 
            	}
                image.setImageBitmap(bmp);
            }
        }
    }

	@Override
	public void onClick(View arg0) {
		description=descripEditText.getText().toString();
		picloc= location.getLocation(picname);
		new AsyncTask<Uri, Void, Void>(){
			
			@Override
			protected Void doInBackground(Uri... arg) {
				
				Transfer.uploadFile(getRealPathFromURI(arg[0]));
				
				Picture picture=new Picture();
				picture.put("ImageName", imagename);
				picture.put("Description", description);
				picture.put("Location", picloc);
				picture.insert(picture);
				
				
				return null;
			}
			
			protected void onPostExecute(Void v) {
				//Toast.makeText(MainActivity.this, "Photo Uploaded", Toast.LENGTH_LONG).show();
				Toast.makeText(MainActivity.this,"Photo Uploaded", Toast.LENGTH_LONG).show();
			}
		}.execute(uri);
	}
	
	private String getRealPathFromURI(Uri uri)
	{
		String filePath;
        if (uri != null && "content".equals(uri.getScheme())) {
            Cursor cursor = getContentResolver().
              query(uri, new String[] { android.provider.MediaStore.Images.ImageColumns.DATA }, null, null, null);
            cursor.moveToFirst();   
            filePath = cursor.getString(0);
            cursor.close();
        }
        else {
            filePath = uri.getPath();
        }
        return(filePath);
    }
}
