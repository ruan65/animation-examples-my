package premiumapp.org.propertyanimationtutplus;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class SomeActivity extends ActionBarActivity implements View.OnTouchListener {

    @InjectView(R.id.imageView)
    ImageView wheel;

    @InjectView(R.id.imageView2)
    ImageView wheel2;

    float dX, dY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_some);

        ButterKnife.inject(this);

        wheel.setOnTouchListener(this);
        wheel2.setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {

        switch (event.getActionMasked()) {

            case MotionEvent.ACTION_DOWN:

                dX = view.getX() - event.getRawX();
                dY = view.getY() - event.getRawY();
                break;

            case MotionEvent.ACTION_MOVE:
                view.animate()
                        .x(event.getRawX() + dX)
                        .y(event.getRawY() + dY)
                        .setDuration(0)
                        .start();
                break;
            default:
                return false;
        }
        return true;
    }

    public void recreate(View view) {

        recreate();
    }
}


//                ObjectAnimator.ofFloat(v, "x", event.getRawX() + dX)
//                        .setDuration(0)
//                        .start();
//
//                ObjectAnimator.ofFloat(v, "y", event.getRawY() + dY )
//                        .setDuration(0)
//                        .start();
//
//                v.animate()
//                        .translationX(event.getRawX() + dX)
//                        .translationY(event.getRawY() + dY)
//                        .setDuration(0)
//                        .start();
