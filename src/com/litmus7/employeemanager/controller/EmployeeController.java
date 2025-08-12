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
import com.litmus7.employeemanager.constants.AppException;


public class EmployeeController {
	private static final Scanner scanner = new Scanner(System.in);
	EmployeeService employeeService=new EmployeeService();
    private static final List<Employee> employees = new ArrayList<>();
    
    public static void getEmployeeDataFromTextFile(File readfile) {
        if (readfile != null && readfile.exists()) {
            try (BufferedReader bufferedreader = new BufferedReader(new FileReader(readfile))) {
                String line;
                while ((line = bufferedreader.readLine()) != null) {
                    String[] data = line.split("\\$");
                    if (data.length == 7) {
                        int id = Integer.parseInt(data[0].trim());
                        String firstname = data[1].trim();
                        String lastname = data[2].trim();
                        String mobile = data[3].trim();
                        String email = data[4].trim();
                        String date = data[5].trim();
                        String status = data[6].trim();

                        if (ValidationUtil.isunique(id, employees) &&
                            ValidationUtil.isnotnull(firstname) &&
                            ValidationUtil.isnotnull(lastname) &&
                            ValidationUtil.isvalidnumber(mobile) &&
                            ValidationUtil.isvalidemail(email) &&
                            ValidationUtil.isValidDate(date) &&
                            ValidationUtil.isvalidstatus(status)) {

                            employees.add(new Employee(id, firstname, lastname, mobile, email, 
                                                       LocalDate.parse(date), Boolean.parseBoolean(status)));
                        } else {
                            System.out.println("Invalid data");
                        }
                    }
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        } else {
            System.out.println("File is either null or does not exist.");
        }
    }
    
    public static void writeEmployeeDataToCSVFile(File readfile, File writefile) {
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

                        employees.add(new Employee(id, fname, lname, mobile, email, 
                                                   LocalDate.parse(date), Boolean.parseBoolean(status)));
                        writer.println(String.join(",", data));
                    } else {
                        System.out.println("Invalid data: " + String.join(",", data));
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    public static Employee getEmployeeData() {
       
        System.out.println("Enter employee details:\nID\nFirst name\nLast name\nMobile\nEmail\nJoin Date (yyyy-mm-dd)\nStatus (true/false)");

        try {
            int id = Integer.parseInt(scanner.nextLine().trim());
            String firstname = scanner.nextLine().trim();
            String lastname = scanner.nextLine().trim();
            String mobile = scanner.nextLine().trim();
            String email = scanner.nextLine().trim();
            String date = scanner.nextLine().trim();
            String status = scanner.nextLine().trim();

            if (ValidationUtil.isunique(id, employees) &&
                ValidationUtil.isnotnull(firstname) &&
                ValidationUtil.isnotnull(lastname) &&
                ValidationUtil.isvalidnumber(mobile) &&
                ValidationUtil.isvalidemail(email) &&
                ValidationUtil.isValidDate(date) &&
                ValidationUtil.isvalidstatus(status)) {

                return new Employee(id, firstname, lastname, mobile, email, LocalDate.parse(date), Boolean.parseBoolean(status));
            } else {
                System.out.println("Invalid employee details");
                return null;
            }
            
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }


    public static void getEmployeeDataFromClient(File writefile) {
        
        try (PrintWriter writer = new PrintWriter(new FileWriter(writefile))) {
            System.out.print("Enter number of employees: ");
            int n = Integer.parseInt(scanner.nextLine());

            for (int i = 0; i < n; i++) {
                Employee emp = getEmployeeData();
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
   }
    public response<Void, Boolean, String> createEmployee(Employee employee) {
		if (employee == null) return new response<>(null,false,"enter employee details");
		try {
			employeeService.createEmployee(employee);
		} catch (AppException e) {
			return new response<>(null,false,e.getMessage());
		}
		return new response<>(null,true,"employee inserted successfully");
	}
	
	public response<List<Employee>, Boolean, String>  getAllEmployees() {
		List<Employee> employees;
		try {
			employees = employeeService.getAllEmployees();
		} catch (AppException e) {
			return new response<>(null,false,e.getMessage());
		}
		return new response<>(employees,true,"employee list retrieved successfully");
	}
	
	public response<Employee, Boolean, String> getEmployeeById(int employeeId) {
		if (employeeId <= 0) return new response<>(null,false,"invalid employee id");
		Employee employee = null;
		try {
			employee = employeeService.getEmployeeData(employeeId);
		} catch (AppException e) {
			return new response<>(null,false,e.getMessage());
		}
		return new response<>(employee,true,"employee data retrieved successfully");
	}
	
	public response<Void, Boolean, String> deleteEmployeebyId(int employeeId) {
		if (employeeId <= 0) return new response<>(null,false,"invalid employee id");
		try {
			employeeService.deleteEmployeeData(employeeId);
		} catch (AppException e) {
			return new response<>(null,false,e.getMessage());
		}
		return new response<>(null,true,"employee deleted successfully");
	}

	public response<Void, Boolean, String> updateEmployee(Employee employee) {
		if (employee == null) return new response<>(null,false,"enter correct employee details");
		try {
			employeeService.updateEmployee(employee);
		} catch (AppException e) {
			return new response<>(null,false,e.getMessage());
		}
		return new response<>(null,true,"employee data updated successfully");
	}
}
