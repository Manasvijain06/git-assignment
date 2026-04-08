// 1)  Write a program to find the average of elements in an array.

import java.util.Scanner;
public class ArrayAvg {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in); 

        System.out.print("Enter number of elements: ");
        int n = sc.nextInt(); 

        int arr[] = new int[n];  // Declare array
        int sum = 0;             // Variable to store sum

        // Input elements
        System.out.println("Enter elements:");
        for (int i = 0; i < n; i++) {
            arr[i] = sc.nextInt();  // Store elements
            sum = sum + arr[i];     // Add to sum
        }

        double average = (double) sum / n;  // Calculate average

        System.out.println("Average = " + average); 
    }
}
