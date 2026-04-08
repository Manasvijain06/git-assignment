// Create a program to handle exceptions using try-catch blocks.

import java.util.Scanner;
public class TryCatchBlock {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
         System.out.print("Enter numerator: ");
            int numerator = sc.nextInt();

            System.out.print("Enter denominator: ");
            int denominator = sc.nextInt();

        try {
            // Input two numbers
           
            // Division (may cause ArithmeticException)
            int result = numerator / denominator;
            System.out.println("Result: " + result);

        } catch (ArithmeticException e) {
            // Handles division by zero
            System.out.println("Error: Cannot divide by zero!");

        } finally {
            // This block executes always
            System.out.println("Program execution completed.");
        }
    }
}

