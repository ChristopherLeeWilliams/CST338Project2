package will6366.project_2_part_3;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
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

    private static final String TAG = "PlaceHold";

    private TextView mPickupDate;
    private TextView mPickupTime;
    private TextView mReturnDate;
    private TextView mReturnTime;
    private Button mPickupDateButton;
    private Button mReturnDateButton;
    private DatePickerDialog.OnDateSetListener mOnDateSetListeners;
    private DatePickerDialog.OnDateSetListener mOnDateSetListeners2;
    private TimePickerDialog.OnTimeSetListener mOnTimeSetListeners;
    private TimePickerDialog.OnTimeSetListener mOnTimeSetListeners2;

    String pickupDate,pickupTime, returnDate, returnTime;
    ArrayList<Book> books;
    ArrayList<String> bookStrings;

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


        DatabaseHelper db = new DatabaseHelper(this);

        Spinner dropdown = (Spinner)findViewById(R.id.book_drop_down);

        books = db.getAllBooks();
        bookStrings = db.getAllBookTitlesWithAuthors();

        ArrayAdapter<String> bookAdapter  = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, bookStrings);
        dropdown.setAdapter(bookAdapter);
        dropdown.setSelection(0);

        //Log.d(TAG,"0:"+books.get(0)+"\n1:"+books.get(1));
        //Log.d(TAG,"\n0:"+bookStrings.get(0)+"\n1:"+bookStrings.get(1));

        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG,"From array: "+bookStrings.get(position)+"\nFrom DB: "+books.get(position));
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // sometimes you need nothing here
            }
        });


        mPickupDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(PlaceHold.this,
                        android.R.style.Theme_Holo_Dialog, mOnDateSetListeners,year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mOnDateSetListeners = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                // pickup date
                month = month + 1;
                Log.d(TAG,month+"/"+dayOfMonth+"/"+year);
                pickupDate = String.format("%02d/%02d/%04d",month,dayOfMonth,year);
                mPickupDate.setText(pickupDate);
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

                TimePickerDialog timeDialogue = new TimePickerDialog(PlaceHold.this, mOnTimeSetListeners,hour,minute, true);
                timeDialogue.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                timeDialogue.show();
            }
        });

        mOnTimeSetListeners = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                //pickup time
                //Log.d(TAG,hourOfDay+":"+minute);
                pickupTime = String.format("%02d:%02d:%02d",hourOfDay,minute,0);
                mPickupTime.setText(pickupTime);
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
                        android.R.style.Theme_Holo_Dialog, mOnDateSetListeners2,year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mOnDateSetListeners2 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                //return date
                month = month + 1;
                Log.d(TAG,month+"/"+dayOfMonth+"/"+year);
                returnDate = String.format("%02d/%02d/%04d",month,dayOfMonth,year);
                mReturnDate.setText(returnDate);
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

                TimePickerDialog timeDialogue = new TimePickerDialog(PlaceHold.this, mOnTimeSetListeners2,hour,minute, true);
                timeDialogue.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                timeDialogue.show();
            }
        });

        mOnTimeSetListeners2 = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                // return time
                Log.d(TAG,hourOfDay+":"+minute);
                returnTime = String.format("%02d:%02d:%02d",hourOfDay,minute,0);
                mReturnTime.setText(returnTime);
            }
        };


    }

    public void submitHold(View v) {
        if (datesSelected()) {
            if (lessThan48Hours()) {

            } else {
                Toast.makeText(this, "Hold period cannot exceed 48 hours!", Toast.LENGTH_SHORT).show();
            }

        } else {
            Toast.makeText(this, "Hold information missing!", Toast.LENGTH_SHORT).show();
        }
    }

    public boolean lessThan48Hours() {
        DateUtils obj = new DateUtils();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("M/dd/yyyy hh:mm:ss");
        try {
            Date date1 = simpleDateFormat.parse(pickupDate + " "+pickupTime);
            Date date2 = simpleDateFormat.parse(returnDate + " "+returnTime);

             return (differnceInHours(date1, date2)<=48);

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

    public long differnceInHours(Date startDate, Date endDate){
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

        return (elapsedHours + (elapsedDays*24));

    }
}
