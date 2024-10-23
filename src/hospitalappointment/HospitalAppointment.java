package hospitalappointment;

import java.util.InputMismatchException;
import java.util.Scanner;

public class HospitalAppointment {
    Scanner scan = new Scanner(System.in);
    Config conf = new Config();
    
    public void appointmentConfig(){
        int option;
        do {
            try {
                 System.out.println("\n\t--------------------------------------------------------\n");
                        System.out.println("\n\t--- Appointments Menu ---\n");
                        System.out.println("\t1. Schedule Appointment");
                        System.out.println("\t2. View Appointments");
                        System.out.println("\t3. Edit Appointment");
                        System.out.println("\t4. Delete Appointment");
                        System.out.println("\t5. Exit");
                 System.out.println("\n\t--------------------------------------------------------\n");
                System.out.print("\nChoose an option: ");
                option = scan.nextInt();
                scan.nextLine(); 
  System.out.println("\n\t--------------------------------------------------------\n");
                switch (option) {
                    case 1:
                        System.out.println("\n   --- ADDING NEW APPOINTMENT ---\n");
                        scheduleAppointment();
                        break;
                    case 2:
                        System.out.println("\n\t\t\t\t\t\t\t\t   --- APPOINTMENTS LIST ---");
                        String query = "SELECT * FROM appointments";
                        viewAppointments(query);
                        break;
                    case 3:
                        System.out.println("\n   --- EDITING AN APPOINTMENT ---\n");
                        editAppointment();
                        break;
                    case 4:
                        System.out.println("\n   --- DELETING AN APPOINTMENT ---\n");
                        deleteAppointment();
                        break;
                    case 5:
                        System.out.println("Exiting Appointments Menu..");
                        break;
                    default:
                        System.out.println("Invalid option.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                scan.nextLine();
                option = -1;
            }
        } while (option != 5);
    }

    public void scheduleAppointment() {
        System.out.println("Enter Appointment Details:");
        
        int p_id;
        do{
            System.out.print("\nPatient ID: ");
            p_id = scan.nextInt();
            if(!conf.doesIDExist("patients", p_id)){
                System.out.println("Patient ID Doesn't Exist.");
            }
        }while(!conf.doesIDExist("patients", p_id));
        scan.nextLine();
        
        System.out.print("Appointment Type: ");
        String type = scan.nextLine();
        
        System.out.print("Doctor Name: ");
        String doc = scan.nextLine();
        
        System.out.print("Appointment Date: ");
        String date = scan.nextLine();
        
        System.out.print("Appointment Status: ");
        String stats = scan.nextLine();
        
        String sql = "INSERT INTO appointments (patient_id, a_type, p_doctor, a_date, a_status) VALUES (?, ?, ?, ?, ?)";       
        conf.addRecord(sql, p_id, type, doc, date, stats);
    }

    public void viewAppointments(String query) {
         
        String[] Headers = {"ID", "Patient ID", "Appointment Type", "Doctor", "Appointment Date", "Appointment Status"};
        String[] Columns = {"id", "patient_id", "a_type", "p_doctor", "a_date", "a_status"};
        
        conf.viewRecords(query, Headers, Columns);
    }

   public void editAppointment() {
    int id;        
    boolean idExists;
    
    // Prompt for the appointment ID to edit
    do {
        System.out.print("Appointment ID you want to edit: ");
        id = scan.nextInt();
        
        // Check for existence of the appointment ID
        idExists = conf.doesIDExist("appointments", id); // Corrected table name to "appointments"
        if (!idExists) {
            System.out.println("Appointment ID Doesn't Exist.\n");
        }
    } while (!idExists);
    
    // Query to view current appointment details
    String query = "SELECT * FROM appointments WHERE id = " + id;
    viewAppointments(query);
    
    System.out.println("Enter New Appointment Details:");
    
    int p_id;
    do {
        System.out.print("\nNew Patient ID: ");
        p_id = scan.nextInt();
        // Check if the new patient ID exists
        if (!conf.doesIDExist("patients", p_id)) {
            System.out.println("Patient ID Doesn't Exist.");
        }
    } while (!conf.doesIDExist("patients", p_id));
    scan.nextLine();
    
    System.out.print("New Appointment Type: ");
    String type = scan.nextLine();
    
    System.out.print("New Doctor Name: ");
    String doc = scan.nextLine();
    
    System.out.print("New Appointment Date: ");
    String date = scan.nextLine();
    
    System.out.print("New Appointment Status: ");
    String stats = scan.nextLine();
    
    // Ensure correct SQL syntax by removing the extra comma
    String sql = "UPDATE appointments SET patient_id = ?, a_type = ?, p_doctor = ?, a_date = ?, a_status = ? WHERE id = ?";       
    conf.updateRecord(sql, p_id, type, doc, date, stats, id); 
}


    public void deleteAppointment() {
        System.out.print("Enter ID you want to delete: ");
        int id = scan.nextInt();
        
        String sql = "DELETE FROM appointments WHERE id = ?";
        conf.deleteRecord(sql, id);
    }
}