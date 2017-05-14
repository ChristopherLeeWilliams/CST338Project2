package will6366.project_2_part_3.helperObjects;

/**
 * Created by Null on 5/12/2017.
 */

public class Hold {
    private int mId;
    private int mBookId;
    private int mUserId;
    private String mPickupDate;
    private String mreturnDate;
    private double mPrice;

    public Hold() {
        mId = 0;
        mBookId = 0;
        mUserId = 0;
        mPickupDate = "0000-00-00 00:00:00";
        mreturnDate = "0000-00-00 00:00:00";
        mPrice = 0;
    }

    public Hold(int id, int bookId, int userId, String pickupDate, String mreturnDate, double price) {
        mId = id;
        mBookId = bookId;
        mUserId = userId;
        mPickupDate = pickupDate;
        this.mreturnDate = mreturnDate;
        mPrice = price;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public int getBookId() {
        return mBookId;
    }

    public void setBookId(int bookId) {
        mBookId = bookId;
    }

    public int getUserId() {
        return mUserId;
    }

    public void setUserId(int userId) {
        mUserId = userId;
    }

    public String getPickupDate() {
        return mPickupDate;
    }

    public void setPickupDate(String pickupDate) {
        mPickupDate = pickupDate;
    }

    public String getReturnDate() {
        return mreturnDate;
    }

    public void setReturnDate(String mreturnDate) {
        this.mreturnDate = mreturnDate;
    }

    public double getPrice() {
        return mPrice;
    }

    public void setPrice(double price) {
        mPrice = price;
    }

    @Override
    public String toString() {
        return "Hold{" +
                ", mBookId=" + mBookId +
                ", mUserId=" + mUserId +
                ", mPickupDate='" + mPickupDate + '\'' +
                ", mReturnDate='" + mreturnDate + '\'' +
                ", mPrice=" + mPrice +
                '}';
    }
}
