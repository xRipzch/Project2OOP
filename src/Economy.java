import java.time.LocalDateTime;
import java.util.ArrayList;
import java.io.PrintStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Scanner;


public class Economy {
    private ArrayList<Reservation> reservations;
    private Users users;

    public Economy(ArrayList<Reservation> reservations) {
        this.reservations = reservations;
        this.users = new Users("User", this);
    }

    public void todayEcon() {
        int total = 0;
        LocalDateTime timeStart = LocalDateTime.now();

        Boolean foundReservations = false;
        for (Reservation reservation : reservations) {
            if (reservation.getTimeStart().toLocalDate().isEqual(timeStart.toLocalDate())) {
                if (reservation.getHasPaid()) {
                    total = total + reservation.getPrice();
                }
                foundReservations = true;
            }
        }
        if (foundReservations) System.out.println("You have made " + total + "$ today!");
        if (!foundReservations) System.out.println("Nothing found on " + timeStart.toLocalDate());

    }

    public void printEconMenu() {
        users.logIn();
    }
    public void searchEcon(){
        Scanner scanner = new Scanner(System.in);
        int total = 0;
        System.out.println("What day of the month?");
        int day = scanner.nextInt();
        System.out.println("What month (1-12)?");
        int month = scanner.nextInt();
        System.out.println("What year?");
        int year = scanner.nextInt();
        int hour = 0;
        int minute = 0;

        LocalDateTime timeStart = LocalDateTime.of(year, month, day, hour, minute);
        Boolean foundReservations = false;
        for (Reservation reservation : reservations) {
            if (reservation.getTimeStart().toLocalDate().isEqual(timeStart.toLocalDate())) {
                if (reservation.getHasPaid()) {
                    total = total + reservation.getPrice();
                }
                foundReservations = true;
            }
        }
        if (foundReservations)System.out.println("You have made " + total + "$ on the "+timeStart.toLocalDate());
        if (!foundReservations) System.out.println("Nothing found on " + timeStart.toLocalDate());


    }
    public void writeToEconFile() {

        int total = 0;
        for (Reservation reservationTotal : reservations) {
            if (reservationTotal.getHasPaid()) {
                total = total + reservationTotal.getPrice();
            }
        PrintStream ps = new PrintStream(System.out);
        try {
            ps = new PrintStream(new FileOutputStream("economy.txt"), true);
            for (int i = 0; i < reservations.size(); i++) {
                if(reservations.get(i).getHasPaid())
                    ps.println(reservations.get(i).getName() + " has paid: " + reservations.get(i).getPrice() + " on the " + reservations.get(i).getTimeEnd());
            }

            ps.println("Total economy: " + total + "$");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } ps.close();
        }
        System.out.println("You have made a total of " + total + "$");
    }


}