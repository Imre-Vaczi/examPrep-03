package vehiclerental;

public class User {

    private String UserName;
    private String email;
    private long balance;

    public User(String userName, String email, long balance) {
        UserName = userName;
        this.email = email;
        this.balance = balance;
    }

    public User minusBalance(long amount) {
        this.balance = Math.max(0, this.balance - amount);
        return this;
    }

    public String getUserName() {
        return UserName;
    }

    public String getEmail() {
        return email;
    }

    public long getBalance() {
        return balance;
    }
}
