// Implement a simple file I/O operation to read data from a text file. 

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FileRead {
    public static void main(String[] args) {
        try {
            // Specify the file path (make sure the file exists)
            File file = new File("C:\\Users\\Manasvi\\OneDrive\\Desktop\\assign\\manasvijain_java_training\\session1\\Advance\\InterfaceAndAbstractClass.txt");

            // Create Scanner object to read the file
            Scanner sc = new Scanner(file);

            System.out.println("Contents of the file:");

            // Read the file line by line
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                System.out.println(line);
            }

            sc.close(); // Close the scanner

        } catch (FileNotFoundException e) {
            // Handle exception if file not found
            System.out.println("Error: File not found!");
        }
    }
}
