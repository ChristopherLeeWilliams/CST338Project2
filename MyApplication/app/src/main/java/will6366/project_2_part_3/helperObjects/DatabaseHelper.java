package will6366.project_2_part_3.helperObjects;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import java.util.ArrayList;


public class DatabaseHelper extends SQLiteOpenHelper{

    private static final String DATABASE_NAME = "library";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL(BookTable.CREATE_BOOK_TABLE);
        sqLiteDatabase.execSQL(UserTable.CREATE_USER_TABLE);
        //TODO: insert the admin
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS books");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS users");

        this.onCreate(sqLiteDatabase);
    }

    public void addBook(Book book) throws SQLException {
        Log.d("addBook", book.toString());
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(BookTable.KEY_TITLE, book.getTitle());
        values.put(BookTable.KEY_AUTHOR, book.getAuthor());
        values.put(BookTable.KEY_ISBN, book.getISBN());
        values.put(BookTable.KEY_HOURLY_FEE, book.getHourlyFee());

        db.insertOrThrow(BookTable.TABLE_BOOKS, null, values);
        db.close();
    }

    public void addUser(User user) throws SQLException{
        Log.d("addUser", user.toString());
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(UserTable.KEY_USERNAME, user.getUsername());
        values.put(UserTable.KEY_PASSWORD, user.getPassword());
        values.put(UserTable.KEY_ADMIN, user.isAdmin());

        db.insertOrThrow(UserTable.TABLE_USERS, null, values);
        db.close();
    }


    public Book getBook(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(BookTable.TABLE_BOOKS,
                BookTable.COLUMNS,
                " id = ?",
                new String[] { String.valueOf(id) },
                null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }

        Book book = new Book();
        book.setId(Integer.parseInt(cursor.getString(0)));
        book.setTitle(cursor.getString(1));
        book.setAuthor(cursor.getString(2));
        Log.d("getBook(" + id + ")", book.toString());

        return book;
    }

    public ArrayList<Book> getAllBooks() {
        ArrayList<Book> books = new ArrayList<>();
        String query = "SELECT * FROM " + BookTable.TABLE_BOOKS;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(query,null);

        Book book = null;

        if(cursor.moveToFirst()) {
            do {
                book = new Book();
                book.setId(Integer.parseInt(cursor.getString(0)));
                book.setTitle(cursor.getString(1));
                book.setAuthor(cursor.getString(2));
                book.setISBN(Integer.parseInt(cursor.getString(3)));
                book.setHourlyFee(Float.parseFloat(cursor.getString(4)));
                books.add(book);
            } while (cursor.moveToNext());
        }

        Log.d("getAllBooks()", books.toString());
        return books;
    }

    public ArrayList<String> getAllBookTitlesWithAuthors() {
        ArrayList<String> books = new ArrayList<>();
        String query = "SELECT * FROM " + BookTable.TABLE_BOOKS;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(query,null);

        Book book = null;

        if(cursor.moveToFirst()) {
            do {
                books.add(cursor.getString(1)+" By "+cursor.getString(2));
            } while (cursor.moveToNext());
        }
        return books;
    }

    public int updateBook (Book book) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("title", book.getTitle());
        values.put("author", book.getAuthor());

        int i = db.update(BookTable.TABLE_BOOKS,
                values,
                BookTable.KEY_ID + " = ? ",
                new String[] { String.valueOf(book.getId())});

        db.close();
        return i;
    }


    public void deleteBook(Book book) {

        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(BookTable.TABLE_BOOKS,
                BookTable.KEY_ID + " = ?",
                new String[] { String.valueOf(book.getId())});

        db.close();

        Log.d("deleteBook", book.toString());
    }
}