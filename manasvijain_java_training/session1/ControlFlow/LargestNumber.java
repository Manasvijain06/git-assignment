// 2)  Implement a program to find the largest number among three given numbers using a conditional statement.

import java.util.Scanner; 
public class LargestNumber {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in); 

        System.out.print("Enter first number: ");
        int a = sc.nextInt();  // Input first number

        System.out.print("Enter second number: ");
        int b = sc.nextInt();  // Input second number

        System.out.print("Enter third number: ");
        int c = sc.nextInt();  // Input third number

        // Check which number is largest using conditional statements
        if (a >= b && a >= c) {
            System.out.println("Largest number is: " + a);
        } else if (b >= a && b >= c) {
            System.out.println("Largest number is: " + b);
        } else {
            System.out.println("Largest number is: " + c);
        }
    }
}

