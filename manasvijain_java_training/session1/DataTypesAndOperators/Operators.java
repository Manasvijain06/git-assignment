// 2)  Write a program to demonstrate the use of arithmetic, logical, and relational operators.

public class Operators {

    public static void main(String[] args) {
    
        int a = 2;

        int b = 3;

        // 🔹 Arithmetic Operators
        System.out.println("\nArithmetic Operations:");
        System.out.println("Addition = " + (a + b));
        System.out.println("Subtraction = " + (a - b));
        System.out.println("Multiplication = " + (a * b));
        System.out.println("Division = " + (a / b));
        System.out.println("Modulus = " + (a % b));

        // 🔹 Relational Operators
        System.out.println("\nRelational Operations:");
        System.out.println("a > b : " + (a > b));
        System.out.println("a < b : " + (a < b));
        System.out.println("a == b : " + (a == b));
        System.out.println("a != b : " + (a != b));

        // 🔹 Logical Operators
        System.out.println("\nLogical Operations:");
        System.out.println("(a > 0 && b > 0) : " + (a > 0 && b > 0));
        System.out.println("(a > 0 || b > 0) : " + (a > 0 || b > 0));
        System.out.println("!(a > 0) : " + !(a > 0));
    }
}
