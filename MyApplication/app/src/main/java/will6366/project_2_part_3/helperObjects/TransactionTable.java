package will6366.project_2_part_3.helperObjects;

public class TransactionTable {

    public static final String TABLE_TRANSACTIONS = "transactions";

    public static final String KEY_ID = "id";
    public static final String KEY_TYPE = "transactionType";
    public static final String KEY_USERNAME = "transactionUsername";
    public static final String KEY_TRANSACTION_DATE = "transactionDate";
    public static final String KEY_TRANSACTION_TIME = "transactionTime";

    // Optional on type:
    public static final String KEY_BOOK_TITLE =  "bookTitle";
    public static final String KEY_BOOK_AUTHOR = "bookAuthor";
    public static final String KEY_BOOK_ISBN = "bookISBN";
    public static final String KEY_BOOK_HOURLY_FEE = "bookHourlyFee";
    public static final String KEY_HOLD_PICKUP_DATE = "holdPickupDate";
    public static final String KEY_HOLD_RETURN_DATE = "holdReturnDate";
    public static final String KEY_HOLD_RESERVATION_NUMBER = "holdReservationNumber";

    public static final String[] COLUMNS = {KEY_ID,KEY_TYPE, KEY_USERNAME, KEY_TRANSACTION_DATE, KEY_TRANSACTION_TIME,
            KEY_BOOK_TITLE, KEY_BOOK_AUTHOR, KEY_BOOK_ISBN, KEY_BOOK_HOURLY_FEE, KEY_HOLD_PICKUP_DATE, KEY_HOLD_RETURN_DATE, KEY_HOLD_RESERVATION_NUMBER};

    public static final String CREATE_TRANSACTION_TABLE = "CREATE TABLE " + TABLE_TRANSACTIONS +"( "+
            KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            KEY_TYPE + " TEXT NOT NULL, " +
            KEY_USERNAME + " TEXT NOT NULL, " +
            KEY_TRANSACTION_DATE + " TEXT NOT NULL, " +
            KEY_TRANSACTION_TIME + " TEXT NOT NULL, " +
            KEY_BOOK_TITLE + " TEXT, " +
            KEY_BOOK_AUTHOR + " TEXT, " +
            KEY_BOOK_ISBN + " INTEGER, " +
            KEY_BOOK_HOURLY_FEE + " DOUBLE, " +
            KEY_HOLD_PICKUP_DATE + " TEXT, " +
            KEY_HOLD_RETURN_DATE + " TEXT, " +
            KEY_HOLD_RESERVATION_NUMBER + " INTEGER)";
}
