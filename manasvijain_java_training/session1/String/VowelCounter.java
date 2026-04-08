// Implement a function to count the number of vowels in a string. 

import java.util.Scanner;

public class VowelCounter {

    // Function to count vowels in a string
    public static int countVowels(String str) {
        int count = 0;
        str = str.toLowerCase(); // Convert to lowercase for uniform checking

        // Loop through each character
        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);
            // Check if character is a vowel
            if (ch == 'a' || ch == 'e' || ch == 'i' || ch == 'o' || ch == 'u') {
                count++;
            }
        }
        return count;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Input string from user
        System.out.print("Enter a string: ");
        String input = sc.nextLine();

        // Count vowels and display
        int vowels = countVowels(input);
        System.out.println("Number of vowels: " + vowels);
    }
}