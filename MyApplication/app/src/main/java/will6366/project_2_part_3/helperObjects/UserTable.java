package will6366.project_2_part_3.helperObjects;


public class UserTable {
    public static final String TABLE_USERS = "users";

    public static final String KEY_ID = "id";
    public static final String KEY_USERNAME = "username";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_ADMIN = "isAdmin";

    public static final String[] COLUMNS = {KEY_ID, KEY_USERNAME, KEY_PASSWORD, KEY_ADMIN};

    public static final String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USERS +"( " +
            KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            KEY_USERNAME + " TEXT UNIQUE NOT NULL, " +
            KEY_PASSWORD + " TEXT NOT NULL, " +
            KEY_ADMIN + " INTEGER)";

}
