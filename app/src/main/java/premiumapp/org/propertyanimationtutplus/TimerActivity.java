package premiumapp.org.propertyanimationtutplus;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.ViewTreeObserver;
import android.view.animation.BounceInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.RelativeLayout;

import butterknife.Bind;
import butterknife.ButterKnife;

public class TimerActivity extends AppCompatActivity implements View.OnTouchListener {

    public static final float sOvershoot = 1f;
    public static final int ANIM_DURATION = 650;

    @Bind(R.id.v0)
    View v0;
    @Bind(R.id.v1)
    View v1;
    @Bind(R.id.v2)
    View v2;
    @Bind(R.id.v3)
    View v3;
    @Bind(R.id.containerRL)
    RelativeLayout mFrame;

    int v0h, v1h, v2h, v3h, v0l, v1l, v2l, v3l; // highest and lowest positions of all movable views
    int minH;

    double delta, yBefore; // these are used in the MotionEvent switch

    boolean isUpV1, isUpV2, isUpV3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        ButterKnife.bind(this);

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
                minH = maxH / 3;

                v0l = minH / 2;
                v1l = maxH;
                v2l = maxH + minH;
                v3l = maxH + minH * 2;

                v1h = v1l - minH * 2;
                v2h = v2l - minH * 2;
                v3h = v3l - minH * 2;

                initViewHeightAndMargin(v0, maxH, v0h);
                initViewHeightAndMargin(v1, maxH, v1l);
                initViewHeightAndMargin(v2, maxH, v2l);
                initViewHeightAndMargin(v3, maxH, v3l);
            }
        });
    }

    @Override
    public boolean onTouch(View touchView, MotionEvent event) {

        MarginLayoutParams lp = getMarginLayoutParams(touchView);

        switch (event.getActionMasked()) {

            case MotionEvent.ACTION_DOWN:

                delta = event.getRawY() - lp.topMargin;
                yBefore = event.getRawY();
                break;

            case MotionEvent.ACTION_MOVE:

                if (touchView.getId() == R.id.v0 && yBefore + 10 < event.getRawY()) {
                    handleDownFling(touchView, touchView.getId());
                }
                handleMoveMotionEvent(touchView, event, lp);
                break;

            case MotionEvent.ACTION_UP:

                float yNow = event.getRawY();

                if (yBefore > yNow  || touchView.getId() == R.id.v0) {

                    handleUpFling(touchView, touchView.getId());
                } else {

                    handleDownFling(touchView, touchView.getId());
                }
                break;
            default:
                return false;
        }
        return true;
    }

    private void handleUpFling(View v, int id) {

        int finalPosition;

        switch (id) {

            case R.id.v0:
                finalPosition = v0h;

                ValueAnimator marginAnimator = createMarginAnimator(v, finalPosition, 900);
                marginAnimator.setInterpolator(new BounceInterpolator());
                marginAnimator.start();
                return;

            case R.id.v1:
                finalPosition = v1h;
                isUpV1 = true;
                break;

            case R.id.v2:
                finalPosition = v2h;
                if (!isUpV1) {
                    createMarginAnimator(v1, v1h, ANIM_DURATION).start();
                    isUpV1 = true;
                }
                isUpV2 = true;
                break;

            default:
                finalPosition = v3h;
                if (!isUpV1) {
                    createMarginAnimator(v1, v1h, ANIM_DURATION).start();
                    isUpV1 = true;
                }
                if (!isUpV2) {
                    createMarginAnimator(v2, v2h, ANIM_DURATION).start();
                    isUpV2 = true;
                }
                isUpV3 = true;
                break;
        }
        createMarginAnimator(v, finalPosition, ANIM_DURATION).start();
    }

    private void handleDownFling(View v, int id) {

        int finalPosition;

        switch (id) {

            case R.id.v0:

                if (isUpV1) {
                    createMarginAnimator(v1, v1l, ANIM_DURATION).start();
                    isUpV1 = false;
                }
                if (isUpV2) {
                    createMarginAnimator(v2, v2l, ANIM_DURATION).start();
                    isUpV2 = false;
                }
                if (isUpV3) {
                    createMarginAnimator(v3, v3l, ANIM_DURATION).start();
                    isUpV3 = false;
                }
                return;

            case R.id.v1:
                finalPosition = v1l;
                if (isUpV2) {
                    createMarginAnimator(v2, v2l, ANIM_DURATION).start();
                    isUpV2 = false;
                }
                if (isUpV3) {
                    createMarginAnimator(v3, v3l, ANIM_DURATION).start();
                    isUpV3 = false;
                }
                isUpV1 = false;
                break;

            case R.id.v2:
                finalPosition = v2l;
                if (isUpV3) {
                    createMarginAnimator(v3, v3l, ANIM_DURATION).start();
                    isUpV3 = false;
                }
                isUpV2 = false;
                break;

            default:
                finalPosition = v3l;
                isUpV3 = false;
                break;
        }
        createMarginAnimator(v, finalPosition, ANIM_DURATION).start();
    }

    private boolean handleMoveMotionEvent(View view, MotionEvent event, MarginLayoutParams lp) {

        double value = event.getRawY() - delta;

        switch (view.getId()) {

            case R.id.v0:
                if (lp.topMargin >= v0h && lp.topMargin <= v0l) {

                    setMarginTop(view, (int) value);
                }
                return adjustTopMargin(lp, v0h, v0l);

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
