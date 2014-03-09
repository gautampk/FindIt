package com.stackUnderflow.findit;

import java.io.File;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.FileObserver;
import android.provider.MediaStore;
import android.view.KeyEvent;
import android.view.MotionEvent;

import com.google.android.glass.media.CameraManager;
import com.google.android.glass.touchpad.Gesture;
import com.google.android.glass.touchpad.GestureDetector;

public class Remember extends Activity {
	protected int keyCode;
	private GestureDetector gesture = null;
	protected static final int TAKE_PICTURE = 1;
	private CameraView cameraView = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		cameraView = new CameraView(this);
		gesture = createGestureDetector(this);
		// setContentView(card1.toView());
		this.setContentView(cameraView);
		File f = new File(Environment.DIRECTORY_PICTURES + "Pictures/FindIt");
		if(!f.exists()){
			f.mkdir();
			f.mkdirs();
		}
	}

	protected void onStop() {
		super.onStop();
		cameraView = null;
		finish();
	}

	protected void onResume() {
		super.onResume();
		if (cameraView != null) {
			cameraView.releaseCamera();
		}
		this.setContentView(cameraView);
	}

	protected void onPause() {
		super.onPause();
		if (cameraView != null) {
			cameraView.releaseCamera();
		}
	}

	private GestureDetector createGestureDetector(Context context) {
		GestureDetector gestureDetector = new GestureDetector(context);

		gestureDetector.setBaseListener(new GestureDetector.BaseListener() {

			@Override
			public boolean onGesture(Gesture gesture) {
				if (cameraView != null) {
					if (gesture.equals(Gesture.TAP)) {
						Intent intent = new Intent(
								MediaStore.ACTION_IMAGE_CAPTURE);
						if (intent != null) {
							startActivityForResult(intent, TAKE_PICTURE);
						}
						return true;
					}
				}
				return false;
			}
		});
		return gestureDetector;

	}

	public boolean onGenericMotionEvent(MotionEvent event) {
		if (gesture != null) {
			return gesture.onMotionEvent(event);
		}
		return false;
	}

	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == TAKE_PICTURE && resultCode == RESULT_OK) {
			String picturePath = data
					.getStringExtra(CameraManager.EXTRA_PICTURE_FILE_PATH);
			processPictureWhenReady(picturePath);
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	private void processPictureWhenReady(final String picturePath) {
		final File pictureFile = new File(picturePath);
		if (pictureFile.exists()) {

		} else {
			pictureFile.mkdir();
			FileObserver observer = new FileObserver(pictureFile.getPath()) {
				private boolean isFileWritten;

				@Override
				public void onEvent(int event, String path) {

					if (!isFileWritten) {
						File affectedFile = new File(pictureFile, path);
						isFileWritten = (event == FileObserver.CLOSE_WRITE && affectedFile
								.equals(pictureFile));
						if (isFileWritten) {
							stopWatching();
							runOnUiThread(new Runnable() {
								@Override
								public void run() {
									processPictureWhenReady(picturePath);
								}
							});
						}
					}
				}
			};
			observer.startWatching();
		}

	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {

		/*
		 * if (true) { final String truth =
		 * "OLLIE IS A PRO, AND FIXED THIS FOR YOU ;)"; }
		 */

		if (keyCode == KeyEvent.KEYCODE_CAMERA) {
			return false;
		} else {
			return super.onKeyDown(keyCode, event);
		}
		// if (keyCode == KeyEvent.KEYCODE_DPAD_CENTER) {
		//
		// // Capture image, save to *filename (filename =
		// // Calendar.getInstance().time() ?)
		//
		// /*
		// * procImg.storeLocation(filename, this); //add EXIF geoloc data
		// */
		//
		// Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		// if (intent != null) {
		// startActivityForResult(intent, TAKE_PICTURE);
		// intent = null;
		// }
		// return true;
		// } else if (keyCode == KeyEvent.KEYCODE_BACK) {
		// onStop();
		// }

	}
}
