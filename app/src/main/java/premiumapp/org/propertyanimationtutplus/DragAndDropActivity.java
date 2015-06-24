package premiumapp.org.propertyanimationtutplus;

import android.content.ClipData;
import android.graphics.Typeface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.DragEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;


public class DragAndDropActivity extends AppCompatActivity {

    private TextView option1, option2, option3, choice1, choice2, choice3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drag_and_drop);

        option1 = (TextView)findViewById(R.id.option_1);
        option2 = (TextView)findViewById(R.id.option_2);
        option3 = (TextView)findViewById(R.id.option_3);

        choice1 = (TextView)findViewById(R.id.choice_1);
        choice2 = (TextView)findViewById(R.id.choice_2);
        choice3 = (TextView)findViewById(R.id.choice_3);

        option1.setOnTouchListener(new ChoiceTouchListener());
        option2.setOnTouchListener(new ChoiceTouchListener());
        option3.setOnTouchListener(new ChoiceTouchListener());

        choice1.setOnDragListener(new ChoiceDragListener());
        choice2.setOnDragListener(new ChoiceDragListener());
        choice3.setOnDragListener(new ChoiceDragListener());
    }

    private final class ChoiceTouchListener implements View.OnTouchListener {

        @Override
        public boolean onTouch(View v, MotionEvent event) {

            if (event.getAction() == MotionEvent.ACTION_DOWN) {


                ClipData data = ClipData.newPlainText("", "");

                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);

                v.startDrag(data, shadowBuilder, v, 0);

                return true;
            }
            return false;
        }
    }

    private final class ChoiceDragListener implements View.OnDragListener {

        @Override
        public boolean onDrag(View v, DragEvent event) {

            TextView draggedView = (TextView) event.getLocalState();

            switch (event.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:
                    //no action necessary

                    draggedView.setVisibility(View.INVISIBLE);
                case DragEvent.ACTION_DRAG_ENTERED:
                    //no action necessary
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    //no action necessary
                    break;
                case DragEvent.ACTION_DROP:


                    draggedView.setVisibility(View.INVISIBLE);

                    TextView target = (TextView) v;

                    target.setText(draggedView.getText());

                    target.setTypeface(Typeface.DEFAULT_BOLD);

                    Object tag = target.getTag();

                    if (tag != null) {
                        int existingId = (int) tag;
                        findViewById(existingId).setVisibility(View.VISIBLE);
                    }

                    target.setTag(draggedView.getId());

                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                    //no action necessary


                    break;
                default:
                    break;
            }
            return true;
        }
    }
}
