//2)  Create a program to check if a number is even or odd.

import java.util.Scanner;
public class EvenOddChecker {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter a number: ");
        int num = sc.nextInt();

        // Check if number is divisible by 2
        if (num % 2 == 0) {
            System.out.println(num + " is even."); // Run if number is even
        } else {
            System.out.println(num + " is odd."); // Run if number is odd
        }
    
    }
}
