package will6366.project_2_part_3.helperObjects;


public class BookTable {
    public static final String TABLE_BOOKS = "books";

    public static final String KEY_ID = "id";
    public static final String KEY_TITLE = "title";
    public static final String KEY_AUTHOR = "author";
    public static final String KEY_ISBN = "isbn";
    public static final String KEY_HOURLY_FEE = "hourly_fee";


    public static final String[] COLUMNS = {KEY_ID, KEY_TITLE, KEY_AUTHOR, KEY_ISBN, KEY_HOURLY_FEE};

    public static final String CREATE_BOOK_TABLE = "CREATE TABLE " + TABLE_BOOKS +"( " +
            KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            KEY_TITLE + " TEXT UNIQUE NOT NULL, " +
            KEY_AUTHOR + " TEXT NOT NULL, " +
            KEY_ISBN + " INT UNIQUE NOT NULL, " +
            KEY_HOURLY_FEE + " DOUBLE NOT NULL)";

}
