package com.pinku.holidaymemories;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import android.util.Log;

public class Picture extends java.util.HashMap<String,String> { 
	
	//private static final long serialVersionUID = 3973249260049876884L;
    private static final String host = "http://10.10.0.213";
    
    public Picture(){}
	public Picture(String imageName, String description, String location) {
          put("ImageName", imageName);
          put("Description", description);
          put("Location", location);
          
    }
	public static Picture[] getPictureList()
	{
		Picture[] list = null;
		try {
			JSONArray a = JsonParser.getJSONArrayFromUrl(host+"/WCFServicePriyanka/Service.svc/list");
		    list = new Picture[a.length()];
		    System.out.println("Hi"+a.length());
            for (int i=0; i<a.length(); i++) {
            	JSONObject ob=a.getJSONObject(i);
                list[i] = new Picture(ob.getString("ImageName"),ob.getString("Description"),ob.getString("Location"));
            }
		} catch (Exception e) {
			Log.i("JSON", "Error Picture list");
		}
        return list;
	
	}
	public static Picture getProduct(String code) {
		try {
            JSONArray item = JsonParser.getJSONArrayFromUrl(host+"/WCFServicePriyanka/Service.svc/retrieve/"+code);
            JSONObject ob = item.getJSONObject(0);
            return (new Picture(ob.getString("ImageName"),
                		ob.getString("Description"),
                		ob.getString("Location")));
                		
		} catch (Exception e) {
			Log.i("JSON", "Error getProduct");
		}
        return null;
	}

	public static List<Picture> getFullProductList() {
	    List<Picture> list = new ArrayList<Picture>();
		try {
			JSONArray a = JsonParser.getJSONArrayFromUrl(host+"/WCFServicePriyanka/Service.svc/list");
            for (int i=0; i<a.length(); i++) {
                String pcode = a.getString(i);
                list.add(getProduct(pcode));
            }
		} catch (Exception e) {
			Log.i("JSON", "Error Full Product list");
		}
        return list;
	}
	
	public static String update(Picture p) {
        JSONObject product = new JSONObject();
        try {
        	 product.put("ImageName", p.get("ImageName"));
             product.put("Description",p.get("Description"));
             product.put("Location", p.get("Location"));
            
            
        } catch (Exception e) {
        }
        String result = JsonParser.postStream(host+"/WCFServicePriyanka/Service.svc/update", product.toString());
        return result;
	}
	public static String delete(String p)
	{
		String result = JsonParser.getStream(host+"/WCFServicePriyanka/Service.svc/delete/"+ p);
		return result;
	}
	public static String insert(Picture p) {
        JSONObject product = new JSONObject();
        try {
            product.put("ImageName", p.get("ImageName"));
            product.put("Description",p.get("Description"));
            product.put("Location", p.get("Location"));
           
        } catch (Exception e) {
        }
        String result = JsonParser.postStream(host+"/WCFServicePriyanka/Service.svc/add", product.toString());
        return result;
	}
}