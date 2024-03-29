import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Deque;
import java.util.List;
import java.util.Scanner;
import java.util.Random;

public class Patient {
    String name;
    String color;

    public  Patient(String name, String color) {
        this.name = name;
        this.color = color;
    }

    @Override
    public String toString() {
        return name + " (" + color + ")";
    }



    public static void main(String[] args) {
        Deque<Patient> emergencyQueue = new ArrayDeque<>();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nEmergency Room Menu:");
            System.out.println("1. Add Patient");
            System.out.println("2. Treat Patient");
            System.out.println("3. Search Patient");
            System.out.println("4. Sort Queue");
            System.out.println("5. Delete Patient");
            System.out.println("6. Add Patients");
            System.out.println("7. Exit");
            
            System.out.print("Enter your choice: ");

            if (scanner.hasNextInt()) {
                int choice = scanner.nextInt();
                scanner.nextLine(); 

                switch (choice) {
                    case 1:
                        addPatient(emergencyQueue, scanner);
                        break;
                    case 2:
                        treatPatient(emergencyQueue);
                        break;
                    case 3:
                        searchPatient(emergencyQueue, scanner);
                        break;
                    case 4:
                        sortQueue(emergencyQueue);
                        break;
                    case 5:
                        deletePatient(emergencyQueue, scanner);
                        break;
                    case 6:
                    	addRandomPatients(emergencyQueue);
                    	break;
                    case 7:
                        System.out.println("Exiting the program.");
                        System.exit(0);
                    default:
                        System.out.println("Invalid choice. Try again.");
                }

                System.out.println("\nEmergency Room Queue:");
                printQueue(emergencyQueue);
            } else {
                System.out.println("Invalid input. Please enter a valid choice (1-6).");
                scanner.nextLine(); 
            }
        }
    }

    public static void addPatient(Deque<Patient> queue, Scanner scanner) {
        System.out.print("Enter patient's name: ");
        String name = scanner.nextLine().trim();

        System.out.print("Enter patient's color (red, blue, green): ");
        String color = scanner.nextLine().trim().toLowerCase();

        if (color.equals("red") || color.equals("blue") || color.equals("green")) {
            Patient patient = new Patient(name, color);

            if (color.equals("red")) {
                queue.addFirst(patient); // Red patients are pushed to the front.
            } else {
                // Handle priority for blue and green patients
                boolean added = false;
                Deque<Patient> tempQueue = new ArrayDeque<>();

                while (!queue.isEmpty()) {
                    Patient currentPatient = queue.poll();
                    if ((color.equals("blue") && currentPatient.color.equals("red"))
                            || (color.equals("green") && currentPatient.color.equals("red"))
                            || (color.equals("blue") && currentPatient.color.equals("green"))) {
                        
                        tempQueue.addLast(patient);
                        tempQueue.addLast(currentPatient);
                        added = true;
                        break;
                    } else {
                        tempQueue.addLast(currentPatient);
                    }
                }

                if (!added) {
                    // If the patient is the only one or has lower priority than existing patients
                    tempQueue.addLast(patient);
                }

                
                queue.addAll(tempQueue);
            }

            System.out.println(name + " added to the queue.");
        } else {
            System.out.println("Invalid color. Please enter red, blue, or green.");
        }
    }



    public static void treatPatient(Deque<Patient> queue) {
        if (!queue.isEmpty()) {
            Patient patient = null;

            // Iterate through the queue to find the highest priority patient
            for (Patient currentPatient : queue) {
                if (currentPatient.color.equals("red")) {
                    patient = currentPatient;
                    break; // Treat the first red patient found
                } else if (currentPatient.color.equals("blue") && (patient == null || patient.color.equals("green"))) {
                    patient = currentPatient;
                } else if (currentPatient.color.equals("green") && patient == null) {
                    patient = currentPatient;
                }
            }

            queue.remove(patient);
            System.out.println("Treating " + patient);
        } else {
            System.out.println("No patients in the queue.");
        }
    }


    public static void searchPatient(Deque<Patient> queue, Scanner scanner) {
        System.out.print("Enter patient's name to search: ");
        String searchName = scanner.nextLine().trim();

        for (Patient patient : queue) {
            if (patient.name.equalsIgnoreCase(searchName)) {
                System.out.println("Patient found: " + patient);
                return;
            }
        }

        System.out.println("Patient not found: " + searchName);
    }

    public static void sortQueue(Deque<Patient> queue) {
        List<Patient> patientList = new ArrayList<>(queue);

        
        Comparator<Patient> patientComparator = Comparator
                .comparing((Patient p) -> p.color.equals("red") ? 0 : (p.color.equals("blue") ? 1 : 2))
                .thenComparing(p -> p.name);

        
        patientList.sort(patientComparator);

       
        queue.clear();
        queue.addAll(patientList);

        System.out.println("Queue sorted.");
    }



    private static void deletePatient(Deque<Patient> queue, Scanner scanner) {
        System.out.print("Enter patient's name to delete: ");
        String deleteName = scanner.nextLine().trim();

        System.out.print("Enter patient's color to delete: ");
        String deleteColor = scanner.nextLine().trim().toLowerCase();

        queue.removeIf(patient -> patient.name.equalsIgnoreCase(deleteName) && patient.color.equalsIgnoreCase(deleteColor));

        System.out.println("Patient deleted: " + deleteName + " (" + deleteColor + ")");
    }


    public static void printQueue(Deque<Patient> queue) {
        if (queue.isEmpty()) {
            System.out.println("No patients in the queue.");
        } else {
            for (Patient patient : queue) {
                System.out.println(patient);
            }
        }
    }
    public static void addRandomPatients(Deque<Patient> queue) {
        Random random = new Random();
        int numberOfPatients = random.nextInt(20) + 1; // Random number between 1 and 20

        String[] names = {"Jason", "John", "Ronny", "Victoria", "Dave", "Justin", "Alex", "Lily", "Owen", "Victor", 
                          "Max", "Bob", "Dina", "Kevin", "Bria", "Jen", "Jenny", "Ana", "Ethan", "Roy"};

        for (int i = 0; i < numberOfPatients; i++) {
            String name = names[random.nextInt(names.length)];
            String[] colors = {"red", "blue", "green"};
            String color = colors[random.nextInt(colors.length)];
            Patient patient = new Patient(name, color);
            queue.addLast(patient);
        }

        System.out.println("Added " + numberOfPatients + " random patients to the queue.");
    }
}
