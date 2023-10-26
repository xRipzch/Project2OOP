import java.util.ArrayList;

public class Economy {
    private static ArrayList<Reservation> reservations;

    public Economy(ArrayList<Reservation> reservations) {
        Economy.reservations = reservations;
    }

    public void printEconMenu() {
        Users users = new Users("User");
        users.logIn();
    }

    public static void totalEcon() {
        int total = 0;
        for (Reservation reservationTotal : reservations) {
            if (reservationTotal.getHasPaid()) {
                total += reservationTotal.getPrice();
                System.out.println("You have made a total of " + total + "$");
            }
        }
    }
}