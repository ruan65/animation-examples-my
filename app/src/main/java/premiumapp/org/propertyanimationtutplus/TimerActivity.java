package premiumapp.org.propertyanimationtutplus;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.ViewTreeObserver;
import android.view.animation.OvershootInterpolator;
import android.widget.RelativeLayout;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class TimerActivity extends AppCompatActivity implements View.OnTouchListener {

    private static final float sOvershoot = 2f;

    @InjectView(R.id.v0)
    View v0;
    @InjectView(R.id.v1)
    View v1;
    @InjectView(R.id.v2)
    View v2;
    @InjectView(R.id.v3)
    View v3;
    @InjectView(R.id.containerRL)
    RelativeLayout mFrame;

    View mOpenedView;

    int v1h, v2h, v3h, v1l, v2l, v3l; // highest and lowest positions of all movable views

    double delta, yBefore; // these are used in the MotionEvent switch

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        ButterKnife.inject(this);

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

                Log.d("ml", "max height" + maxH);


                initViewHeightAndMargin(v0, maxH, 0);
                initViewHeightAndMargin(v1, maxH, v1l);
                initViewHeightAndMargin(v2, maxH, v2l);
                initViewHeightAndMargin(v3, maxH, v3l);

                mOpenedView = v0;
            }
        });
    }

    @Override
    public boolean onTouch(View touchView, MotionEvent event) {

        if (touchView == mOpenedView) return false;

        MarginLayoutParams lp = getMarginLayoutParams(touchView);

        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:

                delta = event.getRawY() - lp.topMargin;
                yBefore = event.getRawY();
                break;

            case MotionEvent.ACTION_MOVE:

                handleMoveMotionEvent(touchView, event, lp);
                break;

            case MotionEvent.ACTION_UP:

                int id = touchView.getId();
                int finalPosition;

                float yNow = event.getRawY();

                if (yBefore > yNow) {
                    finalPosition = (id == R.id.v1) ? v1h : (id == R.id.v2) ? v2h : v3h;
                } else {
                    finalPosition = (id == R.id.v1) ? v1l : (id == R.id.v2) ? v2l : v3l;
                }
                createMarginAnimator(touchView, finalPosition, 500).start();
                break;
            default:
                return false;
        }
        return true;
    }

    private boolean handleMoveMotionEvent(View view, MotionEvent event, MarginLayoutParams lp) {

        double value = event.getRawY() - delta;

        switch (view.getId()) {

            case R.id.v1:

                if (lp.topMargin >= v1h && lp.topMargin <= v1l) {

                    setMarginTop(view, (int) value);
                }
                return adjustTopMargin(lp, v1h, v1l);

            case R.id.v2:

                if (lp.topMargin >= v2h && lp.topMargin <= v2l) {

                    setMarginTop(view, (int) value);
                }
                return adjustTopMargin(lp, v2h, v2l);

            case R.id.v3:

                if (lp.topMargin >= v3h && lp.topMargin <= v3l) {

                    setMarginTop(view, (int) value);
                }
                return adjustTopMargin(lp, v3h, v3l);

            default:
                return true;
        }
    }

    private void initViewHeightAndMargin(View v, int height, int marginTop) {

        MarginLayoutParams lp = getMarginLayoutParams(v);

        lp.height = (int) (height * (1f + sOvershoot * .1f));
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

    private boolean adjustTopMargin(MarginLayoutParams lp, int high, int low) {

        lp.topMargin = lp.topMargin < high
                ? lp.topMargin = high
                : lp.topMargin > low ? low : lp.topMargin;
        return true;
    }

    private ValueAnimator createMarginAnimator(final View view, int endValue, int duration) {

        final MarginLayoutParams lp = getMarginLayoutParams(view);

        ValueAnimator animator = ValueAnimator.ofInt(lp.topMargin, endValue);

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

                lp.topMargin = (int) animation.getAnimatedValue();
                view.requestLayout();
            }
        });

        animator.setDuration(duration);

        animator.setInterpolator(new OvershootInterpolator(sOvershoot));
        return animator;
    }
}
