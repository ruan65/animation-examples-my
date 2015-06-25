package premiumapp.org.propertyanimationtutplus;

import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewTreeObserver;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class TimerActivity extends AppCompatActivity implements View.OnTouchListener {

    @InjectView(R.id.tv1) TextView tv1;
    @InjectView(R.id.tv2) TextView tv2;
    @InjectView(R.id.tv3) TextView tv3;
    @InjectView(R.id.tv4) TextView tv4;
    @InjectView(R.id.containerRL) RelativeLayout container;

    LayoutParams lp1, lp2, lp3, lp4;

    private GestureDetectorCompat mDetector;

    TextView currentTouchedView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        ButterKnife.inject(this);

        tv1.setOnTouchListener(this);
        tv2.setOnTouchListener(this);
        tv3.setOnTouchListener(this);
        tv4.setOnTouchListener(this);

        mDetector = new GestureDetectorCompat(this, new LocalGestureListener());

        setViewHeights();
    }

    private void setViewHeights() {

        container.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @Override
            public void onGlobalLayout() {
                container.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                int heightPixels = container.getHeight();


                lp1 = tv1.getLayoutParams();
                lp2 = tv2.getLayoutParams();
                lp3 = tv3.getLayoutParams();
                lp4 = tv4.getLayoutParams();

                changeViewHeight(tv1, lp1, heightPixels * 2 / 5);
                changeViewHeight(tv2, lp2, heightPixels / 5);
                changeViewHeight(tv3, lp3, heightPixels / 5);
                changeViewHeight(tv4, lp4, heightPixels / 5);
            }
        });
    }

    private void changeViewHeight(View v, LayoutParams lp, int h) {
        lp.height = h;
        v.setLayoutParams(lp);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        currentTouchedView = (TextView) v;
        mDetector.onTouchEvent(event);

        return true;
    }

    private class LocalGestureListener extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {

            Log.d("ml", "y1 - y2 = " + (e1.getY() - e2.getY()));



            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

            Log.d("ml", "fl x = " + e2.getX() + " Y = " + e2.getY());
            Log.d("ml", "fl x1 = " + e1.getX() + " Y1 = " + e1.getY());
            return true;
        }
    }
}
