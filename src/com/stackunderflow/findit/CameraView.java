package stackUnderflow.testfind;

import com.google.android.glass.app.Card;

import android.content.Context;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class CameraView extends SurfaceView implements SurfaceHolder.Callback {
	private SurfaceHolder holder = null;
	private Camera camera = null;

	@SuppressWarnings("deprecation")
	public CameraView(Context context) {
		super(context);
		holder = this.getHolder();
		holder.addCallback(this);
		holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		if (camera != null) {

			this.setCameraParameters(camera);
			camera = Camera.open();
		}

		try {
			camera.setPreviewDisplay(holder);
		} catch (Exception e) {
			this.releaseCamera();
		}
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		if (camera != null) {
			camera.startPreview();
		}
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		this.releaseCamera();

	}

	public void releaseCamera() {
		if (camera != null) {
			camera.release();
			camera = null;
		}
	}

	public void setCameraParameters(Camera camera) {
		if (camera != null) {
			Parameters param = camera.getParameters();
			param.setPreviewFpsRange(30000, 30000);
			camera.setParameters(param);
		}
	}

}
