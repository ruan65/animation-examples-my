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
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationSet;
import android.widget.ImageView;
import android.widget.ViewAnimator;


public class MainActivity extends AppCompatActivity {

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
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.timer:
                startActivity(new Intent(MainActivity.this, TimerActivity.class));
                break;
            case R.id.drag_and_drop:
                startActivity(new Intent(MainActivity.this, DragAndDropActivity.class));
                break;
            case R.id.experiment:
                startActivity(new Intent(MainActivity.this, ExperimentActivity.class));
                break;
            case R.id.some:
                startActivity(new Intent(MainActivity.this, SomeActivity.class));
                break;
        }
        return true;
    }

}
