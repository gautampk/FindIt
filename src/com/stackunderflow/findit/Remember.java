package com.stackunderflow.findit;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.net.Uri;
import android.speech.RecognizerIntent;
import android.view.MotionEvent;
import android.view.View;
import java.sql.Timestamp;
import com.google.android.glass.app.Card;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import com.google.android.glass.media.CameraManager;

public class Remember extends Activity {
    public String filepath = Environment.getExternalStorageDirectory()+"/Pictures/FindIt/";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

        File f = new File(filepath);
        if(!f.exists()){
            f.mkdir();
            f.mkdirs();
        }

        Card cam = new Card(this);
        View camView = cam.toView();
        camView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                takePicture();

                return true;
            }
        });
        setContentView(camView);
	}

    private static final int TAKE_PICTURE_REQUEST = 1;

    private void takePicture() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, TAKE_PICTURE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == TAKE_PICTURE_REQUEST && resultCode == RESULT_OK) {
            String picturePath = data.getStringExtra(CameraManager.EXTRA_PICTURE_FILE_PATH);
            ArrayList<String> voiceResults = getIntent().getExtras().getStringArrayList(RecognizerIntent.EXTRA_RESULTS);
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}
