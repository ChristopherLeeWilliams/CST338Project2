package will6366.project_2_part_3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import will6366.project_2_part_3.helperObjects.Book;
import will6366.project_2_part_3.helperObjects.DatabaseHelper;
import will6366.project_2_part_3.helperObjects.User;

public class CancelHold extends AppCompatActivity {

    private static final String TAG = "CancelHold";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancel_hold);
    }

    public void doStuff(View view) {
        DatabaseHelper db = new DatabaseHelper(this);
        try {
            db.deleteAllBooks();
            db.addBook(new Book(1, "Hot Java", "S. Narayanan", 123101,.05));
            db.addBook(new Book(1, "Fun Java", "Y. Byun", 300972, 1.0));
            db.addBook(new Book(1, "Algorithm for Java", "K. Alice", 777123,.25));
        } catch (Exception e) {
            Log.d(TAG,e.getMessage());
        }
        db.close();
    }
}
