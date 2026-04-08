// 3)  Create a program to search for a specific element within an array using linear search.

import java.util.Scanner;
public class LinearSearch {
    
    static int linearSearch(int arr[], int n, int key) {
        for (int i = 0; i < n; i++) {  
            if (arr[i] == key) {
                return i;  // Return index if element found
            }
        }
        return -1;  // Return -1 if element not found
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter number of elements: ");
        int n = sc.nextInt(); 
        int arr[] = new int[n];

        // Input elements
        System.out.println("Enter elements:");
        for (int i = 0; i < n; i++) {
            arr[i] = sc.nextInt();
        }
        //Element to search
        System.out.print("Enter element to search: ");
        int key = sc.nextInt();

        int result = linearSearch(arr, n, key);  // Call function

        // Display result
        if (result != -1) {
            System.out.println("Element found at index: " + result);
        } else {
            System.out.println("Element not found");
        }
    }
}

