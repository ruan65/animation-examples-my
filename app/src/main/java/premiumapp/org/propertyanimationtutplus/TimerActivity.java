package premiumapp.org.propertyanimationtutplus;

import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class TimerActivity extends AppCompatActivity implements View.OnTouchListener {

    @InjectView(R.id.v0) TextView v0;
    @InjectView(R.id.v1) TextView v1;
    @InjectView(R.id.v2) TextView v2;
    @InjectView(R.id.v3) TextView v3;
    @InjectView(R.id.containerRL) RelativeLayout mFrame;

    private GestureDetectorCompat mDetector;

    TextView mCurrentTouchedView = null;
    TextView mOpenedView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        ButterKnife.inject(this);

        v0.setOnTouchListener(this);
        v1.setOnTouchListener(this);
        v2.setOnTouchListener(this);
        v3.setOnTouchListener(this);

        mDetector = new GestureDetectorCompat(this, new LocalGestureListener());

        setViewHeights();
    }

    private void setViewHeights() {

        mFrame.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @Override
            public void onGlobalLayout() {
                mFrame.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                int heightPixelsDp = mFrame.getHeight();

                int maxH = heightPixelsDp / 2;

                Log.d("ml", "heightPixelsDp = " + heightPixelsDp);

                initViewHeightAndMargin(v0, maxH, 0);
                initViewHeightAndMargin(v1, maxH, maxH);
                initViewHeightAndMargin(v2, maxH, maxH + maxH / 3);
                initViewHeightAndMargin(v3, maxH, maxH + maxH * 2 / 3);

                mOpenedView = v0;
            }
        });
    }

    private void initViewHeightAndMargin(View v, int height, int marginTop) {

        ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) v.getLayoutParams();

        lp.height = height;
        lp.topMargin = marginTop;
        v.setLayoutParams(lp);
    }

    private void changeMarginTop(View v, int delta) {

        ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) v.getLayoutParams();

        lp.topMargin += delta;

        v.setLayoutParams(lp);
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {

        if (v == mOpenedView) return false;

        mCurrentTouchedView = (TextView) v;
        mDetector.onTouchEvent(event);

        return true;
    }

    private class LocalGestureListener extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {

            if (mCurrentTouchedView.getId() != R.id.v0) {
                changeMarginTop(mCurrentTouchedView, (int) (-distanceY * 0.7));
            }

            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

//            Log.d("ml", "fl x = " + e2.getX() + " Y = " + e2.getY());
//            Log.d("ml", "fl x1 = " + e1.getX() + " Y1 = " + e1.getY());
            return true;
        }
    }
}
