package com.litmus7.employeemanager.app;

import java.io.File;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Scanner;

import com.litmus7.employeemanager.controller.EmployeeController;
import com.litmus7.employeemanager.dto.Employee;
import com.litmus7.employeemanager.dto.response;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EmployeeManagerApp {
    private static final Logger logger = LogManager.getLogger(EmployeeManagerApp.class);

        public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        Scanner scanner = new Scanner(System.in);
        EmployeeController employeeController = new EmployeeController();


        File readfile = new File("employee.txt");
        File writefile = new File("employee.csv");
        
        while (true) {
            System.out.println("Enter the option");
            System.out.println("1. Get employee data from text file");
            System.out.println("2. Write employee data to CSV file");
            System.out.println("3. Get employee data from user");
            System.out.println("4. Manage employee data");
            System.out.println("5. Exit");

            String input = scanner.nextLine(); 
            int choice;
            try {
                choice = Integer.parseInt(input.trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number between 1 and 5.");
                continue;  
            }

            int id;

            switch (choice) {
                case 1:
                    employeeController.getEmployeeDataFromTextFile(readfile);
                    break;
                case 2:
                    employeeController.writeEmployeeDataToCSVFile(readfile, writefile);
                    break;
                case 3:
                    employeeController.getEmployeeDataFromClient(true);
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
                        
                    	Employee employeeToCreate = employeeController.getEmployeeDataFromClient(false);
                        response<Void> response1 = employeeController.createEmployee(employeeToCreate);
                        if (response1.getErrorCode()!=null)
                        System.out.println(response1.getErrorCode()+" :"+response1.getMessage());
                        else
                            System.out.println(response1.getMessage());

                        break;
                    case 2:
                            response<List<Employee>> response2 = employeeController.getAllEmployees();
                            if (response2.getData()!=null) {
                                List<Employee> employeelist = response2.getData();
                                for (Employee employee : employeelist) {
                                    System.out.println(employee);
                                }
                            }
                            if (response2.getErrorCode()!=null)
                                System.out.println(response2.getErrorCode()+" :"+response2.getMessage());
                            break;

                    case 3:
                            System.out.print("Enter employee id: ");
                            id = Integer.parseInt(reader.readLine());
                            response<Employee> response3 = employeeController.getEmployeeById(id);
                            if (response3.getData()!=null) {
                                System.out.println(response3.getData());
                                break;
                            }
                            if (response3.getErrorCode()!=null)
                                System.out.println(response3.getErrorCode()+" :"+response3.getMessage());
                            break;
                    
                    case 4:
                            System.out.print("Enter employee id: ");
                            id = Integer.parseInt(reader.readLine());
                            response<Void> response4 = employeeController.deleteEmployeebyId(id);
                            if (response4.getErrorCode()!=null) {
                                System.out.println(response4.getErrorCode()+":"+response4.getMessage());
                                break;
                            }
                            System.out.println(response4.getMessage());
                            break;

                    case 5:
                            Employee employeeToUpdate = employeeController.getEmployeeDataFromClient(false);
                            response<Void>  response5 = employeeController.updateEmployee(employeeToUpdate);
                            if (response5.getErrorCode()!=null) {
                                System.out.println(response5.getErrorCode()+":"+response5.getMessage());
                                break;
                            }
                            System.out.println(response5.getMessage());
                            break;

                        default:
                            System.out.println("Invalid choice\n");
                            logger.warn("Invalid menu option selected: " + choice);
                    }
                    break;
                case 5:
                    scanner.close();
                    return;
                default:
                    System.out.println("Choose correct option");
                    logger.warn("Invalid menu option selected: " + choice);
            }
        }}}
