import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class FileHandling {
    String timeStartString= "";
    String name;
    String timeEndString = "";

    public void loadReservationsFromFile(ArrayList<Reservation> reservations) {

        File file = new File("Reservations.txt");
        Scanner fileScanner = null;
        try {
            fileScanner = new Scanner(file);
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                Scanner tokenizer = new Scanner(line);
                tokenizer.useDelimiter(" has a reservation from | and ends at ");
                if (tokenizer.hasNext()) {
                    name = tokenizer.next();
                }
                if (tokenizer.hasNext()) {
                     timeStartString = tokenizer.next();
                }
                if (tokenizer.hasNext()) {
                     timeEndString = tokenizer.next();
                }
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
                LocalDateTime timeStart = LocalDateTime.parse(timeStartString, formatter);
                LocalDateTime timeEnd = LocalDateTime.parse(timeEndString, formatter);

                Reservation reservation = new Reservation(name, timeStart, timeEnd, 0, false);
                reservations.add(reservation);
            }


            fileScanner.close();
        } catch (NullPointerException e) {

            e.printStackTrace();
        } catch (FileNotFoundException e) {
           e.printStackTrace();
        }
    }
}
