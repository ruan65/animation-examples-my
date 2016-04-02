package premiumapp.org.propertyanimationtutplus;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DatePickerActivity extends AppCompatActivity {

    @Bind(R.id.et_date)
    EditText etDate;

    @Bind(R.id.tv_info)
    TextView tvInfo;

    private final Calendar calendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_picker);
        ButterKnife.bind(this);

//        etDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if (hasFocus) {
//                    new DatePickerDialog(DatePickerActivity.this, new DatePickerDialog.OnDateSetListener() {
//                        @Override
//                        public void onDateSet(DatePicker picker, int year, int monthOfYear, int dayOfMonth) {
//
//                            etDate.setText(picker.getDayOfMonth() + "/" + picker.getMonth() +
//                                    "/" + picker.getYear());
//
//                        }
//                    }, 2016, 1, 1);
//                }
//            }
//        });

        etDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                new DatePickerDialog(DatePickerActivity.this, new DateSetListener(), year, month, day).show();
            }
        });

    }

    private class DateSetListener implements DatePickerDialog.OnDateSetListener {

        @Override
        public void onDateSet(DatePicker picker, int year, int monthOfYear, int dayOfMonth) {

            String myFormat = "dd MMMM yyyy";
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());

            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, monthOfYear);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            etDate.setText(sdf.format(calendar.getTime()));
        }
    }

    @OnClick(R.id.btn_show_date)
    public void show(View v) {
        tvInfo.setText(etDate.getText().toString());
    }
}
