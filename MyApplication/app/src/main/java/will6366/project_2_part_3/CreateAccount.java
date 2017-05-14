package will6366.project_2_part_3;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

import will6366.project_2_part_3.helperObjects.DatabaseHelper;
import will6366.project_2_part_3.helperObjects.Transaction;
import will6366.project_2_part_3.helperObjects.User;


public class CreateAccount extends AppCompatActivity {
    private int numberOfErrors = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
    }

    public void createUser(View view) {
        Log.d("createUser"," in create user");

        //Get user input
        String username = ( (EditText)findViewById(R.id.user_name) ).getText().toString();
        String password = ( (EditText)findViewById(R.id.create_account_password) ).getText().toString();

        //Check format of username and password with Regex
        if (isCorrectFormat(username,password)) {
            Log.d("isCorrectFormat", "true");
            //Make a database
            DatabaseHelper db = new DatabaseHelper(this);

            //Check db for username
            //Add user to db
            try {
                db.addUser(new User(username,password,false));

                // -------------------------------------------- TRANSACTION STUFF ---------------------------------------------
                    /*
                    public Transaction(String transactionType, String username, String transactionDate, String transactionTime, String bookTitle, String bookAuthor,
                            int bookISBN, double bookHourlyFee, String holdPickupDate, String holdReturnDate, int holdReservationNumber)
                     */

                String date =  new SimpleDateFormat("yyyy/MM/dd").format(new Date());
                String time = new SimpleDateFormat("HH:mm:ss").format(new Date());
                db.addTransaction(new Transaction("New Account",username,date,time,"","",0,0,"","",0));
                // ------------------------------------------ END TRANSACTION STUFF ------------------------------------------

                db.close();
                //Message user for success or failure
                //Toast.makeText(this,"User created successfully!", Toast.LENGTH_SHORT).show();
                makeAlert("Account Creation","Account created successfully! Select Ok to return to main menu");
            } catch (Exception e) {
                // catch exception thrown from SQLite
                Toast.makeText(this,"Username taken", Toast.LENGTH_SHORT).show();
                db.close();
                numberOfErrors++;
                if (numberOfErrors == 2) {finish();}
                return;
            }

        } else {
            Log.d("isCorrectFormat", "false");
            Toast.makeText(this, "Incorrect Username or Password Format", Toast.LENGTH_SHORT).show();
            numberOfErrors++;
            if (numberOfErrors == 2) {finish();}
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

    public void makeAlert(String title, String message) {
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                        startActivity(new Intent(CreateAccount.this,MainMenu.class));
                    }
                })
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .show();
    }
}
