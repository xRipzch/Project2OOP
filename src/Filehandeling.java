import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.File;
public class Filehandeling {
    public void loadReservationFile() {
        Scanner fileScanner = null;
        File file = new File("Reservations.txt");
        while (fileScanner.hasNextLine()) {
            String data = fileScanner.nextLine();
            System.out.println("Loaded data in file");
            System.out.println(data);
        }
        fileScanner.close();
    }
}

// kan vi kører engag yessir ng ?