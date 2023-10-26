import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

public class Menu {
    private ArrayList<Reservation> reservations;
    private Scanner scanner = new Scanner(System.in);

    public Menu(ArrayList<Reservation> reservations) {
        this.reservations = reservations;
    }

    public void addReservation() {
        int price = 0;
        LocalDateTime timeEnd = null;
        boolean isAldreaybooked = false;
        Scanner scanner = new Scanner(System.in);
        System.out.println("What's the name of the person getting a haircut?");
        String name = scanner.nextLine();
        System.out.println("What day of the month?");
        int day = scanner.nextInt();
        System.out.println("What month (1-12)?");
        int month = scanner.nextInt();
        System.out.println("What year?");
        int year = scanner.nextInt();
        System.out.println("What hour of the day?");
        int hour = scanner.nextInt();
        System.out.println("What minute?");
        int minute = scanner.nextInt();
        scanner.nextLine();//scanner bug
        LocalDateTime time = LocalDateTime.of(year, month, day, hour, minute);

        System.out.println("Is it a male (M) or Female (F)");
        String mF = scanner.nextLine();
        if (mF.equalsIgnoreCase("m")) {
            price = 250;
            timeEnd = time.plusMinutes(30);
        } else if (mF.equalsIgnoreCase("f")) {
            price = 350;
            timeEnd = time.plusHours(1);
        } else {
            System.out.println("invalid gender, try again");
            addReservation();
        }

        if(!isSlotAvailable(time, timeEnd)){
            isAldreaybooked = true;
        }

       if (!isAldreaybooked) {
            Reservation reservation = new Reservation(name, time, timeEnd, price, false);
            int index = 0;
            while (index < reservations.size() && reservation.getTimeStart().isAfter(reservations.get(index).getTimeStart())) {
                index++;
            }
            reservations.add(index, reservation);
            saveReservationsToFile();


            System.out.println("Reservation added successfully.");
        } else {
            System.out.println("The choosen time is not in opening hours, try again");
            addReservation();
        }
    }

    public void deleteReservation() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the name of the person you want to delete (or type 'QUIT')");
        String nameDelete = scanner.nextLine();
        if (nameDelete.equalsIgnoreCase("quit")) run();

        boolean reservationFound = false;
        for (Reservation reservation : reservations) {
            if (reservation.getName().equalsIgnoreCase(nameDelete)) {

                reservationFound = true;
                System.out.println("Reservation for " + nameDelete + " found successfully.");
                System.out.println(reservation);
                System.out.println("Are you sure you want to delete reservation? (Y/N)");
                String deleteYesNo = scanner.nextLine();
                if (deleteYesNo.equalsIgnoreCase("y")) {
                    reservations.remove(reservation);
                    System.out.println("Deletion successful");
                    run();
                } else if (deleteYesNo.equalsIgnoreCase("n")) {
                    System.out.println("Deletion stopped, back to menu");
                    run();
                } else System.out.println("invalid option, try again");
                deleteReservation();
            }
        }
        if (!reservationFound) {
            System.out.println("Reservation for " + nameDelete + " not found.");
        }
    }

    public void testSøgning() {
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
                System.out.println(reservation);
                foundReservations = true;
            }
        }
        if (!foundReservations) System.out.println("Nothing found on " + timeStart.toLocalDate());
    }

    public void ledigeTider() {
        System.out.println("What day of the month?");
        int day = scanner.nextInt();
        System.out.println("What month (1-12)?");
        int month = scanner.nextInt();
        System.out.println("What year?");
        int year = scanner.nextInt();
        scanner.nextLine(); // Scannerbug

        System.out.println("Is it male or female haircut? (M/F)");
        String mF = scanner.nextLine();

        int slotDurationMinutes;

        if (mF.equalsIgnoreCase("m")) {
            slotDurationMinutes = 30;
        } else if (mF.equalsIgnoreCase("f")) {
            slotDurationMinutes = 60;
        } else {
            System.out.println("Invalid gender input. Please enter 'M' for male or 'F' for female.");
            return;
        }

        LocalDateTime startOfDayPlusDuration = LocalDateTime.of(year, month, day, 10, 0);
        LocalDateTime endOfDay = LocalDateTime.of(year, month, day, 18, 0);

        while (startOfDayPlusDuration.isBefore(endOfDay)) {
            LocalDateTime endOfSlot = startOfDayPlusDuration.plusMinutes(slotDurationMinutes);
            if (isSlotAvailable(startOfDayPlusDuration, endOfSlot)) {
                System.out.println("From " + startOfDayPlusDuration.toLocalTime() + " to " + endOfSlot.toLocalTime());
            }
            startOfDayPlusDuration = endOfSlot;
        }
    }

    private boolean isSlotAvailable(LocalDateTime start, LocalDateTime end) {
        if (start.getHour() >= 10 && end.getHour() <= 18 && end.isAfter(start)) {
            for (Reservation reservation : reservations) {
                if (!(end.isBefore(reservation.getTimeStart()) || start.isAfter(reservation.getTimeEnd()))) {
                    return false;
                }
            }
            return true;
        } else {
            System.out.println("Outside of opening hours");
            return false;
        }
    }

    private void checkOut() {

        System.out.println("Whats the name of the person you are checking out?");
        String name = scanner.nextLine();
        for (Reservation reservation : reservations) {
            if (name.equals(reservation.getName())) {
                System.out.println(reservation.getName() + " has a reservation starting at " + reservation.getTimeStart() + " TOTAL: " + reservation.getPrice());
                System.out.println("You have the following options" +
                        "\n1. Comfirm payment" +
                        "\n2. Add product to pris");
                int choose = scanner.nextInt();
                switch (choose) {
                    case 1 -> reservation.setHasPaid(true);
                    case 2 -> {
                        System.out.println("What product do you want to add?" +
                                "\n1. Shampoo - 65$" +
                                "\n2. Gel - 30$");
                        int product = scanner.nextInt();
                        switch (product) {
                            case 1 -> {
                                System.out.println("65$ added");
                                reservation.setPrice(reservation.getPrice() + 65);
                                System.out.println("New TOTAL: " + reservation.getPrice());
                                run();
                            }
                            case 2 -> {
                                System.out.println("65$ added");
                                reservation.setPrice(reservation.getPrice() + 30);
                                System.out.println("New TOTAL: " + reservation.getPrice());
                                run();
                            }
                        }
                    }
                }
            }

        }
    }

    public void seeAllReservations() {
        for (Reservation reservation : reservations) {
            System.out.println(reservation);
        }
    }
        public void printMenu () {
            System.out.println("What do you wish to do?" +
                    "\n1. Add reservation" +
                    "\n2. Delete reservation" +
                    "\n3. See all reservations" +
                    "\n9. Quit");

        }
        public void saveReservationsToFile () {
            try {
                PrintStream ps = new PrintStream(new FileOutputStream("Reservations.txt"), true);
                for (Reservation fileReservation : reservations) {
                    ps.println(fileReservation);
                    //todo ændre til .get name mm. istedet for at have tostring metode under reservation
                }
                ps.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }
    public void run() {
        Economy economy = new Economy(reservations);
        boolean running = true;
        while (running) {
            printMenu();

            int choose = scanner.nextInt();
            scanner.nextLine();

            switch (choose) {
                case 1 -> addReservation();
                case 2 -> deleteReservation();
                case 3 -> seeAllReservations();
                case 4 -> testSøgning();
                case 5 -> ledigeTider();
                case 6 -> checkOut();
                case 7 -> economy.printEconMenu();
                case 9 -> running = false;
            }
        }
    }
}
