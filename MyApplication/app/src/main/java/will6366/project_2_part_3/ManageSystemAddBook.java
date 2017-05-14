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

import java.text.SimpleDateFormat;
import java.util.Date;

import will6366.project_2_part_3.helperObjects.Book;
import will6366.project_2_part_3.helperObjects.DatabaseHelper;
import will6366.project_2_part_3.helperObjects.Transaction;

public class ManageSystemAddBook extends AppCompatActivity {

    EditText title, author, isbn, hourlyFee;
    String bookTitle;
    String bookAuthor;
    int bookISBN, userId;
    double bookHourlyFee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_system_add_book);
        title = (EditText) findViewById(R.id.book_title_input);
        author = (EditText) findViewById(R.id.book_author_input);
        isbn = (EditText) findViewById(R.id.book_isbn_input);
        hourlyFee = (EditText) findViewById(R.id.book_hourly_fee_input);
        userId = getIntent().getIntExtra("userId",0);
    }

    public void makeConfirmationAddBookAlert(String title, String message) {
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            DatabaseHelper db = new DatabaseHelper(ManageSystemAddBook.this);
                            db.addBook(new Book(bookTitle,bookAuthor,bookISBN,bookHourlyFee));
                            try {
                                // -------------------------------------------- TRANSACTION STUFF ---------------------------------------------
                            /*
                            public Transaction(String transactionType, String username, String transactionDate, String transactionTime, String bookTitle, String bookAuthor,
                                    int bookISBN, double bookHourlyFee, String holdPickupDate, String holdReturnDate, int holdReservationNumber)
                             */

                                String date = new SimpleDateFormat("yyyy/MM/dd").format(new Date());
                                String time = new SimpleDateFormat("HH:mm:ss").format(new Date());
                                db.addTransaction(new Transaction("Book Added", db.getUser(userId).getUsername(), date, time, bookTitle, bookAuthor, bookISBN, bookHourlyFee, "", "", 0));
                                // ------------------------------------------ END TRANSACTION STUFF ------------------------------------------
                            } catch (Exception e) {
                                Log.d("ManageSystemAddBook",e.getMessage());
                            }
                            db.close();
                            Toast.makeText(ManageSystemAddBook.this, "Book added successfully!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(ManageSystemAddBook.this, MainMenu.class));
                        } catch (Exception sql) {
                            Toast.makeText(ManageSystemAddBook.this, "Book title or isbn already exist in database!", Toast.LENGTH_LONG).show();
                        }
                    }
                })
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .show();
    }

    public void manage_system_add_book_submit(View view) {
        try {
            bookTitle = title.getText().toString();
            bookAuthor = author.getText().toString();
            bookISBN = Integer.parseInt(isbn.getText().toString());
            bookHourlyFee = Double.parseDouble(hourlyFee.getText().toString());

            makeConfirmationAddBookAlert("Confirmation",
                    "Book Information:" +
                            "\n\t- Title: "+bookTitle +
                            "\n\t- Author: "+ bookAuthor +
                            "\n\t- ISBN: "+ bookISBN +
                            "\n\t- Hourly Fee: "+ bookHourlyFee+
                            "\n Is this correct?");
        } catch (Exception e) {
            Toast.makeText(this, "Invalid Input", Toast.LENGTH_SHORT).show();
        }


    }

    public static Intent newIntent(Context packageContext, int userId) {
        Intent intent = new Intent(packageContext, ManageSystemAddBook.class);
        intent.putExtra("userId",userId);
        return intent;
    }
}
