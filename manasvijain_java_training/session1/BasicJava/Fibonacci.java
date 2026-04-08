// 4)  Write a program to print the Fibonacci sequence up to a specified number.

import java.util.Scanner;
public class Fibonacci {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in); 

        System.out.print("Enter the limit: ");
        int limit = sc.nextInt();

        // First two Fibonacci numbers
        int a = 0, b = 1;

        System.out.print("Fibonacci sequence: ");

        // Print Fibonacci numbers up to the limit
        while (a <= limit) {
            System.out.print(a + " ");

            int next = a + b;  // Calculate next term
            a = b;             
            b = next;         
        }
    }
}
