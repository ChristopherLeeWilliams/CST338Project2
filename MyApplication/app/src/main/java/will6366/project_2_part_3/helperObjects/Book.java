package will6366.project_2_part_3.helperObjects;

/**
 * Created by Null on 5/11/2017.
 */

public class Book {

    private int mId;
    private String mTitle;
    private String mAuthor;
    private int mISBN;
    private double mHourlyFee;

    public Book() {
        mTitle = "";
        mAuthor = "";
        mISBN = 0;
        mHourlyFee = 0;
    }

    public Book(String title, String author) {
        mTitle = title;
        mAuthor = author;
        mISBN = 0;
        mHourlyFee = 0;
    }

    public Book(String title, String author, int ISBN) {
        mTitle = title;
        mAuthor = author;
        mISBN = ISBN;
        mHourlyFee = 0;
    }

    public Book(String title, String author, int ISBN, double hourlyFee) {
        mId = 0;
        mTitle = title;
        mAuthor = author;
        mISBN = ISBN;
        mHourlyFee = hourlyFee;
    }

    public Book(int id, String title, String author, int ISBN, double hourlyFee) {
        mId = id;
        mTitle = title;
        mAuthor = author;
        mISBN = ISBN;
        mHourlyFee = hourlyFee;
    }

    public int getId() {
        return mId;
    }

    public int getISBN() {
        return mISBN;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getAuthor() {
        return mAuthor;
    }

    public double getHourlyFee() {
        return mHourlyFee;
    }

    public void setHourlyFee(double hourlyFee) {
        mHourlyFee = hourlyFee;
    }

    public void setId(int id) {
        this.mId = id;
    }

    public void setISBN(int id) {
        this.mISBN = id;
    }

    public void setTitle(String title) {
        this.mTitle = title;
    }


    public void setAuthor(String author) {
        this.mAuthor = author;
    }

    @Override
    public String toString() {
        return "Book{" +
                ", mTitle='" + mTitle + '\'' +
                ", mAuthor='" + mAuthor + '\'' +
                ", mISBN=" + mISBN +
                ", mHourlyFee=" + mHourlyFee +
                '}';
    }
}