import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

public class FileHandling {
    public void loadReservationsFromFile(ArrayList<Reservation> reservations) {
        try {
            File file = new File("Reservations.txt");
            Scanner fileScanner = new Scanner(file);

            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] parts = line.split(" ");

                if (parts.length == 5) {
                    String name = parts[0];
                    LocalDateTime timeStart = LocalDateTime.parse(parts[1]);
                    LocalDateTime timeEnd = LocalDateTime.parse(parts[2]);
                    int price = Integer.parseInt(parts[3]);
                    boolean hasPaid = Boolean.parseBoolean(parts[4]);

                    Reservation reservation = new Reservation(name, timeStart, timeEnd, price, hasPaid);
                    reservations.add(reservation);
                } else {
                    // Handle incorrect format in the file
                }
            }

            fileScanner.close();
        } catch (FileNotFoundException e) {
            // Handle file not found or other exceptions
            e.printStackTrace();
        }
    }
}
