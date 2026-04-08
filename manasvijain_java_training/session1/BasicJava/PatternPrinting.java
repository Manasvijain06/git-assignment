//5)  Use loops to print patterns like a triangle or square

import java.util.Scanner;
public class PatternPrinting {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);  

        System.out.println("Choose Pattern: 1 for Triangle, 2 for Square");
        
        int choice = sc.nextInt();  // Take user choice

        System.out.print("Enter size: ");
        int n = sc.nextInt();
        
// Check user choice and print corresponding pattern
        if (choice == 1) {

            System.out.println("Triangle Pattern:");
            
            for (int i = 1; i <= n; i++) {        
                for (int j = 1; j <= i; j++) {    
                    System.out.print("* ");
                }
                System.out.println();  // Next line
            }

        } else if (choice == 2) {
    
            System.out.println("Square Pattern:");
            
            for (int i = 1; i <= n; i++) {        
                for (int j = 1; j <= n; j++) {    
                    System.out.print("* ");
                }
                System.out.println();  // Next line
            }

        } else {
            System.out.println("Invalid choice");  // If wrong input
        }
    }
}

