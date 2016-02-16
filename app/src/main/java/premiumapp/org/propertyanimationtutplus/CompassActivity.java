package premiumapp.org.propertyanimationtutplus;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ProgressBar;
import android.widget.TextView;

import premiumapp.org.propertyanimationtutplus.custom_views.CompassView;


public class CompassActivity extends AppCompatActivity {

    Button button;
    ProgressBar progressBar;
    ProgressBar roundProgressBar;
    TextView textView;
//    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compass);

//        handler = new LocalHandler();

//        handler = new Handler(getMainLooper());
        roundProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar = (ProgressBar) findViewById(R.id.progressBar2);
        textView = (TextView) findViewById(R.id.textView);
        button = (Button) findViewById(R.id.btn777);
    }

    public void btnClicked(View view) throws InterruptedException {

        doFakeWork();
    }

    private class LocalHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {

            progressBar.setProgress(msg.what);
            roundProgressBar.setProgress(msg.what);
        }
    }

    void doFakeWork() throws InterruptedException {


        new Thread() {

//            Handler handler = new Handler(Looper.myLooper());

            LocalHandler handler = new LocalHandler();
            @Override
            public void run() {

                for (int i = 1; i <= 10; i++) {

                    try {
                        sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

//                    Message m = handler.obtainMessage(i);
//                    m.what = i;
                    handler.sendMessage(handler.obtainMessage(i));

//                    final int finalI = i;
//                    handler.post(new Runnable() {
//                        @Override
//                        public void run() {
//
//                            progressBar.setProgress(finalI);
//                        }
//                    });
                }

            }
        }.start();

//        for (int i = 1; i <= 10; i++) {
//
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//
////                    Message m = handler.obtainMessage();
////                    m.what = i;
////                    handler.sendMessage(m);
//
//            final int finalI = i;
//            handler.post(new Runnable() {
//                @Override
//                public void run() {
//
//                    progressBar.setProgress(finalI);
//                }
//            });
//        }

    }
}
