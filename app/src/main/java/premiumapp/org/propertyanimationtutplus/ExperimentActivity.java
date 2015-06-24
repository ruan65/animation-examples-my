package premiumapp.org.propertyanimationtutplus;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ImageView;


public class ExperimentActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_experiment);

        Bitmap tile = BitmapFactory.decodeResource(getResources(), R.drawable.trans_background);

        BitmapDrawable drawableTile = new BitmapDrawable(tile);

        drawableTile.setTileModeXY(Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);

        findViewById(R.id.v1).setBackgroundDrawable(drawableTile);

    }
}
