package com.litmus7.employeemanager.app;

import java.io.File;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import com.litmus7.employeemanager.controller.EmployeeController;
import com.litmus7.employeemanager.dto.Employee;
import com.litmus7.employeemanager.dto.response;

public class EmployeeManagerApp {
        public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        Scanner scanner = new Scanner(System.in);
        EmployeeController employeeController = new EmployeeController();

        File readfile = new File("employee.txt");
        File writefile = new File("employee.csv");
        int id;
        String email;
        String date;
        String status;
        
        while (true) {
            System.out.println("Enter the option");
            System.out.println("1. Get employee data from text file");
            System.out.println("2. Write employee data to CSV file");
            System.out.println("3. Get employee data from user");
            System.out.println("4. Manage employee data");
            System.out.println("5. Exit");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    employeeController.getEmployeeDataFromTextFile(readfile);
                    break;
                case 2:
                    employeeController.writeEmployeeDataToCSVFile(readfile, writefile);
                    break;
                case 3:
                    employeeController.getEmployeeDataFromClient(writefile);
                    break;
                case 4:
                    System.out.println("Enter the option:\n"
                            + "1. Add employee\n"
                            + "2. Get information of all employees\n"
                            + "3. Get information of an employee\n"
                            + "4. Delete employee information\n"
                            + "5. Update employee information\n");

                    int option = scanner.nextInt();
                    scanner.nextLine(); 

                    switch (option) {
                        case 1:
                            System.out.print("Enter employee id: ");
                            id = Integer.parseInt(reader.readLine());

                            System.out.print("Enter first name: ");
                            String firstName = reader.readLine();

                            System.out.print("Enter last name: ");
                            String lastName = reader.readLine();

                            System.out.print("Enter mobile number: ");
                            String mobile = reader.readLine();

                            System.out.print("Enter email: ");
                            email = reader.readLine();

                            System.out.print("Enter joining date (yyyy-MM-dd): ");
                            date = reader.readLine();

                            System.out.print("Enter active status(true/false): ");
                            status = reader.readLine();

                            Employee employee = new Employee(
                                    id, firstName, lastName, mobile, email,
                                    LocalDate.parse(date), Boolean.parseBoolean(status)
                            );

                            response<Void, Boolean, String> response1 = employeeController.createEmployee(employee);
                            System.out.println(response1.getMessage());
                            break;

                        case 2:
                            response<List<Employee>, Boolean, String> response2 = employeeController.getAllEmployees();
                            if (response2.getApplicationStatus()) {
                                List<Employee> employeelist = response2.getData();
                                for (Employee emp : employeelist) {
                                    System.out.println(emp);
                                }
                            }
                            System.out.println(response2.getMessage());
                            break;

                        case 3:
                            System.out.print("Enter employee id: ");
                            id = Integer.parseInt(reader.readLine());
                            response<Employee, Boolean, String> response3 = employeeController.getEmployeeById(id);
                            if (response3.getApplicationStatus()) {
                                System.out.println(response3.getData());
                            }
                            System.out.println(response3.getMessage());
                            break;

                        case 4:
                            System.out.print("Enter employee id: ");
                            id = Integer.parseInt(reader.readLine());
                            response<Void, Boolean, String> response4 = employeeController.deleteEmployeebyId(id);
                            System.out.println(response4.getMessage());
                            break;

                        case 5:
                            System.out.print("Enter employee id: ");
                            id = Integer.parseInt(reader.readLine());

                            System.out.print("Enter first name: ");
                            String firstname = reader.readLine();

                            System.out.print("Enter last name: ");
                            String lastname = reader.readLine();

                            System.out.print("Enter mobile number: ");
                            String mobileno = reader.readLine();

                            System.out.print("Enter email: ");
                            email = reader.readLine();

                            System.out.print("Enter joining date (yyyy-MM-dd): ");
                            date = reader.readLine();

                            System.out.print("Enter active status(true/false): ");
                            status = reader.readLine();

                            Employee employeeToUpdate = new Employee(
                            		id, firstname, lastname, mobileno, email,
                                    LocalDate.parse(date), Boolean.parseBoolean(status)
                            );

                            response<Void, Boolean, String> response5 = employeeController.updateEmployee(employeeToUpdate);
                            System.out.println(response5.getMessage());
                            break;

                        default:
                            System.out.println("Invalid choice\n");
                    }
                    break;
                case 5:
                    scanner.close();
                    return;
                default:
                    System.out.println("Choose correct option");
            }
        }
    }
}
