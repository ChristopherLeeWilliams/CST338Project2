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

public class ManageSystemLogin extends AppCompatActivity {

    private static final String TAG = "ManageSystemLogin";
    private int numberOfErrors = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_system_login);
    }


    public void manage_system_login(View view) {
        Log.d("Cancel_Hold_login"," in login");

        //Get user input
        String username = ( (EditText)findViewById(R.id.manage_system_user_name) ).getText().toString();
        String password = ( (EditText)findViewById(R.id.manage_system_password) ).getText().toString();

        //Check format of username and password with Regex
        if (isCorrectFormat(username,password)) {
            Log.d("isCorrectFormat", "true");
            DatabaseHelper db = new DatabaseHelper(this);
            try {
                User user = db.getUser(username);
                if (password.equals(user.getPassword())) {
                    db.close();
                    if(user.isAdmin() == 1) {
                        //New intent to send userId
                        Intent i = ManageSystem.newIntent(ManageSystemLogin.this, user.getUserId());
                        startActivity(i);
                    } else {
                        Toast.makeText(this,"Manage System is only available for administrators.", Toast.LENGTH_LONG).show();
                        db.close();
                        numberOfErrors++;
                        if (numberOfErrors == 2) {startActivity(new Intent(this,MainMenu.class));}
                    }
                } else {
                    Toast.makeText(this,"Incorrect Password", Toast.LENGTH_SHORT).show();
                    db.close();
                    numberOfErrors++;
                    if (numberOfErrors == 2) {startActivity(new Intent(this,MainMenu.class));}
                }
                //startActivity(new Intent(this,MainMenu.class));

            } catch (Exception e) {
                // catch exception thrown from SQLite
                Toast.makeText(this,"Something went wrong: "+e.getMessage(), Toast.LENGTH_LONG).show();
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
