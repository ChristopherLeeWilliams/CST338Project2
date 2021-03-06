package will6366.project_2_part_3;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import will6366.project_2_part_3.helperObjects.DatabaseHelper;
import will6366.project_2_part_3.helperObjects.Transaction;
import will6366.project_2_part_3.helperObjects.User;

public class ManageSystem extends AppCompatActivity {

    User admin;
    TextView logs;
    public final String TAG = "ManageSystem";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_system);
        Log.d(TAG,"Arrived");
        logs = (TextView) findViewById(R.id.manage_system_log_data);

        // Get admin user from id
        try {
            DatabaseHelper db = new DatabaseHelper(ManageSystem.this);
            admin = db.getUser(getIntent().getIntExtra("userId", 0));
            db.close();
        } catch (Exception e) {
            // An error logging in
            Toast.makeText(ManageSystem.this, "There was an error logging in!", Toast.LENGTH_SHORT).show();
            startActivity (new Intent(ManageSystem.this, MainMenu.class));
        }

        //Fill TextView "manage_system_log_data" with transaction data
        DatabaseHelper db = new DatabaseHelper(ManageSystem.this);
        try {
            ArrayList<Transaction> transactions = db.getAllTransactions();
            logs.setText("");
            for (int i = 0; i < transactions.size(); i++) {
                logs.setText(logs.getText() +""+ transactions.get(i)+"\n\n");
            }
        } catch (Exception e) {
            Log.d(TAG,e.getMessage());
        }
        db.close();
    }

    public static Intent newIntent(Context packageContext, int userId) {
        Intent intent = new Intent(packageContext, ManageSystem.class);
        intent.putExtra("userId", userId);
        return intent;
    }

    public void addNewBook(View view) {
        Intent i = ManageSystemAddBook.newIntent(ManageSystem.this, admin.getUserId());
        startActivity(i);
    }

    public void resetDatabase(View view) {
        makeConfirmationDeleteAlert("Warning!!!","This option will reset: " +
                "\n\t- Books " +
                "\n\t- Holds " +
                "\n\t- Transactions " +
                "\n\t- Users [!admin2 will remain]" +
                "\nContinue?");
    }

    public void makeConfirmationDeleteAlert(String title, String message) {
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        DatabaseHelper db = new DatabaseHelper(ManageSystem.this);
                        db.deleteAllBooks();
                        db.deleteAllHolds();
                        db.deleteAllUsers();
                        db.deleteAllTransactions();
                        db.addUser(new User("!admin2","!admin2",true));
                        db.close();
                        Toast.makeText(ManageSystem.this, "Database Reset!", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(ManageSystem.this, MainMenu.class));
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
