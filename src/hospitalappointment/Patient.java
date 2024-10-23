package hospitalappointment;

import java.util.Scanner;

public class Patient {
    Scanner scan = new Scanner(System.in);
    Config conf = new Config();
    
    public void patientConfig() {
        int option;
        do {
            System.out.println("\n\t--- Patients Menu ---\t");
            System.out.println("1. Add Patient");
            System.out.println("2. View Patients");
            System.out.println("3. Edit Patient");
            System.out.println("4. Delete Patient");
            System.out.println("5. Exit");
            
            System.out.print("\nChoose an option: ");
            option = scan.nextInt();
            scan.nextLine(); 

            switch (option) {
                case 1:
                    System.out.println("\n   --- ADDING NEW PATIENT ---\n");
                    addPatient();
                    break;
                case 2:
                    System.out.println("\n\t\t\t      --- PATIENTS LIST ---");
                    viewPatients(); 
                    break;
                case 3:
                    editPatient();
                    break;
                case 4:
                    deletePatient();
                    break;
                case 5:
                    System.out.println("Exiting Patients Menu.");
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        } while (option != 5);
    }

    private void addPatient() {
        System.out.println("Enter Appointment Details:");
        
        System.out.print("\nPatient Name: ");
        String name = scan.nextLine();
        
        System.out.print("Email: ");
        String email = scan.nextLine();
        
         
        System.out.print("status: ");
        String status = scan.next();
        
        
        String sql = "INSERT INTO patients (p_name, p_email,p_status) VALUES (?, ?,?)";       
        conf.addRecord(sql, name, email,status);
    }

    private void viewPatients() {
        String query = "SELECT * FROM patients";
        String[] Headers = {"ID", "Patient Name", "Email","status"};
        String[] Columns = {"id", "p_name", "p_email","p_status"};
        
        conf.viewRecords(query, Headers, Columns);
     }

    private void editPatient() {
        int p_id;
        do{
            System.out.print("\nPatient ID: ");
            p_id = scan.nextInt();
            if(!conf.doesIDExist("patients", p_id)){
                System.out.println("Patient ID Doesn't Exist.");
            }
        }while(!conf.doesIDExist("patients", p_id));
        scan.nextLine();
        
        System.out.println("Enter Appointment Details:");
        
        System.out.print("Patient Name: ");
        String name = scan.nextLine();
        
        System.out.print("Email: ");
        String email = scan.nextLine();
        
          System.out.print("status: ");
        String status = scan.nextLine();
        
        String sql = "UPDATE patients SET p_name = ?, p_email = 1? ,p_status = ? WHERE id = ?";
        conf.updateRecord(sql, name, email,status, p_id);
    }

    private void deletePatient() {
        System.out.print("Enter ID you want to delete: ");
        int id = scan.nextInt();
        
        String sql = "DELETE FROM patients WHERE id = ?";
        conf.deleteRecord(sql, id);
    }
}