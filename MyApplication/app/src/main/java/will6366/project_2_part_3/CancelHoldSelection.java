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
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import will6366.project_2_part_3.helperObjects.Book;
import will6366.project_2_part_3.helperObjects.DatabaseHelper;
import will6366.project_2_part_3.helperObjects.Hold;
import will6366.project_2_part_3.helperObjects.Transaction;

public class CancelHoldSelection extends AppCompatActivity {

    private static final String TAG = "CancelHoldBookSelection";
    private int numberOfErrors = 0;
    ArrayList<Hold> holds;
    ArrayList<String> holdStrings;
    Hold selectedHold;
    int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancel_hold_selection);

        //userId = 1;
        userId = getIntent().getIntExtra("userId", 0);

        DatabaseHelper db = new DatabaseHelper(this);
        Spinner dropdown = (Spinner) findViewById(R.id.hold_drop_down);

        //Get holds matching userId
        Log.d("Looking for hold","Desired: "+userId);
        db.getAllHolds();
        holds = db.getAllUserHolds(userId);
        db.close();

        if (holds.size() > 0) {
            holdStrings = holdsToString(holds);

            ArrayAdapter<String> holdAdapter = new ArrayAdapter<>(CancelHoldSelection.this, android.R.layout.simple_spinner_dropdown_item, holdStrings);

            dropdown.setAdapter(holdAdapter);
            dropdown.setSelection(0);
        } else {
            // no holds were available
            makeErrorAlert("No Holds Found!", "There are no holds currently placed by this user, press Ok" +
                    " to return to the main menu.");
        }

        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "From array: " + holdStrings.get(position));
                selectedHold = holds.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // nothing
            }
        });
    }

    public ArrayList<String> holdsToString(ArrayList<Hold> holds) {
        ArrayList<String> a = new ArrayList<>();
        DatabaseHelper db = new DatabaseHelper(this);

        for (int i = 0; i < holds.size(); i++) {
            // display book title and hold period
            a.add(db.getBook(holds.get(i).getBookId()).getTitle() + " [" + holds.get(i).getPickupDate().substring(0,16) +
                    " - " + holds.get(i).getReturnDate().substring(0,16) + "]");
        }
        return a;
    }

    public void makeConfirmationAlert(String title, String message) {
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        DatabaseHelper db = new DatabaseHelper(CancelHoldSelection.this);
                        db.deleteHold(selectedHold.getId());
                        // -------------------------------------------- TRANSACTION STUFF ---------------------------------------------
                        /*
                        public Transaction(String transactionType, String username, String transactionDate, String transactionTime, String bookTitle, String bookAuthor,
                                int bookISBN, double bookHourlyFee, String holdPickupDate, String holdReturnDate, int holdReservationNumber)
                         */

                        String date =  new SimpleDateFormat("yyyy/MM/dd").format(new Date());
                        String time = new SimpleDateFormat("HH:mm:ss").format(new Date());
                        db.addTransaction(new Transaction("Cancel Hold",db.getUser(userId).getUsername(),date,time,db.getBook(selectedHold.getBookId()).getTitle(),
                                                            "",0,0,selectedHold.getPickupDate(),selectedHold.getReturnDate(),selectedHold.getId()));
                        // ------------------------------------------ END TRANSACTION STUFF ------------------------------------------
                        db.close();
                        Toast.makeText(CancelHoldSelection.this, "Hold Cancelled!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(CancelHoldSelection.this, MainMenu.class));
                    }
                })
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .show();
    }

    public void makeErrorAlert(String title, String message) {
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(CancelHoldSelection.this, MainMenu.class));
                    }
                })
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .show();
    }

    public static Intent newIntent(Context packageContext, int userId) {
        Intent intent = new Intent(packageContext, CancelHoldSelection.class);
        intent.putExtra("userId", userId);
        return intent;
    }

    public void submit_hold_cancel(View view) {
        if (selectedHold != null) {
            // Prompt user to confirm selection
            DatabaseHelper db = new DatabaseHelper(this);
            Book holdBook = db.getBook(selectedHold.getBookId());
            db.close();
            makeConfirmationAlert("Confirm Hold Cancellation","Are you sure you would like to remove the selected hold? Press yes to continue." +
                    "\nTitle: "+ holdBook.getTitle()+
                    "\nPickup Date: "+ selectedHold.getPickupDate()+
                    "\nReturn Date: "+ selectedHold.getReturnDate());
        } else {
            Toast.makeText(this, "No hold selected!", Toast.LENGTH_SHORT).show();
            numberOfErrors++;
            if (numberOfErrors == 2) {
                startActivity(new Intent(this, MainMenu.class));
            }
        }
    }
}
