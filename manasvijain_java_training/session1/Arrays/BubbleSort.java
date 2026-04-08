// 2)  Implement a function to sort an array in ascending order using bubble sort or selection sort.
import java.util.Scanner; 

public class BubbleSort {
   // Function to perform Bubble Sort
    static void bubbleSort(int arr[], int n) {
        for (int i = 0; i < n - 1; i++) {          
            for (int j = 0; j < n - i - 1; j++) {   

                // Compare adjacent elements
                if (arr[j] > arr[j + 1]) {
                    // Swap elements
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);  

        System.out.print("Enter number of elements: ");
        int n = sc.nextInt();  // Array size

        int arr[] = new int[n];  // Declare array

        // Input elements
        System.out.println("Enter elements:");
        for (int i = 0; i < n; i++) {
            arr[i] = sc.nextInt();
        }

        bubbleSort(arr, n);  // Call sorting function

        // Display sorted array
        System.out.println("Sorted array in ascending order:");
        for (int i = 0; i < n; i++) {
            System.out.print(arr[i] + " ");
        }
    }
}

