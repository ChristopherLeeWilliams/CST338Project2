package will6366.project_2_part_3;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

import will6366.project_2_part_3.helperObjects.Book;
import will6366.project_2_part_3.helperObjects.DatabaseHelper;

public class PlaceHoldBookSelection extends AppCompatActivity {

    private TextView selected_hold_date;
    private static final String TAG = "PlaceHoldBookSelection";
    String pickupDate, returnDate;
    int holdHours;
    ArrayList<Book> books;
    ArrayList<String> bookStrings;
    Book selectedBook;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_hold_book_selection);

        pickupDate = getIntent().getStringExtra("pickupDate");
        returnDate = getIntent().getStringExtra("returnDate");
        holdHours = getIntent().getIntExtra("holdHours",0);

        selected_hold_date = (TextView) findViewById(R.id.selected_hold_date);
        selected_hold_date.setText("["+pickupDate+" - "+returnDate+"]");

        //Log.d("Pickup Date ",pickupDate);
        //Log.d("Return Date ",returnDate);
        //Log.d("Hold Hours: ",""+holdHours);

        DatabaseHelper db = new DatabaseHelper(this);

        Spinner dropdown = (Spinner)findViewById(R.id.book_drop_down);

        books = db.getAllBooks();
        bookStrings = db.getAllBookTitlesWithAuthors();

        ArrayAdapter<String> bookAdapter  = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, bookStrings);
        dropdown.setAdapter(bookAdapter);
        dropdown.setSelection(0);

        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG,"From array: "+bookStrings.get(position));
                selectedBook = books.get(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // sometimes you need nothing here
            }
        });
    }


    public static Intent newIntent(Context packageContext, String pickupDate, String returnDate, long holdHours) {
        Intent intent = new Intent(packageContext, PlaceHoldBookSelection.class);
        intent.putExtra("pickupDate",pickupDate);
        intent.putExtra("returnDate",returnDate);
        intent.putExtra("holdHours",holdHours);
        return intent;
    }
}
