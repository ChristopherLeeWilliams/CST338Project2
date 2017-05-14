package will6366.project_2_part_3;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import will6366.project_2_part_3.helperObjects.DatabaseHelper;
import will6366.project_2_part_3.helperObjects.Book;
// Credit: https://www.youtube.com/watch?v=hwe1abDO2Ag

public class PlaceHold extends AppCompatActivity {

    private TextView mPickupDate;
    private TextView mPickupTime;
    private TextView mReturnDate;
    private TextView mReturnTime;
    private Button mPickupDateButton;
    private Button mReturnDateButton;
    private DatePickerDialog.OnDateSetListener mOnPickupDateSetListeners;
    private DatePickerDialog.OnDateSetListener mOnReturnDateSetListeners;
    private TimePickerDialog.OnTimeSetListener mOnPickupTimeSetListeners;
    private TimePickerDialog.OnTimeSetListener mOnReturnTimeSetListeners;

    private int numberOfErrors = 0;
    private static final String TAG = "PlaceHold";

    int pDay,pMonth,pYear,pHour,pMinute = 0;
    int rDay,rMonth,rYear,rHour,rMinute = 0;
    long holdHours;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_hold);

        mPickupDate = (TextView) findViewById(R.id.pickup_date);
        mPickupTime = (TextView) findViewById(R.id.pickup_time);
        mPickupDateButton = (Button) findViewById(R.id.pickup_date_button);
        mReturnDate = (TextView) findViewById(R.id.return_date);
        mReturnTime = (TextView) findViewById(R.id.return_time);
        mReturnDateButton = (Button) findViewById(R.id.return_date_button);


        mPickupDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(PlaceHold.this,
                        android.R.style.Theme_Holo_Dialog, mOnPickupDateSetListeners,year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mOnPickupDateSetListeners = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                // pickup date
                pDay = dayOfMonth;
                pMonth = month + 1;
                pYear = year;
                Log.d(TAG,pMonth+"/"+pDay+"/"+pYear);
                //pickupDate = String.format("%02d/%02d/%04d",pDay,pMonth,pYear);
                mPickupDate.setText(String.format("%02d/%02d/%04d",pMonth,pDay,pYear));
            }
        };

        mPickupDate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                Calendar cal = Calendar.getInstance();
                int hour = cal.get(Calendar.HOUR_OF_DAY);
                int minute = cal.get(Calendar.MINUTE);

                TimePickerDialog timeDialogue = new TimePickerDialog(PlaceHold.this, mOnPickupTimeSetListeners,hour,minute, true);
                timeDialogue.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                timeDialogue.show();
            }
        });

        mOnPickupTimeSetListeners = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                pHour = hourOfDay;
                pMinute = minute;
                Log.d(TAG,pHour+":"+pMinute);
                mPickupTime.setText(String.format("%02d:%02d:%02d",pHour,pMinute,0));
            }
        };


        mReturnDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(PlaceHold.this,
                        android.R.style.Theme_Holo_Dialog, mOnReturnDateSetListeners,year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mOnReturnDateSetListeners = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                //return date
                rDay = dayOfMonth;
                rMonth = month + 1;
                rYear = year;
                Log.d(TAG,rMonth+"/"+rDay+"/"+rYear);
                mReturnDate.setText(String.format("%02d/%02d/%04d",rMonth,rDay,rYear));
            }
        };

        mReturnDate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                Calendar cal = Calendar.getInstance();
                int hour = cal.get(Calendar.HOUR_OF_DAY);
                int minute = cal.get(Calendar.MINUTE);

                TimePickerDialog timeDialogue = new TimePickerDialog(PlaceHold.this, mOnReturnTimeSetListeners,hour,minute, true);
                timeDialogue.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                timeDialogue.show();
            }
        });

        mOnReturnTimeSetListeners = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                // return time
                rHour = hourOfDay;
                rMinute = minute;
                Log.d(TAG,rHour+":"+rMinute);
                mReturnTime.setText(String.format("%02d:%02d:%02d",rHour,rMinute,0));
            }
        };


    }

    public void submitHold(View v) {
        if (datesSelected()) {
            if (SevenDaysOrLess()) {
                //Check if book is available
                //DB requires: YYYY-MM-DD HH:MM:SS
                String pickupDateForDB = String.format("%04d-%02d-%02d %02d:%02d:%02d",pYear,pMonth,pDay,pHour,pMinute,0);
                String returnDateForDB = String.format("%04d-%02d-%02d %02d:%02d:%02d",rYear,rMonth,rDay,rHour,rMinute,0);

                Intent i = PlaceHoldBookSelection.newIntent(PlaceHold.this,pickupDateForDB,returnDateForDB,holdHours);
                startActivity(i);
            } else {
                Toast.makeText(this, "Hold period must be at least 1 minute and at most 7 days!", Toast.LENGTH_SHORT).show();
            }

        } else {
            Toast.makeText(this, "Hold information missing!", Toast.LENGTH_SHORT).show();
        }
    }

    public boolean SevenDaysOrLess() {
        DateUtils obj = new DateUtils();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        Log.d(TAG,simpleDateFormat.toPattern());
        try {
            String p = String.format("%02d/%02d/%04d %02d:%02d:%02d",pMonth,pDay,pYear,pHour,pMinute,0);
            String r = String.format("%02d/%02d/%04d %02d:%02d:%02d",rMonth,rDay,rYear,rHour,rMinute,0);
            Date date1  = simpleDateFormat.parse(p);
            Date date2 = simpleDateFormat.parse(r);



            long dif = differenceInHours(date1, date2);
            return ((dif<=168)&&(dif>=0));

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean datesSelected() {
        if (mPickupDate.getText().length() < 1) { return false; }
        if (mPickupTime.getText().length() < 1) { return false; }
        if (mReturnDate.getText().length() < 1) { return false; }
        if (mReturnTime.getText().length() < 1) { return false; }
        return true;
    }

    public long differenceInHours(Date startDate, Date endDate){
        // credit: http://stackoverflow.com/questions/21285161/android-difference-between-two-dates

        //milliseconds
        long different = endDate.getTime() - startDate.getTime();

        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;
        long daysInMilli = hoursInMilli * 24;

        long elapsedDays = different / daysInMilli;
        different = different % daysInMilli;

        long elapsedHours = different / hoursInMilli;
        different = different % hoursInMilli;

        long elapsedMinutes = different / minutesInMilli;
        different = different % minutesInMilli;

        long elapsedSeconds = different / secondsInMilli;

        Log.d(TAG,String.format(
                "%d days, %d hours, %d minutes, %d seconds%n",
                elapsedDays,
                elapsedHours, elapsedMinutes, elapsedSeconds));
        holdHours = (elapsedHours + (elapsedDays*24));
        return holdHours;

    }
}
