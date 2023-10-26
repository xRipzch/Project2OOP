import  java.util.Scanner;
public class Users {
    private String name;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Users (String name) {
        setName(name);
    }

    public void logIn() {
        Scanner scanner = new Scanner(System.in);
        String name = scanner.nextLine();
        String password = scanner.nextLine();

        if (name.equals("harry") && password.equals("hairyharry")) {
            System.out.println("Welcome Harry");
            setName("Harry");
        } else if (name.equals("revisor") && password.equals("hairyharry")) {
            System.out.println("Welcome Accountant");
            setName("Accountant");
        } else {
            System.out.println("Wrong password or username");
            System.out.println("Try again");
            logIn();
        }
    }
}
