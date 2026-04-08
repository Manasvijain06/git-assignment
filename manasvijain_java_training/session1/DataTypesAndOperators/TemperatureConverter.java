// 3.  Create a program to convert a temperature from Celsius to Fahrenheit and vice versa.

import java.util.Scanner; 
public class TemperatureConverter {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);  // Create Scanner object

        System.out.println("Choose Conversion:");
        System.out.println("1 for converting Celsius to Fahrenheit");
        System.out.println("2 for converting Fahrenheit to Celsius");

        int choice = sc.nextInt();  // Take choice

        if (choice == 1) {
            System.out.print("Enter temperature in Celsius: ");
            double c = sc.nextDouble(); 

            double f = (c * 9/5) + 32;  // Convert to Fahrenheit
            System.out.println("Temperature in Fahrenheit = " + f);

        } else if (choice == 2) {
            System.out.print("Enter temperature in Fahrenheit: ");
            double f = sc.nextDouble(); 

            double c = (f - 32) * 5/9;  // Convert to Celsius
            System.out.println("Temperature in Celsius = " + c);

        } else {
            System.out.println("Invalid choice"); 
        }
    }
}
