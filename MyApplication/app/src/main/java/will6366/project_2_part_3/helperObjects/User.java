package will6366.project_2_part_3.helperObjects;

/**
 * Created by Null on 5/11/2017
 */


public class User {
    private String username;
    private String password;
    private boolean isAdmin;
    private int userId;

    public User() {
        this.username = "";
        this.password = "";
        this.isAdmin = false;
    }

    public User(String username, String password, boolean isAdmin) {
        this.username = username;
        this.password = password;
        this.isAdmin = isAdmin;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int isAdmin() {
        return isAdmin? 1:0;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", Admin=" + isAdmin +
                '}';
    }
}
