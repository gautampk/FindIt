package com.stackunderflow.findit;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by gautam on 08/03/14.
 */
public class Remind extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find);

        launchNav("text0");
    }

    private void launchNav(String filename){
        procImg processor = new procImg();
        processor.launchNav(filename);
    }
}