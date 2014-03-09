package com.stackunderflow.findit;

import java.io.IOException;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;

public class procImg{
	
    public void storeLocation(String filename, Context context) {

    	Location currentloc = null;
    	if (LocationGrabber.canGetLocation()){
    		currentloc.setLatitude(LocationGrabber.latitude);
    		currentloc.setLongitude(LocationGrabber.longitude);
    		locToExif(filename, currentloc);
    	}
    }
    
    public static Intent launchNav(String filename) {
        String filepath = Environment.getExternalStorageDirectory()+"/Pictures/FindIt/"+filename;
        procImg processor = new procImg();

        Location imgLoc = exifToLoc(filepath);
        String lat = ""+imgLoc.getLatitude();
        String lng = ""+imgLoc.getLongitude();
        Intent dir = new Intent(Intent.ACTION_VIEW);
        dir.setData(Uri.parse("google.navigation:q=" + lat + ", " + lng));
        return dir;
    }
	
	private void locToExif(String filename, Location loc) {
		try {
			ExifInterface ef = new ExifInterface(filename);
		    ef.setAttribute(ExifInterface.TAG_GPS_LATITUDE, decToDMS(loc.getLatitude()));
		    ef.setAttribute(ExifInterface.TAG_GPS_LONGITUDE,decToDMS(loc.getLongitude()));
		    if (loc.getLatitude() > 0) 
		    	ef.setAttribute(ExifInterface.TAG_GPS_LATITUDE_REF, "N"); 
		    else              
		    	ef.setAttribute(ExifInterface.TAG_GPS_LATITUDE_REF, "S");
		    if (loc.getLongitude()>0) 
		    	ef.setAttribute(ExifInterface.TAG_GPS_LONGITUDE_REF, "E");    
		    else             
		    	ef.setAttribute(ExifInterface.TAG_GPS_LONGITUDE_REF, "W");
		    ef.saveAttributes();
		} catch (IOException e) {}         
	}
		
	private String decToDMS(double coord) {
		coord = coord > 0 ? coord : -coord;						// -105.9876543 -> 105.9876543
		String sOut = Integer.toString((int)coord) + "/1,";		// 105/1,
		coord = (coord % 1) * 60;         						// .987654321 * 60 = 59.259258
		sOut = sOut + Integer.toString((int)coord) + "/1,";		// 105/1,59/1,
		coord = (coord % 1) * 60000;             				// .259258 * 60000 = 15555
		sOut = sOut + Integer.toString((int)coord) + "/1000";	// 105/1,59/1,15555/1000
		return sOut;
	}

	private static Location exifToLoc(String flNm) {
        String sLat = "", sLatR = "", sLon = "", sLonR = "";
        try {
        ExifInterface ef = new ExifInterface(flNm);
        sLat  = ef.getAttribute(ExifInterface.TAG_GPS_LATITUDE);
        sLon  = ef.getAttribute(ExifInterface.TAG_GPS_LONGITUDE);
        sLatR = ef.getAttribute(ExifInterface.TAG_GPS_LATITUDE_REF);
        sLonR = ef.getAttribute(ExifInterface.TAG_GPS_LONGITUDE_REF);
        } catch (IOException e) {return null;}

        double lat = dmsToDbl(sLat);
        if (lat > 180.0) return null;
        double lon = dmsToDbl(sLon);
        if (lon > 180.0) return null;

        lat = sLatR.contains("S") ? -lat : lat;
        lon = sLonR.contains("W") ? -lon : lon;

        Location loc = new Location("exif");
        loc.setLatitude(lat);
        loc.setLongitude(lon);
        return loc;
    }
		
	private static Double dmsToDbl(String sDMS){
		double dRV = 999.0;
		try {
			String[] DMSs = sDMS.split(",", 3);
			String s[] = DMSs[0].split("/", 2);
		    dRV = (new Double(s[0])/new Double(s[1]));
		    s = DMSs[1].split("/", 2);
		    dRV += ((new Double(s[0])/new Double(s[1]))/60);
		    s = DMSs[2].split("/", 2);
		    dRV += ((new Double(s[0])/new Double(s[1]))/3600);
		    } catch (Exception e) {
		}
        return dRV;
	}
}