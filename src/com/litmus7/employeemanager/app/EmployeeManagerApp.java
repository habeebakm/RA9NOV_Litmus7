package com.litmus7.employeemanager.app;

import java.io.File;
import java.util.Scanner;
import com.litmus7.employeemanager.controller.EmployeeController;

public class EmployeeManagerApp {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        EmployeeController controller = new EmployeeController();

        File readfile = new File("employee.txt");
        File writefile = new File("employee.csv");

        while (true) {
            System.out.println("Enter the option");
            System.out.println("1. Get employee data from text file");
            System.out.println("2. Write employee data to CSV file");
            System.out.println("3. Get employee data from user");
            System.out.println("4. Manage employee data");
            System.out.println("5. Exit");

            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    controller.GetEmployeeDataFromTextFile(readfile);
                    break;
                case 2:
                    controller.WriteEmployeeDataToCSVFile(readfile, writefile);
                    break;
                case 3:
                    controller.GetEmployeeDataFromClient(writefile);
                    break;
                case 4:
                	controller.ManageEmployeeData();
                    break;
                case 5:
                	return;
                default:
                    System.out.println("Choose correct option");
            }
        }
    }
}



