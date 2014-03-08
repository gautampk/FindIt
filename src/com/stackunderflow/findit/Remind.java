package src.com.stackunderflow.findit;

import android.app.Activity;
import android.os.Bundle;

public class Remind extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_find);

        startActivity(procImg.launchNav("test0"));
    }
}