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

    private static final String DATABASE_NAME = "sqlite_master";
    private static final int DATABASE_VERSION = 3;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL(BookTable.CREATE_BOOK_TABLE);
        sqLiteDatabase.execSQL(UserTable.CREATE_USER_TABLE);
        sqLiteDatabase.execSQL(HoldTable.CREATE_HOLD_TABLE);
        sqLiteDatabase.execSQL(TransactionTable.CREATE_TRANSACTION_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + BookTable.TABLE_BOOKS);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + UserTable.TABLE_USERS);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + HoldTable.HOLDS_DATA);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TransactionTable.TABLE_TRANSACTIONS);
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

    public void addHold(Hold hold) throws SQLException {
        Log.d("addHold", hold.toString());
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(HoldTable.KEY_USER_ID, hold.getUserId());
        values.put(HoldTable.KEY_BOOK_ID, hold.getBookId());
        values.put(HoldTable.KEY_PICKUP_DATE, hold.getPickupDate());
        values.put(HoldTable.KEY_RETURN_DATE, hold.getReturnDate());
        values.put(HoldTable.KEY_PRICE, hold.getPrice());

        db.insertOrThrow(HoldTable.HOLDS_DATA, null, values);
        db.close();
    }

    public void addTransaction(Transaction t) throws SQLException {
        Log.d("addTransaction", t.toString());
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        //values.put(TransactionTable.KEY_ID, t.getTransactionId());
        values.put(TransactionTable.KEY_TYPE, t.getTransactionType());
        values.put(TransactionTable.KEY_USERNAME, t.getTransactionUsername());
        values.put(TransactionTable.KEY_TRANSACTION_DATE, t.getTransactionDate());
        values.put(TransactionTable.KEY_TRANSACTION_TIME, t.getTransactionTime());
        values.put(TransactionTable.KEY_BOOK_TITLE, t.getTransactionBookTitle());
        values.put(TransactionTable.KEY_BOOK_AUTHOR, t.getTransactionBookAuthor());
        values.put(TransactionTable.KEY_BOOK_ISBN, t.getTransactionBookISBN());
        values.put(TransactionTable.KEY_BOOK_HOURLY_FEE, t.getTransactionBookHourlyFee());
        values.put(TransactionTable.KEY_HOLD_PICKUP_DATE, t.getTransactionHoldPickupDate());
        values.put(TransactionTable.KEY_HOLD_RETURN_DATE, t.getTransactionHoldReturnDate());
        values.put(TransactionTable.KEY_HOLD_RESERVATION_NUMBER, t.getTransactionHoldReservationNumber());


        db.insertOrThrow(TransactionTable.TABLE_TRANSACTIONS, null, values);
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

    public void deleteHold(int id) {
            SQLiteDatabase db = this.getReadableDatabase();
            db.delete(HoldTable.HOLDS_DATA,
                    HoldTable.KEY_ID + " = ? ",
                    new String[]{String.valueOf(id)});
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
        book.setISBN(Integer.parseInt(cursor.getString(3)));
        book.setHourlyFee(Double.parseDouble(cursor.getString(4)));
        Log.d("getBook(" + id + ")", book.toString());
        db.close();
        return book;
    }

    public Hold getHold(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(HoldTable.HOLDS_DATA,
                HoldTable.COLUMNS,
                HoldTable.KEY_ID+" = ?",
                new String[] { String.valueOf(id) },
                null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }
        // KEY_ID, user_id, book_id, pickup_date, return_date, price
        Hold hold = new Hold();
        hold.setId(Integer.parseInt(cursor.getString(0)));
        hold.setUserId(Integer.parseInt(cursor.getString(1)));
        hold.setBookId(Integer.parseInt(cursor.getString(2)));
        hold.setPickupDate(cursor.getString(3));
        hold.setReturnDate(cursor.getString(4));
        hold.setPrice(Double.parseDouble(cursor.getString(5)));

        Log.d("getHold(" + id + ")", hold.toString());
        db.close();
        return hold;
    }

    // find unique hold
    public Hold getHold(int bookId, String pickupDate) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectionArgs = HoldTable.KEY_BOOK_ID + " = ? AND " + HoldTable.KEY_PICKUP_DATE + " = ? ";

        Cursor cursor = db.query(HoldTable.HOLDS_DATA,
                HoldTable.COLUMNS,
                selectionArgs,
                new String[] { String.valueOf(bookId), pickupDate },
                null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }
        // KEY_ID, user_id, book_id, pickup_date, return_date, price
        Hold hold = new Hold();
        hold.setId(Integer.parseInt(cursor.getString(0)));
        hold.setUserId(Integer.parseInt(cursor.getString(1)));
        hold.setBookId(Integer.parseInt(cursor.getString(2)));
        hold.setPickupDate(cursor.getString(3));
        hold.setReturnDate(cursor.getString(4));
        hold.setPrice(Double.parseDouble(cursor.getString(5)));

        db.close();
        return hold;
    }

    // find unique hold
    public Hold getHold(String pickupDate) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(HoldTable.HOLDS_DATA,
                HoldTable.COLUMNS,
                HoldTable.KEY_PICKUP_DATE + " = ? ",
                new String[] { pickupDate },
                null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }
        // KEY_ID, user_id, book_id, pickup_date, return_date, price
        Hold hold = new Hold();
        hold.setId(Integer.parseInt(cursor.getString(0)));
        hold.setUserId(Integer.parseInt(cursor.getString(1)));
        hold.setBookId(Integer.parseInt(cursor.getString(2)));
        hold.setPickupDate(cursor.getString(3));
        hold.setReturnDate(cursor.getString(4));
        hold.setPrice(Double.parseDouble(cursor.getString(5)));

        db.close();
        return hold;
    }

    public User getUser(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(UserTable.TABLE_USERS,
                UserTable.COLUMNS,
                UserTable.KEY_ID + "  = ?",
                new String[] { String.valueOf(userId) },
                null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }
        // KEY_ID, KEY_USERNAME, KEY_PASSWORD, KEY_ADMIN
        User user = new User();
        user.setUserId(Integer.parseInt(cursor.getString(0)));
        user.setUsername(cursor.getString(1));
        user.setPassword(cursor.getString(2));
        boolean admin = Integer.parseInt(cursor.getString(3))==1?true:false;
        user.setAdmin(admin);
        Log.d("getUser(" + user + ")", user.toString());
        db.close();
        return user;
    }

    public User getUser(String username) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(UserTable.TABLE_USERS,
                UserTable.COLUMNS,
                " username = ?",
                new String[] { username },
                null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }
        // KEY_ID, KEY_USERNAME, KEY_PASSWORD, KEY_ADMIN
        User user = new User();
        user.setUserId(Integer.parseInt(cursor.getString(0)));
        user.setUsername(cursor.getString(1));
        user.setPassword(cursor.getString(2));
        boolean admin = Integer.parseInt(cursor.getString(3))==1?true:false;
        user.setAdmin(admin);
        Log.d("getUser(" + username + ")", user.toString());
        db.close();
        return user;
    }

    public ArrayList<Book> getAllBooks() {
        ArrayList<Book> books = new ArrayList<>();
        String query = "SELECT * FROM " + BookTable.TABLE_BOOKS;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(query,null);

        Book book;

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

    public ArrayList<Book> getAllBooksAvailable(String startDate, String endDate) {
        ArrayList<Book> books = this.getAllBooks();
        ArrayList<Book> booksAvailable = new ArrayList<>();
        ArrayList<Hold> holds = this.getAllHolds();
        ArrayList<Hold> importantHolds = new ArrayList<>();

        // get all the holds which interfere with the given period
        // Ignore: if (hold pickup date >= end Date) or (hold return date <= start date)
        Hold temp;
        for(int i = 0; i < holds.size(); i++) {
            temp = holds.get(i);
            Log.d("CheckingDates\n","startDate("+startDate+") compare to returnDate("+temp.getReturnDate()+") [ignore if >= 0]:"+(startDate.compareTo(temp.getReturnDate())));
            Log.d("","endDate("+endDate+") compare to pickupDate("+temp.getPickupDate()+") [ignore if <= 0]"+ (endDate.compareTo(temp.getPickupDate())));
            if ((startDate.compareTo(temp.getReturnDate()) < 0) || (endDate.compareTo(temp.getPickupDate()) < 0)){
                importantHolds.add(holds.get(i));
            }
        }

        // book id's found in importandHolds are not available (skip them and add the rest)
        Book tempBook;
        // Hold temp;
        boolean skip;
        for(int i = 0; i < books.size(); i++){
            skip = false;
            tempBook = books.get(i);
            for(int j = 0; j < importantHolds.size(); j++) {
                temp = importantHolds.get(j);
                if(tempBook.getId() == temp.getBookId()) { skip = true;  }
            }
            if(!skip) {
                booksAvailable.add(tempBook);
            }
        }
        return booksAvailable;
    }

    public ArrayList<Hold> getAllHolds() {
        ArrayList<Hold> holds = new ArrayList<>();
        String query = "SELECT * FROM " + HoldTable.HOLDS_DATA;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(query,null);

        Hold hold;

        // KEY_ID, user_id, book_id, pickup_date, return_date, price
        if(cursor.moveToFirst()) {
            do {
                hold = new Hold();
                hold.setId(Integer.parseInt(cursor.getString(0)));
                hold.setUserId(Integer.parseInt(cursor.getString(1)));
                Log.d("Hold Date","userId = "+cursor.getString(1));
                hold.setBookId(Integer.parseInt(cursor.getString(2)));
                hold.setPickupDate(cursor.getString(3));
                hold.setReturnDate(cursor.getString(4));
                hold.setPrice(Double.parseDouble(cursor.getString(5)));
                holds.add(hold);
            } while (cursor.moveToNext());
        }

        Log.d("getAllHolds()", holds.toString());
        return holds;
    }

    public ArrayList<Transaction> getAllTransactions() {
        ArrayList<Transaction> transactions = new ArrayList<>();
        String query = "SELECT * FROM " + TransactionTable.TABLE_TRANSACTIONS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query,null);

       Transaction transaction;

        // KEY_ID, user_id, book_id, pickup_date, return_date, price
        if(cursor.moveToFirst()) {
            do {
                transaction = new Transaction();
                transaction.setTransactionId(Integer.parseInt(cursor.getString(0)));
                transaction.setTransactionType(cursor.getString(1));
                transaction.setTransactionUsername(cursor.getString(2));
                transaction.setTransactionDate(cursor.getString(3));
                transaction.setTransactionTime(cursor.getString(4));
                transaction.setTransactionBookTitle(cursor.getString(5));
                transaction.setTransactionBookAuthor(cursor.getString(6));
                transaction.setTransactionBookISBN(Integer.parseInt(cursor.getString(7)));
                transaction.setTransactionBookHourlyFee(Double.parseDouble(cursor.getString(8)));
                transaction.setTransactionHoldPickupDate(cursor.getString(9));
                transaction.setTransactionHoldReturnDate(cursor.getString(10));
                transaction.setTransactionHoldReservationNumber(Integer.parseInt(cursor.getString(11)));
                transactions.add(transaction);
            } while (cursor.moveToNext());
        }

        //Log.d("getAllTransactions()", transactions.toString());
        return transactions;
    }

    public ArrayList<Hold> getAllUserHolds(int userId) {
        ArrayList<Hold> holds = new ArrayList<>();
        String query = "SELECT * FROM " + HoldTable.HOLDS_DATA +" WHERE "+HoldTable.KEY_USER_ID+" = "+userId;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(query,null);

        Hold hold;

        // KEY_ID, user_id, book_id, pickup_date, return_date, price
        if(cursor.moveToFirst()) {
            do {
                hold = new Hold();
                hold.setId(Integer.parseInt(cursor.getString(0)));
                hold.setUserId(Integer.parseInt(cursor.getString(1)));
                hold.setBookId(Integer.parseInt(cursor.getString(2)));
                hold.setPickupDate(cursor.getString(3));
                hold.setReturnDate(cursor.getString(4));
                hold.setPrice(Double.parseDouble(cursor.getString(5)));
                holds.add(hold);
            } while (cursor.moveToNext());
        }

        Log.d("getAllUserHolds()", holds.toString());
        return holds;
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

    public void deleteAllBooks() {

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(BookTable.TABLE_BOOKS,"",new String[]{});
        db.close();

        Log.d("deleteAllBooks", "Deleted all Books");
    }

    public void deleteAllHolds() {

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(HoldTable.HOLDS_DATA,"",new String[]{});
        db.close();

        Log.d("deleteAllHolds", "Deleted all Holds");
    }

    public void deleteAllUsers() {

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(UserTable.TABLE_USERS,"",new String[]{});
        db.close();

        Log.d("deleteAllUsers", "Deleted all Users");
    }

    public void deleteAllTransactions() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TransactionTable.TABLE_TRANSACTIONS,"",new String[]{});
        db.close();

        Log.d("deleteAllTransactions", "Deleted all Transactions");
    }
}