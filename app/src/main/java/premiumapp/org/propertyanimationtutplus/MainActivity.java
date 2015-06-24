package premiumapp.org.propertyanimationtutplus;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationSet;
import android.widget.ImageView;
import android.widget.ViewAnimator;


public class MainActivity extends ActionBarActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView wheel = (ImageView) findViewById(R.id.wheel);
        ImageView sun = (ImageView) findViewById(R.id.sun);

        Animator wheelSet = AnimatorInflater.loadAnimator(this, R.animator.wheel_spin);
        Animator sunSet = AnimatorInflater.loadAnimator(this, R.animator.sun_swing);

        wheelSet.setTarget(wheel);
        sunSet.setTarget(sun);

        wheelSet.start();
        sunSet.start();

        animateSky(findViewById(R.id.car_layout), 3000).start();
        animateCloud(findViewById(R.id.cloud1), 3000, -360).start();
        animateCloud(findViewById(R.id.cloud2), 3000, -330).start();

        wheel.setOnClickListener(this);
        sun.setOnClickListener(this);
    }

    private ValueAnimator animateSky(View v, int duration) {

        ValueAnimator skyAnim = ObjectAnimator.ofInt(v, "backgroundColor",
                Color.rgb(0x66, 0xcc, 0xff), Color.rgb(0x00, 0x66, 0x99));

        skyAnim.setDuration(duration);
        skyAnim.setEvaluator(new ArgbEvaluator());

        skyAnim.setRepeatCount(ValueAnimator.INFINITE);
        skyAnim.setRepeatMode(ValueAnimator.REVERSE);

        return skyAnim;
    }

    private ObjectAnimator animateCloud(View v, int duration, int offset) {

        ObjectAnimator animator = ObjectAnimator.ofFloat(v, "x", offset);
        animator.setDuration(duration);
        animator.setRepeatMode(ValueAnimator.REVERSE);
        animator.setRepeatCount(ValueAnimator.INFINITE);

        return animator;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.wheel:
                startActivity(new Intent(MainActivity.this, TimerActivity.class));
                break;
            case R.id.sun:
                startActivity(new Intent(MainActivity.this, DragAndDropActivity.class));
                break;
        }
    }
}
