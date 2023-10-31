public class Admin extends Users {
    private String password;

    public Admin(String name, String password, Economy economy) {
        super(name, economy);
        setPassword(password);
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
