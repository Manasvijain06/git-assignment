//1.  Write a program to check if a given number is prime using an if-else statement.

import java.util.Scanner;
public class CheckPrime {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter a number: ");

        int num = sc.nextInt();  // Take input

        int count = 0;  // To count factors

        // Check factors from 1 to num
        for (int i = 1; i <= num; i++) {
            if (num % i == 0) {
                count++;  // Increase count if divisible
            }
        }

        // Check if number is prime using if-else
        if (count == 2) {
            System.out.println("The number is Prime");
        } else {
            System.out.println("The number is Not Prime");
        }
    }
}

