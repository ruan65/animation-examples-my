package premiumapp.org.propertyanimationtutplus;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;


public class ExperimentActivity extends ActionBarActivity {

    ImageView iv1, iv2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_experiment);

        iv1 = (ImageView) findViewById(R.id.imageView);
        iv2 = (ImageView) findViewById(R.id.imageView2);


        setDrawableToImageView(iv1, R.drawable.rect);
        setDrawableToImageView(iv2, R.drawable.rect1);

    }

    private void setDrawableToImageView(ImageView iv, int resId) {

        iv.setImageResource(resId);
    }
}
