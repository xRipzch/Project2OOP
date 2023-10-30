import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class Menu {
    ArrayList<Reservation> reservations;
    Economy economy = new Economy(reservations);
    private final Scanner scanner = new Scanner(System.in);
    DateTimeFormatter dateFormatter;

    public Menu(ArrayList<Reservation> reservations) {
        this.reservations = reservations;
    }

    public void addReservation() {
        int price = 0;
        LocalDateTime timeEnd = null;
        boolean isAlreadybooked = false;
        Scanner scanner = new Scanner(System.in);
        System.out.println("What's the name of the person getting a haircut?");
        String name = scanner.nextLine().toLowerCase();
        System.out.println("What day would you like to get a haircut? [dd/MM/yyyy]");
        String dateInput = scanner.nextLine();
        dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate date = LocalDate.parse(dateInput, dateFormatter);

        System.out.println("What time would you like? [HH:mm]");
        String timeInput = scanner.nextLine();
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime timeForCut = LocalTime.parse(timeInput, timeFormatter);
        LocalDateTime dateTime = LocalDateTime.of(date, timeForCut);
        System.out.println("Is it a male (M) or Female (F)");
        String mF = scanner.nextLine();
        if (mF.equalsIgnoreCase("m")) {
            price = 250;
            timeEnd = dateTime.plusMinutes(30);
        } else if (mF.equalsIgnoreCase("f")) {
            price = 350;
            timeEnd = (dateTime.plusHours(1));
        } else {
            System.out.println("invalid gender, try again");
            addReservation();
        }

        if (!isSlotAvailable(dateTime, timeEnd)) {
            isAlreadybooked = true;
        }

        if (!isAlreadybooked) {
            Reservation reservation = new Reservation(name, dateTime, timeEnd, price, false, mF);
            int index = 0;
            while (index < reservations.size() && reservation.getTimeStart().isAfter(reservations.get(index).getTimeStart())) {
                index++;
            }
            reservations.add(index, reservation);
            saveReservationsToFile();


            System.out.println("Reservation added successfully.");
        } else {
            System.out.println("Time unavailable, try again");
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
                    saveReservationsToFile();
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


    public void ledigeTider() {
       scanner.nextLine(); // scanner bug
        System.out.println("What day would you like to get a haircut? [dd/MM/yyyy]");
        String dateInput = scanner.nextLine();
         dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate date = LocalDate.parse(dateInput, dateFormatter);
        String timeInput = "00:00";
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime timeForCut = LocalTime.parse(timeInput, timeFormatter);
        LocalDateTime dateTime = LocalDateTime.of(date, timeForCut);
        int year = dateTime.getYear();
        int month = dateTime.getMonthValue();
        int day = dateTime.getDayOfMonth();

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
            startOfDayPlusDuration = startOfDayPlusDuration.plusMinutes(slotDurationMinutes);
        }
    }

    private boolean isSlotAvailable(LocalDateTime start, LocalDateTime end) {
        if (start.getHour() >= 10 && (end.getHour() < 18 || (end.getHour() == 18 && end.getMinute() == 0)) && end.isAfter(start)) {
            for (Reservation reservation : reservations) {
                if (start.isBefore(reservation.getTimeEnd()) && end.isAfter(reservation.getTimeStart())) {
                    return false;
                }
            }
            return true;
        } else {
            return false;
        }
    }


    private void checkOut() {
        Economy economy = new Economy(reservations);
        boolean checkoutComplete = false;

        while (!checkoutComplete) {
            System.out.println("What's the name of the person you are checking out (or type 'CANCEL')?");
            scanner.nextLine();
            String name = scanner.nextLine().toLowerCase();

            if (name.equalsIgnoreCase("CANCEL")) {
                run();
            } else {
                for (Reservation reservation : reservations) {
                    if (name.equals(reservation.getName())) {
                        System.out.println(reservation.getName() + " has a reservation starting at " + reservation.getTimeStart() + " TOTAL: " + reservation.getPrice());
                        System.out.println("You have the following options" +
                                "\n1. Confirm payment" +
                                "\n2. Add product");
                        int choose = scanner.nextInt();

                        if (choose == 1) {
                            System.out.println("Payment confirmed");
                            reservation.setHasPaid(true);
                            economy.writeToEconFile();
                            saveReservationsToFile();

                            checkoutComplete = true;
                        } else if (choose == 2) {
                            boolean productAdded = false;
                            while (!productAdded) {
                                System.out.println("What product do you want to add?" +
                                        "\n1. Shampoo - 65$" +
                                        "\n2. Gel - 30$");
                                int product = scanner.nextInt();
                                if (product == 1) {
                                    System.out.println("65$ added");
                                    reservation.setPrice(reservation.getPrice() + 65);
                                    System.out.println("New TOTAL: " + reservation.getPrice());
                                    reservation.setHasPaid(true);
                                    economy.writeToEconFile();
                                    productAdded = true;
                                    saveReservationsToFile();
                                } else if (product == 2) {
                                    System.out.println("30$ added");
                                    reservation.setPrice(reservation.getPrice() + 30);
                                    System.out.println("New TOTAL: " + reservation.getPrice());
                                    reservation.setHasPaid(true);
                                    economy.writeToEconFile();
                                    productAdded = true;
                                    saveReservationsToFile();
                                }
                            }
                            checkoutComplete = true;
                        }
                    }
                }

                if (!checkoutComplete) {
                    System.out.println("Reservation not found. Please try again.");
                }
            }
        }
    }

    public void seeAllReservations() {
        for (Reservation reservation : reservations) {
            System.out.println("Reservation for :"+reservation.getName()+" - from "+reservation.getTimeStart().getHour()+":"+reservation.getTimeStart().getMinute()+ " to "+reservation.getTimeEnd().getHour()+":"+reservation.getTimeEnd().getMinute());
            System.out.println(reservation.getTimeStart().getDayOfMonth()+"-"+reservation.getTimeStart().getMonthValue()+"-"+reservation.getTimeStart().getYear());
            if(reservation.getHasPaid()){
                System.out.println("The pris is "+reservation.getPrice()+"$ and has been paid!");
            }else System.out.println("The pris is "+reservation.getPrice()+"$ and has not been paid!");
        }
    }

    public void printMenu() {
        System.out.println("What do you wish to do?" +
                "\n1. Add reservation" +
                "\n2. Delete reservation" +
                "\n3. See all reservations" +
                "\n4. See available slots" +
                "\n5. Check out" +
                "\n6. Economy [Password Protected]" +
                "\n9. Quit");

    }

    public void saveReservationsToFile() {
        try {
            PrintStream ps = new PrintStream(new FileOutputStream("Reservations.txt"), true);
            for (Reservation fileReservation : reservations) {
                ps.println(fileReservation);


            }
            ps.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void loadFiles() {
        FileHandling fileHandling = new FileHandling();
        fileHandling.loadReservationsFromFile(reservations);
    }


    public void run() {
        loadFiles();
        boolean running = true;
        while (running) {
            printMenu();
            int choose = scanner.nextInt();

            switch (choose) {
                case 1 -> addReservation();
                case 2 -> deleteReservation();
                case 3 -> seeAllReservations();
                case 4 -> ledigeTider();
                case 5 -> checkOut();
                case 6 -> economy.printEconMenu();
                case 9 -> System.exit(0);
            }
        }
    }
}
