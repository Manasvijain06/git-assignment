// 4.  Create a program to calculate the sum of even numbers from 1 to 10 using a while loop.

public class SumEvenNumber {
    
    public static void main(String[] args) {

        int i = 1;     
        int sum = 0;    // Variable to store sum

        // Loop from 1 to 10
        while (i <= 10) {

            // Check if number is even
            if (i % 2 == 0) {
                sum = sum + i; 
            }

            i++;
        }

        System.out.println("Sum of even numbers from 1 to 10 = " + sum);
    }
}