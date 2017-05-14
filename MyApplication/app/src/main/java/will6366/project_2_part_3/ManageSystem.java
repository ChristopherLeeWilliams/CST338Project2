package will6366.project_2_part_3;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import will6366.project_2_part_3.helperObjects.DatabaseHelper;
import will6366.project_2_part_3.helperObjects.User;

public class ManageSystem extends AppCompatActivity {

    User admin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_system);

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

        //TODO: fill TextView "manage_system_log_data" with transaction data

    }

    public static Intent newIntent(Context packageContext, int userId) {
        Intent intent = new Intent(packageContext, ManageSystem.class);
        intent.putExtra("userId", userId);
        return intent;
    }

    public void addNewBook(View view) {
        startActivity(new Intent(ManageSystem.this,ManageSystemAddBook.class));
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
