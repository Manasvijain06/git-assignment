// Create a class to represent a student with attributes like name, roll number, and marks. Implement inheritance to create a "GraduateStudent" class that extends the "Student" class with additional features.

// Base class
class Student {
    String name;
    int rollNumber;
    double marks;

    // Constructor to initialize values
    Student(String name, int rollNumber, double marks) {
        this.name = name;
        this.rollNumber = rollNumber;
        this.marks = marks;
    }

    // Method to display student details
    void display() {
        System.out.println("Name: " + name);
        System.out.println("Roll Number: " + rollNumber);
        System.out.println("Marks: " + marks);
    }
}

// Derived class
class GraduateStudent extends Student {
    String specialization;  // Additional feature

    // Constructor
    GraduateStudent(String name, int rollNumber, double marks, String specialization) {
        super(name, rollNumber, marks);  // Call parent constructor
        this.specialization = specialization;
    }

    // Method to display graduate student details
    void displayGraduate() {
        display();  // Call parent method
        System.out.println("Specialization: " + specialization);
    }
}

// Main class
public class Inheritance {
    public static void main(String[] args) {

        // Create object of GraduateStudent
        GraduateStudent g1 = new GraduateStudent("Manasvi", 101, 85.5, "Computer Science");

        // Display details
        g1.displayGraduate();
    }
}

    
