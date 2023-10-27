public class Admin extends Users{
    private  String password;

    public Admin(String name, String password) {
        super(name);
        setPassword(password);
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
