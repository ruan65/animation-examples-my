package premiumapp.org.propertyanimationtutplus;

import android.graphics.Matrix;
import android.graphics.RectF;
import android.hardware.Camera;
import android.hardware.Camera.Size;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.Window;
import android.view.WindowManager;

import java.io.IOException;


public class CameraActivity extends AppCompatActivity {

    private SurfaceView mSurfaceView;
    private SurfaceHolder mHolder;
    private HolderCallback mHolderCallback;

    private Camera mCamera;

    final static int CAMERA_ID = 0;
    final static boolean FULL_SCREEN = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        int param = WindowManager.LayoutParams.FLAG_FULLSCREEN;
        getWindow().setFlags(param, param);

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_camera);

        mSurfaceView = (SurfaceView) findViewById(R.id.surfaceView);

        mHolder = mSurfaceView.getHolder();
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        mHolderCallback = new HolderCallback();
        mHolder.addCallback(mHolderCallback);
    }

    @Override
    protected void onResume() {
        super.onResume();

        mCamera = Camera.open(CAMERA_ID);
        definePreview(FULL_SCREEN);
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (mCamera != null) {
            mCamera.release();
            mCamera = null;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_camera, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        return id == R.id.action_settings || super.onOptionsItemSelected(item);

    }



    private void definePreview(boolean fullScreenMode) {

        Display display = getWindowManager().getDefaultDisplay();

        boolean landscapeMode = display.getWidth() > display.getHeight();

        Size size = mCamera.getParameters().getPreviewSize();

        RectF rDisplay = new RectF();
        RectF rPreview = new RectF();

        rDisplay.set(0, 0, display.getWidth(), display.getHeight());

        if (landscapeMode) {

            rPreview.set(0, 0, size.width, size.height);
        } else {
            rPreview.set(0, 0, size.height, size.width);
        }

        Matrix matrix = new Matrix();

        if (!fullScreenMode) {
            matrix.setRectToRect(rPreview, rDisplay, Matrix.ScaleToFit.START);
        } else {
            matrix.setRectToRect(rDisplay, rPreview, Matrix.ScaleToFit.START);
            matrix.invert(matrix);
        }

        matrix.mapRect(rPreview);

        mSurfaceView.getLayoutParams().height = (int) rPreview.bottom;
        mSurfaceView.getLayoutParams().width = (int) rPreview.right;
    }

    private void defineOrientation(int cameraId) {

        int rotation = getWindowManager().getDefaultDisplay().getRotation();
        int rotDegree = 0;

        switch (rotation) {

            case Surface.ROTATION_90:
                rotDegree = 90;
                break;
            case Surface.ROTATION_180:
                rotDegree = 180;
                break;
            case Surface.ROTATION_270:
                rotDegree = 270;
                break;
        }

        int calcDegree = 0;

        Camera.CameraInfo info = new Camera.CameraInfo();
        Camera.getCameraInfo(cameraId, info);

        if (info.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {

            calcDegree = ((360 - rotDegree) + info.orientation);

        } else if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {

            calcDegree = ((360 - rotDegree) - info.orientation);
            calcDegree += 360;
        }

        calcDegree %= 360;

        mCamera.setDisplayOrientation(calcDegree);
    }

    private class HolderCallback implements SurfaceHolder.Callback {

        @Override
        public void surfaceCreated(SurfaceHolder holder) {

            try {

                mCamera.setPreviewDisplay(holder);
                mCamera.startPreview();

            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            mCamera.stopPreview();
            defineOrientation(CAMERA_ID);

            try {

                mCamera.setPreviewDisplay(mHolder);
                mCamera.startPreview();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            // ignore at this moment
        }
    }
}



























