package com.pinku.holidaymemories;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import com.pinku.pic2.R;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MyAdapter extends ArrayAdapter<Picture> {
    private Context context;
    private Picture[] values;
    private final boolean useIcon = false;
    View parentView;

    public MyAdapter(Context context, Picture[] values) {
       super(context, R.layout.album, values);
       this.context = context;
       this.values = values;
    }

    @Override
    public View getView(int position, View v, ViewGroup parent) {
       if (v == null) {
           LayoutInflater inflater = ((Activity) context).getLayoutInflater();
           v = inflater.inflate(R.layout.album, null);
       }
       //TextView t1 = (TextView)v.findViewById(R.id.textView1);
       TextView t2 = (TextView)v.findViewById(R.id.textView2);
       final ImageView imageView = (ImageView)v.findViewById(R.id.imageView1);
       //t1.setText(values[position].get("ImageName"));
       t2.setText(values[position].get("Description"));
       t2.setTextColor(Color.WHITE);
      String key = values[position].get("ImageName");
         
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
       return v;    
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
}
