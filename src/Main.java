import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        ArrayList<Reservation> reservations = new ArrayList<>();
        Menu menu = new Menu(reservations);
        Economy economy = new Economy(reservations);
        menu.run();
    }
}