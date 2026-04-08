//1)  Write a program to calculate the area of a circle, rectangle, or triangle based on user input.
import java.util.Scanner;

public class AreaCalculator {

    // Method for Circle
    public static double calculateCircle(double r) {
        return Math.PI * r * r;
    }

    // Method for Rectangle
    public static double calculateRectangle(double l, double b) {
        return l * b;
    }

    // Method for Triangle
    public static double calculateTriangle(double h, double base) {
        return 0.5 * h * base;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Choose shape:");
        System.out.println("1. Circle");
        System.out.println("2. Rectangle");
        System.out.println("3. Triangle");

        int choice = sc.nextInt();

        switch (choice) {
            case 1:
                System.out.print("Enter radius: ");
                double r = sc.nextDouble();
                System.out.println("Area of Circle = " + calculateCircle(r));
                break;

            case 2:
                System.out.print("Enter length: ");
                double l = sc.nextDouble();
                System.out.print("Enter breadth: ");
                double b = sc.nextDouble();
                System.out.println("Area of Rectangle = " + calculateRectangle(l, b));
                break;

            case 3:
                System.out.print("Enter height: ");
                double h = sc.nextDouble();
                System.out.print("Enter base: ");
                double base = sc.nextDouble();
                System.out.println("Area of Triangle = " + calculateTriangle(h, base));
                break;

            default:
                System.out.println("Invalid choice!");
        }

    }
}