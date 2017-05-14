package will6366.project_2_part_3.helperObjects;

// Design:
/**
 Keeps track of:
 - Account Creations
 - Transaction Type: New Account
 - Customer's Username:
 - Transaction Date:
 - Transaction Time:
 - Placed Holds
 - Transaction Type: Place Hold
 - Customer's Username:
 - Book Title:
 - Pickup Date/time
 - Return Date/time
 - Reservation Number
 - Transaction Date:
 - Transaction Time:
 - Cancelled Holds
 - Transaction Type: Cancel Hold
 - ALL OTHER: SAME AS PLACED HOLDS
 - Books added
 - Transaction Type: Book Added
 - Administrator Username:
 - Book Info
 - Transaction Date:
 - Transaction Time:
 */

public class Transaction {

    int mId;
    String mTransactionType;
    String mUsername;
    String mTransactionDate;
    String mTransactionTime;

    // Optional on type:
    String mBookTitle;
    String mBookAuthor;
    int mBookISBN;
    double mBookHourlyFee;
    String mHoldPickupDate;
    String mHoldReturnDate;
    int mHoldReservationNumber;

    public Transaction() {
        mId = 0;
        mTransactionType = "";
        mUsername = "";
        mTransactionDate = "";
        mTransactionTime = "";

        // Optional on type:
        mBookTitle = "";
        mBookAuthor = "";
        mBookISBN = 0;
        mBookHourlyFee = 0;
        mHoldPickupDate = "";
        mHoldReturnDate = "";
        mHoldReservationNumber = 0;
    }

    public Transaction(String transactionType, String username, String transactionDate, String transactionTime, String bookTitle, String bookAuthor,
                            int bookISBN, double bookHourlyFee, String holdPickupDate, String holdReturnDate, int holdReservationNumber) {
        mId = 0;
        mTransactionType = transactionType;
        mUsername = username;
        mTransactionDate = transactionDate;
        mTransactionTime = transactionTime;

        // Optional on type:
        mBookTitle = bookTitle;
        mBookAuthor = bookAuthor;
        mBookISBN = bookISBN;
        mBookHourlyFee = bookHourlyFee;
        mHoldPickupDate = holdPickupDate;
        mHoldReturnDate = holdReturnDate;
        mHoldReservationNumber = holdReservationNumber;
    }

    public int getTransactionId() {
        return mId;
    }

    public void setTransactionId(int id) {
        mId = id;
    }

    public String getTransactionType() {
        return mTransactionType;
    }

    public void setTransactionType(String transactionType) {
        mTransactionType = transactionType;
    }

    public String getTransactionUsername() {
        return mUsername;
    }

    public void setTransactionUsername(String username) {
        mUsername = username;
    }

    public String getTransactionDate() {
        return mTransactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        mTransactionDate = transactionDate;
    }

    public String getTransactionTime() {
        return mTransactionTime;
    }

    public void setTransactionTime(String transactionTime) {
        mTransactionTime = transactionTime;
    }

    public String getTransactionBookTitle() {
        return mBookTitle;
    }

    public void setTransactionBookTitle(String bookTitle) {
        mBookTitle = bookTitle;
    }

    public String getTransactionBookAuthor() {
        return mBookAuthor;
    }

    public void setTransactionBookAuthor(String bookAuthor) {
        mBookAuthor = bookAuthor;
    }

    public int getTransactionBookISBN() {
        return mBookISBN;
    }

    public void setTransactionBookISBN(int bookISBN) {
        mBookISBN = bookISBN;
    }

    public double getTransactionBookHourlyFee() {
        return mBookHourlyFee;
    }

    public void setTransactionBookHourlyFee(double bookHourlyFee) {
        mBookHourlyFee = bookHourlyFee;
    }

    public String getTransactionHoldPickupDate() {
        return mHoldPickupDate;
    }

    public void setTransactionHoldPickupDate(String holdPickupDate) {
        mHoldPickupDate = holdPickupDate;
    }

    public String getTransactionHoldReturnDate() {
        return mHoldReturnDate;
    }

    public void setTransactionHoldReturnDate(String holdReturnDate) {
        mHoldReturnDate = holdReturnDate;
    }

    public int getTransactionHoldReservationNumber() {
        return mHoldReservationNumber;
    }

    public void setTransactionHoldReservationNumber(int holdReservationNumber) {
        mHoldReservationNumber = holdReservationNumber;
    }

    @Override
    public String toString() {
        // Print Based on Transaction Type
        if (this.mTransactionType.equals("New Account")) {
            return "Transaction Type: New Account" +
                    "\nCustomer's Username: " + this.mUsername +
                    "\nTransaction Date" + this.mTransactionDate +
                    "\nTransaction Time" + this.mTransactionTime;
        } else if (this.mTransactionType.equals("Place Hold") || this.mTransactionType.equals("Cancel Hold") ) {
            return "Transaction Type: " + this.mTransactionType +
                    "\nCustomer's Username: " + this.mUsername +
                    "\nBook Title: " + this.mBookTitle +
                    "\nPickup Date/Time: " + this.mHoldPickupDate+
                    "\nReturn Date/Time: " + this.mHoldReturnDate+
                    "\nReservation Number: " + this.mHoldReservationNumber+
                    "\nTransaction Date" + this.mTransactionDate +
                    "\nTransaction Time" + this.mTransactionTime;
        } else if (this.mTransactionType.equals("Book Added")) {
            return "Transaction Type: Book Added" +
                    "\nAdministrator Username: " + this.mUsername +
                    "\nBook Title: "+this.mBookTitle+
                    "\nBook Author: "+this.mBookAuthor+
                    "\nBook ISBN: "+this.mBookISBN+
                    "\nBook Hourly Fee: "+this.mBookHourlyFee+
                    "\nTransaction Date" + this.mTransactionDate +
                    "\nTransaction Time" + this.mTransactionTime;
        }
        return "Invalid Transaction Type";
    }
}
