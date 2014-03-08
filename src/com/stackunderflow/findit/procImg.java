package com.stackunderflow.findit;

import java.io.IOException;

import android.content.Intent;
import android.location.Location;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.view.ContextThemeWrapper;

public class procImg extends ContextThemeWrapper {

	public void launchNav(String filename) {
        final String filepath = Environment.getExternalStorageDirectory().getPath()+"/FindIt/"+filename+".jpg";
		
		Location imgLoc = exif2Loc(filepath);
		String lat = ""+imgLoc.getLatitude();
		String lng = ""+imgLoc.getLongitude();
		Intent dir = new Intent(Intent.ACTION_VIEW);
		dir.setData(Uri.parse("google.navigation:q="+lat+", "+lng));
		startActivity(dir);
	}
	

	private Location exif2Loc(String flNm) {
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
		
	private dmsToDbl(String sDMS){
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
