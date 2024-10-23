package hospitalappointment;

import java.util.InputMismatchException;
import java.util.Scanner;

public class PatientAppointment {
    static Config conf = new Config();

    public static void main(String[] args) { 
        Scanner scan = new Scanner(System.in);
        Patient patient = new Patient();
      HospitalAppointment appointments = new HospitalAppointment();
        
        int choice;

        do {    
            try {
                 System.out.println("\n--------------------------------------------------------\n");
                    System.out.println("\n   + Appointment Records System +\n");
                    System.out.println("\t1. Patients");
                    System.out.println("\t2. Appointments");
                    System.out.println("\t3. View APointment Reports");
                    System.out.println("\t4. Exit");
                 System.out.println("\n--------------------------------------------------------\n");
                System.out.print("\nEnter Option: ");
                choice = scan.nextInt();
                scan.nextLine();
                 System.out.println("\n--------------------------------------------------------\n");
                switch (choice) {
                    case 1:  
                        patient.patientConfig();
                        break;
                    case 2:
                        appointments.appointmentConfig();
                        break;
                    case 3:
                        System.out.println("\n\t\t\t\t\t\t\t\t   --- APPOINTMENTS HISTORY ---");
                        
                        String sql = "SELECT appointments.id, patients.name AS patient_name, appointments.a_type, appointments.p_doctor, appointments.a_date, appointments.a_status " +
                                     "FROM appointments " +
                                     "JOIN patients ON appointments.patient_id = patients.id;";
                        
                        String[] headers = {"ID", "Patient Name", "Appointment Type", "Doctor", "Appointment Date", "Appointment Status"};
                        String[] columns = {"id", "patient_name", "a_type", "p_doctor", "a_date", "a_status"};

                        conf.viewRecords(sql, headers, columns);
                        break;
                    case 4:                      
                        System.out.println("Exiting...");
                        break;
                    default:
                        System.out.println("Invalid Option.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                scan.nextLine(); 
                choice = -1; 
            }
        } while (choice != 4);
        
        scan.close(); 
    }
}
