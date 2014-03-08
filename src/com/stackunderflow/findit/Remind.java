package com.stackunderflow.findit;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;

/**
 * Created by gautam on 08/03/14.
 */
public class Remind extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_find);

        launchNav("test0");
    }

    public void launchNav(String filename) {
        String filepath = Environment.getExternalStorageDirectory().getPath()+"/DCIM/Camera/"+filename+".jpg";
        procImg processor = new procImg();

        Location imgLoc = processor.exif2Loc(filepath);
        String lat = ""+imgLoc.getLatitude();
        String lng = ""+imgLoc.getLongitude();
        Intent dir = new Intent(Intent.ACTION_VIEW);
        dir.setData(Uri.parse("google.navigation:q=" + lat + ", " + lng));
        startActivity(dir);
    }
}