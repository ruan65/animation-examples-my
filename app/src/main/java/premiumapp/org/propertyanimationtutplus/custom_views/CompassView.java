package premiumapp.org.propertyanimationtutplus.custom_views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class CompassView extends View {

    private ShapeDrawable mDrawable;

    public CompassView(Context context) {
        super(context);
        int x = 10, y = 10, width = 300, height = 50;

        mDrawable = new ShapeDrawable(new OvalShape());

        mDrawable.getPaint().setColor(0xff74ac23);

        mDrawable.setBounds(x, y, x + width, y + height);


    }

    @Override
    protected void onDraw(Canvas canvas) {

        mDrawable.draw(canvas);
    }
}


























