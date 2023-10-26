public class admin extends Users{
    private  String password;

    public admin (String name, String password) {
        super(name);
        setPassword(password);
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
