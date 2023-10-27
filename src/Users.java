import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Users {
    private boolean loggedIn;
    private String name;

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }


    public Users(String name) {
        setName(name);
    }

    public void logIn() {

        Scanner scanner = new Scanner(System.in);
        System.out.println("(9) Back to menu");
        System.out.println("Enter username: ");
        String name = scanner.nextLine();
        System.out.println("Enter password: ");
        String password = scanner.nextLine();

        if (name.equals("harry") && password.equals("hairyharry")) {
            System.out.println("Welcome Harry");
            econMenu();
            setName("Harry");
            setLoggedIn(true);


        } else if (name.equals("revisor") && password.equals("hairyharry")) {
            System.out.println("Welcome Accountant");
            econMenu();
            setName("Accountant");
            setLoggedIn(true);


        } else if (name.equals("9") || (password.equals("9"))) {
            ArrayList<Reservation> reservations = new ArrayList<>();
            Menu menu = new Menu(reservations);
            menu.run();
        } else {
            System.out.println("Wrong password or username");
            System.out.println("Try again");
            setLoggedIn(false);
            logIn();
        }
    }


    public void readFile() {
        Scanner fileScanner = null;
        File file = new File("economy.txt");
        try {
            fileScanner = new Scanner(file);
            while (fileScanner.hasNextLine()) {
                String s = fileScanner.nextLine();
                System.out.println("Data for economy in file: " + s);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        fileScanner.close();

    }


    private void econMenu() {
        Scanner scanner = new Scanner(System.in);
        int choose;
        do {
            ArrayList<Reservation> reservations = new ArrayList<>();
            Menu menu = new Menu(reservations);
            System.out.println("1. Search economy");
            System.out.println("2. Total economy");
            System.out.println("3. Print economy");
            System.out.println("4. Today's profits.");
            System.out.println("9. Back to menu");
            choose = scanner.nextInt();

            switch (choose) {
                case 1 -> Economy.searchEcon();
                case 2 -> Economy.totalEcon();
                case 3 -> readFile();
                case 4 -> Economy.todayEcon();
                case 9 -> menu.run();
            }



        } while (!(choose == 9));
    }
}
