package will6366.project_2_part_3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import will6366.project_2_part_3.helperObjects.DatabaseHelper;
import will6366.project_2_part_3.helperObjects.User;

public class CancelHoldLogin extends AppCompatActivity {

    private static final String TAG = "CancelHoldLogin";
    private int numberOfErrors = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancel_hold_login);
    }

    public void cancel_hold_login(View view) {
        Log.d("Cancel_Hold_login"," in login");

        //Get user input
        String username = ( (EditText)findViewById(R.id.cancel_hold_user_name) ).getText().toString();
        String password = ( (EditText)findViewById(R.id.cancel_hold_password) ).getText().toString();

        //Check format of username and password with Regex
        if (isCorrectFormat(username,password)) {
            Log.d("isCorrectFormat", "true");
            DatabaseHelper db = new DatabaseHelper(this);
            try {
                User user = db.getUser(username);
                if (password.equals(user.getPassword())) {
                    db.close();
                    //New intent to send userId
                    Log.d("User which matched",user.toString()+" id is? "+user.getUserId());
                    Intent i = CancelHoldSelection.newIntent(CancelHoldLogin.this, user.getUserId());
                    startActivity(i);
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

}
