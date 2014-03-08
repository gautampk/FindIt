package com.stackunderflow.findit;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;

/**
 * Created by gautam on 08/03/14.
 */
public class Remind extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String filename = "test0";
        String filepath = Environment.getExternalStorageDirectory().getPath()+"/Pictures/FindIt/"+filename+".jpg";

        Intent intent = new Intent().setClass(this, Memory.class);
        startActivity(intent);

        startActivity(procImg.launchNav(filepath));
    }
}