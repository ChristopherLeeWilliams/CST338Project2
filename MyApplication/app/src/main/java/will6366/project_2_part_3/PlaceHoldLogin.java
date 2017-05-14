package will6366.project_2_part_3;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import will6366.project_2_part_3.helperObjects.Book;
import will6366.project_2_part_3.helperObjects.DatabaseHelper;
import will6366.project_2_part_3.helperObjects.Hold;
import will6366.project_2_part_3.helperObjects.User;

public class PlaceHoldLogin extends AppCompatActivity {

    Book mBook;
    String mPickupDate;
    String mReturnDate;
    double mPrice;

    private int numberOfErrors = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_hold_login);

        DatabaseHelper db = new DatabaseHelper(this);

        mPickupDate = getIntent().getStringExtra("pickupDate");
        mReturnDate = getIntent().getStringExtra("returnDate");
        mBook = db.getBook(getIntent().getIntExtra("bookId",0));
        mPrice = getIntent().getLongExtra("holdHours",0) * mBook.getHourlyFee();
        db.close();
    }

    public void login(View view) {
        Log.d("login"," in login");

        //Get user input
        String username = ( (EditText)findViewById(R.id.hold_user_name) ).getText().toString();
        String password = ( (EditText)findViewById(R.id.hold_password) ).getText().toString();

        //Check format of username and password with Regex
        if (isCorrectFormat(username,password)) {
            Log.d("isCorrectFormat", "true");
            DatabaseHelper db = new DatabaseHelper(this);
            try {
                User user = db.getUser(username);
                if (password.equals(user.getPassword())) {
                    //int id, int bookId, int userId, String pickupDate, String returnDate, double price
                    Hold temp = new Hold(0,mBook.getId(),user.getUserId(),mPickupDate,mReturnDate,mPrice);
                    db.addHold(temp);
                    //ArrayList<Hold> a = db.getAllHolds();
                    //Log.d("Expected Match Hold","DB) "+a.get(a.size()-1)+" \nLocal: "+temp);

                    db.close();
                    //(yyyy-mm-dd hh-mm-ss):
                    makeErrorAlert("Hold Placed!","" +
                            "Customer username: "+username+
                            "\nPickup date/time: "+mPickupDate+
                            "\nReturn date/time: "+mReturnDate+
                            "\nBook Title: "+ mBook.getTitle()+
                            "\nReservation number: ??" +
                            "\nTotal amount: $"+mPrice);

                    // then add it to holds and transactions
                } else {
                    Toast.makeText(this,"Incorrect Password", Toast.LENGTH_SHORT).show();
                    db.close();
                    numberOfErrors++;
                    if (numberOfErrors == 2) {startActivity(new Intent(this,MainMenu.class));}
                }
                //startActivity(new Intent(this,MainMenu.class));

            } catch (Exception e) {
                // catch exception thrown from SQLite
                Toast.makeText(this,"Something went wrong: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                db.close();
                numberOfErrors++;
                if (numberOfErrors == 2) {startActivity(new Intent(this,MainMenu.class));}
                return;
            }

        } else {
            Log.d("isCorrectFormat", "false");
            Toast.makeText(this, "Incorrect Username or Password", Toast.LENGTH_SHORT).show();
            numberOfErrors++;
            if (numberOfErrors == 2) {startActivity(new Intent(this,MainMenu.class));}
        }
    }


    public boolean isCorrectFormat(String u, String p) {
        //https://docs.oracle.com/javase/7/docs/api/java/util/regex/Pattern.html

        // eliminate white spaces
        String username = u.replaceAll("\\s+","");
        String password = p.replaceAll("\\s+","");

        // check length
        if ((username.length() < 5) || (password.length() < 5)) { return false; }

        // check for 1+ numbers
        if (username.replaceAll("\\D","").length()<1) {return false;}
        if (password.replaceAll("\\D","").length()<1) {return false;}

        // check for 1+ special character (!,@,#, or $)
        if (username.replaceAll("[^!@#$]","").length()<1) {return false;}
        if (password.replaceAll("[^!@#$]","").length()<1) {return false;}

        // check for 3+ alphabetic letters
        if (username.replaceAll("[^[a-zA-Z]]","").length()<3) {return false;}
        if (password.replaceAll("[^[a-zA-Z]]","").length()<3) {return false;}
        return true;
    }

    public void makeErrorAlert(String title, String message) {
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                        startActivity(new Intent(PlaceHoldLogin.this,MainMenu.class));
                    }
                })
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .show();
    }

    public static Intent newIntent(Context packageContext, String pickupDate, String returnDate, long holdHours, int bookId) {
        //holdHours = holdHours;
        Intent intent = new Intent(packageContext, PlaceHoldLogin.class);
        intent.putExtra("pickupDate",pickupDate);
        intent.putExtra("returnDate",returnDate);
        intent.putExtra("holdHours",holdHours);
        intent.putExtra("bookId",bookId);
        return intent;
    }
}
