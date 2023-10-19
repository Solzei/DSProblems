package problem1;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Scanner;

public class EmergencyRoomSim {
	String name;
	String color;

	public Patient(String name, String color){
		this.name = name;
		this.color = color;
	}
	public static void main(String[] args) {
		Deque<Patient> emergencyQueue = new ArrayDeque<>();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Emergency Room Menu:");
            System.out.println("1. Add Patient");
            System.out.println("2. Treat Patient");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    addPatient(emergencyQueue, scanner);
                    break;
                case 2:
                    treatPatient(emergencyQueue);
                    break;
                case 3:
                    System.out.println("Exiting the program.");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    
	}

	public static void addPatient(Deque<Patient> queue, Scanner scanner) {
        System.out.print("Enter patient's name: ");
        String name = scanner.next();

        System.out.print("Enter patient's color (red, blue, green): ");
        String color = scanner.next();

        Patient patient = new Patient(name, color);

        if (color.equalsIgnoreCase("red")) {
            queue.addFirst(patient); // Red patients are pushed to the front.
        } else {
            queue.addLast(patient); // Blue and green patients at the back.
        }

        System.out.println(name + " added to the queue.");
    }
	public static void treatPatient(Deque<Patient> queue) {
        if (!queue.isEmpty()) {
            Patient patient = queue.poll();
            System.out.println("Treating " + patient.name + " with color " + patient.color);
        } else {
            System.out.println("No patients in the queue.");
        }
    }
}
