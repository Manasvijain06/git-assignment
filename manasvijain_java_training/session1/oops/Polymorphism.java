// Demonstrate polymorphism by creating methods with the same name but different parameters in a parent and child class.

// Parent class
class Calculator {
    // Method with one parameter
    void calculate(int a,int b) {
        System.out.println("Sum: " + (a + b));
    }
}

// Child class
class AdvancedCalculator extends Calculator {
    // Method with two parameters (same name, different parameters)
    void calculate(double a, double b) {
        System.out.println("Product: " + (a * b ));
    }
}

// Main class
public class Polymorphism {
    public static void main(String[] args) {
        // Parent object
        Calculator c1 = new Calculator();
        c1.calculate(5, 10);  // Calls parent method

        // Child object
        AdvancedCalculator a1 = new AdvancedCalculator();
        a1.calculate(5.5, 10); // Calls overloaded method
    }
}