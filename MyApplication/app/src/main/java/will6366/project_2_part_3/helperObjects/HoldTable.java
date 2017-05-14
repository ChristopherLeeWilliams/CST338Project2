package will6366.project_2_part_3.helperObjects;


public class HoldTable {
    public static final String HOLDS_DATA = "holds";

    public static final String KEY_ID = "id";
    public static final String KEY_USER_ID = "userId";
    public static final String KEY_BOOK_ID = "bookId";
    public static final String KEY_PICKUP_DATE = "pickupDate";
    public static final String KEY_RETURN_DATE = "returnDate";
    public static final String KEY_PRICE = "price";

    public static final String[] COLUMNS = {KEY_ID, KEY_USER_ID, KEY_BOOK_ID, KEY_PICKUP_DATE, KEY_RETURN_DATE, KEY_PRICE};

    public static final String CREATE_HOLD_TABLE = "CREATE TABLE " + HOLDS_DATA +"( " +
            KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            KEY_USER_ID + " INT NOT NULL, " +
            KEY_BOOK_ID + " INT NOT NULL, " +
            KEY_PICKUP_DATE + " TEXT NOT NULL, "+
            KEY_RETURN_DATE + " TEXT NOT NULL, " +
            KEY_PRICE + " DOUBLE NOT NULL)";
}
