import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Scanner;

public class FileHandling {

    private static final String FILE_NAME = "Reservations.txt";
    private static final String MALE = "m";
    private static final int MALE_PRICE = 250;
    private static final String FEMALE = "f";
    private static final int FEMALE_PRICE = 350;

    public void loadReservationsFromFile(ArrayList<Reservation> reservations) {

        File file = new File(FILE_NAME);

        try {
            Scanner fileScanner = new Scanner(file);
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] tokens = line.split(",");

                String name = tokens[0];

                LocalDate startDate = LocalDate.of(Integer.parseInt(tokens[1]), Integer.parseInt(tokens[2]), Integer.parseInt(tokens[3]));
                LocalTime startTime = LocalTime.of(Integer.parseInt(tokens[4]), Integer.parseInt(tokens[5]));
                LocalDateTime timeStart = LocalDateTime.of(startDate, startTime);

                LocalDate endDate = LocalDate.of(Integer.parseInt(tokens[6]), Integer.parseInt(tokens[7]), Integer.parseInt(tokens[8]));
                LocalTime endTime = LocalTime.of(Integer.parseInt(tokens[9]), Integer.parseInt(tokens[10]));
                LocalDateTime timeEnd = LocalDateTime.of(endDate, endTime);

                int price = Integer.parseInt(tokens[11]);
                boolean hasPaid = Boolean.parseBoolean(tokens[12]);
                String gender = tokens[13];

                Reservation reservation = new Reservation(name, timeStart, timeEnd, price, hasPaid, gender);
                reservations.add(reservation);
            }

            fileScanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            System.out.println("Error in file format.");
            e.printStackTrace();
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Incomplete or corrupted line in file.");
            e.printStackTrace();
        }
    }
}