package com.pinku.holidaymemories;


import java.io.IOException;
import android.media.ExifInterface;



public class Location {
	Float Latitude, Longitude;
	String lat, log;
	String picloc;
public String getLocation(String filename)
{
	
				ExifInterface exif;
				try {
					exif = new ExifInterface(filename);
			
				
				
				 
				
				 String LATITUDE = exif.getAttribute(ExifInterface.TAG_GPS_LATITUDE);
				 String LATITUDE_REF = exif.getAttribute(ExifInterface.TAG_GPS_LATITUDE_REF);
				 String LONGITUDE = exif.getAttribute(ExifInterface.TAG_GPS_LONGITUDE);
				 String LONGITUDE_REF = exif.getAttribute(ExifInterface.TAG_GPS_LONGITUDE_REF);
				 
				 
				 
				 if((LATITUDE !=null)
						   && (LATITUDE_REF !=null)
						   && (LONGITUDE != null)
						   && (LONGITUDE_REF !=null))
						 {
						  if(LATITUDE_REF.equals("N")){
						   Latitude = convertToDegree(LATITUDE);
						  }
						  else{
						   Latitude = 0 - convertToDegree(LATITUDE);
						  }
						  if(LONGITUDE_REF.equals("E")){
						   Longitude = convertToDegree(LONGITUDE);
						  }
						  else{
						   Longitude = 0 - convertToDegree(LONGITUDE);
						  }
						 }
				 lat=String.valueOf(Latitude);
				 log=String.valueOf(Longitude);
				 picloc=lat+","+log;
				 return picloc;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return picloc;
				}
				 
				 
}				 
private Float convertToDegree(String stringDMS){
	 Float result = null;
	 String[] DMS = stringDMS.split(",", 3);
	 String[] stringD = DMS[0].split("/", 2);
	    Double D0 = new Double(stringD[0]);
	    Double D1 = new Double(stringD[1]);
	    Double FloatD = D0/D1;
	 String[] stringM = DMS[1].split("/", 2);
	 Double M0 = new Double(stringM[0]);
	 Double M1 = new Double(stringM[1]);
	 Double FloatM = M0/M1;
	 String[] stringS = DMS[2].split("/", 2);
	 Double S0 = new Double(stringS[0]);
	 Double S1 = new Double(stringS[1]);
	 Double FloatS = S0/S1;
	    result = new Float(FloatD + (FloatM/60) + (FloatS/3600));
	 return result;
	};

}
				
			 
				  
			
			
			
//			
	