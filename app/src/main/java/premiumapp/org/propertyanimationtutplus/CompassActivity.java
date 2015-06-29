package premiumapp.org.propertyanimationtutplus;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import premiumapp.org.propertyanimationtutplus.custom_views.CompassView;


public class CompassActivity extends ActionBarActivity {

    CompassView cv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        cv = new CompassView(this);

        setContentView(cv);
    }
}
