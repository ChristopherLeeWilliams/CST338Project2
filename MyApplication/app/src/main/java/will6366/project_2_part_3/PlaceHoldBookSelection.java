package will6366.project_2_part_3;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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
    int numberOfErrors = 0;


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

        books = db.getAllBooksAvailable(pickupDate,returnDate);
        db.close();

        if (books.size()>0) {
            bookStrings = booksToString(books);

            ArrayAdapter<String> bookAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, bookStrings);
            dropdown.setAdapter(bookAdapter);
            dropdown.setSelection(0);
        } else {
            // no books were available
            makeErrorAlert("No Books Found!", "There are no books available for holds within the timeline specified, press Ok " +
                    "to return to the main menu.");
        }

        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG,"From array: "+bookStrings.get(position));
                selectedBook = books.get(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // nothing
            }
        });
    }

    public ArrayList<String> booksToString(ArrayList<Book> books) {
        ArrayList<String> a = new ArrayList<>();
        for(int i = 0; i< books.size(); i++){
            a.add(books.get(i).getTitle()+" By "+books.get(i).getAuthor());
        }
        return a;
    }

    public void makeErrorAlert(String title, String message) {
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(PlaceHoldBookSelection.this,MainMenu.class));
                    }
                })
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .show();
    }

    public static Intent newIntent(Context packageContext, String pickupDate, String returnDate, long holdHours) {
        Intent intent = new Intent(packageContext, PlaceHoldBookSelection.class);
        intent.putExtra("pickupDate",pickupDate);
        intent.putExtra("returnDate",returnDate);
        int hHours = (int)holdHours;
        intent.putExtra("holdHours",hHours);
        return intent;
    }

    public void submit(View view) {
        if (selectedBook != null) {
            Intent i = PlaceHoldLogin.newIntent(PlaceHoldBookSelection.this,pickupDate,returnDate,holdHours,selectedBook.getId());
            startActivity(i);
        } else {
            Toast.makeText(this, "No book selected!", Toast.LENGTH_SHORT).show();
            numberOfErrors++;
            if (numberOfErrors == 2) {startActivity(new Intent(this,MainMenu.class));}
        }
    }
}
