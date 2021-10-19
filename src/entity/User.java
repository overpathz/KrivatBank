package entity;

public class User {

    private int id;
    private String username;
    private String password;
    private int balance;
    private String cardNo;

    public User() {
    }

    public User(int id, String username, String password, int balance, String cardNo) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.balance = balance;
        this.cardNo = cardNo;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(String username, String password, int balance, String cardNo) {
        this.username = username;
        this.password = password;
        this.balance = balance;
        this.cardNo = cardNo;
    }

    public void setData(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.balance = user.getBalance();
        this.cardNo = user.getCardNo();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", balance='" + balance + '\'' +
                ", cardNo='" + cardNo + '\'' +
                '}';
    }
}
