package will6366.project_2_part_3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import will6366.project_2_part_3.helperObjects.DatabaseHelper;
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
        String password = ( (EditText)findViewById(R.id.password) ).getText().toString();

        //Check format of username and password with Regex
        if (isCorrectFormat(username,password)) {
            Log.d("isCorrectFormat", "true");
            //TODO: make a database
            DatabaseHelper db = new DatabaseHelper(this);

            //TODO: check db for username
            //TODO: add user to db
            try {
                db.addUser(new User(username,password,false));

                //TODO: message user for success or failure
                Toast.makeText(this,"User created successfully!", Toast.LENGTH_SHORT).show();
                db.close();
                finish();
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
}
