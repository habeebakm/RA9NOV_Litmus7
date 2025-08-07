package com.litmus7.employeemanager.controller;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

import com.litmus7.employeemanager.dto.Employee;
import com.litmus7.employeemanager.util.ValidationUtil;
import com.litmus7.employeemanager.service.EmployeeService;
import com.litmus7.employeemanager.dto.response;



public class EmployeeController {
	private static final List<Employee> employees = new ArrayList<>();

public static void ManageEmployeeData() {
	Scanner scanner = new Scanner(System.in);
    EmployeeService employeeService = new EmployeeService();
	System.out.println("enter the option\n 1.add employee\n"
			+ "2.get information of all employees\n3.get information of an employee\n"
			+ "4.delete employee information\n5.update employee information\n");
	
	int choice = scanner.nextInt();
	EmployeeService employeeservice=new EmployeeService();

        switch (choice) {
            case 1:
            	response<Boolean, String, String> addResponse = employeeService.addEmployeeData();
	            System.out.println(addResponse.getMessage());
	            break;
            case 2:
            	response<List<Employee>, String, String> allEmployeeResponse = employeeService.getAllEmployeesData();
                System.out.println(allEmployeeResponse.getMessage());
                if (allEmployeeResponse.getData() != null) {
                    for (Employee emp : allEmployeeResponse.getData()) {
                        System.out.println(emp);
                    }
                }
                break;
            case 3:
            	response<Employee, String, String> singleResponse = employeeService.getEmployeeData();
                System.out.println(singleResponse.getMessage());
                if (singleResponse.getData() != null) {
                    System.out.println(singleResponse.getData());
                }
                else{
                    System.out.println(singleResponse.getMessage());
                }
                break;
            case 4:
            	response<Boolean, String, String> deleteResponse = employeeservice.deleteEmployeeData();
                System.out.println(deleteResponse.getMessage());
                break;
            case 5:
            	response<Boolean, String, String> updateResponse=employeeservice.updateEmployeeData();
                System.out.println(updateResponse.getMessage());
                break;
            default:
                System.out.println("Invalid choice.");
        }
    }
	

	public static void GetEmployeeDataFromTextFile(File readfile) {
        if (readfile != null && readfile.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(readfile))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] data = line.split("\\$");

                    if (data.length == 7) {
                        int id = Integer.parseInt(data[0].trim());
                        String fname = data[1].trim();
                        String lname = data[2].trim();
                        String mobile = data[3].trim();
                        String email = data[4].trim();
                        String dateStr = data[5].trim();
                        String statusStr = data[6].trim();

                        if (ValidationUtil.isunique(id, employees) &&
                            ValidationUtil.isnotnull(fname) &&
                            ValidationUtil.isnotnull(lname) &&
                            ValidationUtil.isvalidnumber(mobile) &&
                            ValidationUtil.isvalidemail(email) &&
                            ValidationUtil.isValidDate(dateStr) &&
                            ValidationUtil.isvalidstatus(statusStr)) {

                            Employee emp = new Employee(id,fname,lname,mobile,email,LocalDate.parse(dateStr), Boolean.parseBoolean(statusStr));
                            employees.add(emp);

                        } else {
                            System.out.println("Invalid data");
                        }
                    }
                }
                br.close();
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        } else {
            System.out.println("File is either null or does not exist.");
        }
    }
    

    public static void WriteEmployeeDataToCSVFile(File readfile, File writefile) {
        try (
            BufferedReader reader = new BufferedReader(new FileReader(readfile));
            PrintWriter writer = new PrintWriter(new FileWriter(writefile))
        ) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split("\\$");
                if (data.length == 7) {
                    int id = Integer.parseInt(data[0].trim());
                    String fname = data[1].trim();
                    String lname = data[2].trim();
                    String mobile = data[3].trim();
                    String email = data[4].trim();
                    String date = data[5].trim();
                    String status = data[6].trim();

                    if (ValidationUtil.isunique(id, employees) &&
                        ValidationUtil.isnotnull(fname) &&
                        ValidationUtil.isnotnull(lname) &&
                        ValidationUtil.isvalidnumber(mobile) &&
                        ValidationUtil.isvalidemail(email) &&
                        ValidationUtil.isValidDate(date) &&
                        ValidationUtil.isvalidstatus(status)) {

                        Employee emp = new Employee(id,fname,lname,mobile,email,LocalDate.parse(date), Boolean.parseBoolean(status));
                        employees.add(emp);
                        String info = String.join(",", data);
                        writer.println(info);
                    } else {
                        System.out.println("Invalid data: " + String.join(",", data));
                    }
                }     
            }
            reader.close();
            writer.close();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    
    public static Employee GetEmployeeData() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter employee details:\nID\nFirst name\nLast name\nMobile\nEmail\nJoin Date (yyyy-mm-dd)\nStatus (true/false)");

        try {
            int id = Integer.parseInt(sc.nextLine().trim());
            String fname = sc.nextLine().trim();
            String lname = sc.nextLine().trim();
            String mobile = sc.nextLine().trim();
            String email = sc.nextLine().trim();
            String date = sc.nextLine().trim();
            String status = sc.nextLine().trim();

            if (ValidationUtil.isunique(id, employees) &&
                    ValidationUtil.isnotnull(fname) &&
                    ValidationUtil.isnotnull(lname) &&
                    ValidationUtil.isvalidnumber(mobile) &&
                    ValidationUtil.isvalidemail(email) &&
                    ValidationUtil.isValidDate(date) &&
                    ValidationUtil.isvalidstatus(status)) {

                return new Employee(id, fname, lname, mobile, email, LocalDate.parse(date), Boolean.parseBoolean(status));
            } else {
                System.out.println("Invalid employee details");
                return null;
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }
    public static void GetEmployeeDataFromClient(File writefile) {
        Scanner sc = new Scanner(System.in);
        try (PrintWriter writer = new PrintWriter(new FileWriter(writefile))) {
            System.out.print("Enter number of employees: ");
            int n = Integer.parseInt(sc.nextLine());

            for (int i = 0; i < n; i++) {
                Employee emp = GetEmployeeData();
                if (emp != null) {
                    employees.add(emp);
                    writer.println(emp.getId() + "," + emp.getFirstName() + "," + emp.getLastName() + "," +
                            emp.getMobileNumber() + "," + emp.getEmail() + "," +
                            emp.getDateofJoin() + "," + emp.isActive());
                    System.out.println("Employee added successfully!");
                } else {
                    System.out.println("Invalid employee details");
                }
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
        sc.close();
    }
}
