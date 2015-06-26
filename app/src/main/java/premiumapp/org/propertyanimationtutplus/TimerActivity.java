package premiumapp.org.propertyanimationtutplus;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.ViewTreeObserver;
import android.widget.RelativeLayout;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class TimerActivity extends AppCompatActivity implements View.OnTouchListener {

    @InjectView(R.id.v0)
    View v0;
    @InjectView(R.id.v1)
    View v1;
    @InjectView(R.id.v2)
    View v2;
    @InjectView(R.id.v3)
    View v3;
    @InjectView(R.id.containerRL) RelativeLayout mFrame;

    View mCurrentTouchedView = null;

    View mOpenedView;

    int v1h, v2h, v3h, v1l, v2l, v3l; // highest and lowest positions of all movable views

    double delta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        ButterKnife.inject(this);

        v0.setOnTouchListener(this);
        v1.setOnTouchListener(this);
        v2.setOnTouchListener(this);
        v3.setOnTouchListener(this);

        setViewHeights();
    }

    private void setViewHeights() {

        mFrame.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @Override
            public void onGlobalLayout() {
                mFrame.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                int heightPixelsDp = mFrame.getHeight();

                int maxH = heightPixelsDp / 2;

                v1l = maxH;
                v2l = maxH + maxH / 3;
                v3l = maxH + maxH * 2 / 3;

                v1h = v1l - maxH * 2 / 3;
                v2h = v2l - maxH * 2 / 3;
                v3h = v3l - maxH * 2 / 3;

                initViewHeightAndMargin(v0, maxH, 0);
                initViewHeightAndMargin(v1, maxH, v1l);
                initViewHeightAndMargin(v2, maxH, v2l);
                initViewHeightAndMargin(v3, maxH, v3l);

                mOpenedView = v0;
            }
        });
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        if (v == mOpenedView) return false;

        mCurrentTouchedView = v;

        MarginLayoutParams lp = getMarginLayoutParams(mCurrentTouchedView);

        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:

                int position = lp.topMargin;

                double y = event.getRawY();

                delta = y - position;

                return true;

            case MotionEvent.ACTION_MOVE:

                double value = event.getRawY() - delta;

                switch (mCurrentTouchedView.getId()) {

                    case R.id.v1:

                        if (lp.topMargin >= v1h && lp.topMargin <= v1l) {

                            setMarginTop(mCurrentTouchedView, (int) value);
                        }
                        return adjustTopMargin(lp, v1h, v1l);

                    case R.id.v2:

                        if (lp.topMargin >= v2h && lp.topMargin <= v2l) {

                            setMarginTop(mCurrentTouchedView, (int) value);
                        }
                        return adjustTopMargin(lp, v2h, v2l);

                    case R.id.v3:

                        if (lp.topMargin >= v3h && lp.topMargin <= v3l) {

                            setMarginTop(mCurrentTouchedView, (int) value);
                        }
                        return adjustTopMargin(lp, v3h, v3l);

                    default:
                        return true;
                }
        }

        return true;
    }

    private void initViewHeightAndMargin(View v, int height, int marginTop) {

        MarginLayoutParams lp = getMarginLayoutParams(v);

        lp.height = height;
        lp.topMargin = marginTop;
        v.setLayoutParams(lp);
    }

    private void setMarginTop(View v, int value) {

        MarginLayoutParams lp = getMarginLayoutParams(v);

        lp.topMargin = value;

        v.setLayoutParams(lp);
    }

    private MarginLayoutParams getMarginLayoutParams(View v) {
        return (MarginLayoutParams) v.getLayoutParams();
    }

    private boolean adjustTopMargin(MarginLayoutParams lp, int h, int l) {
        lp.topMargin = lp.topMargin < h ? lp.topMargin = h : lp.topMargin > l ? l : lp.topMargin;
        return true;
    }
}
