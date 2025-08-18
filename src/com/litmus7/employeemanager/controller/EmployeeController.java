package com.litmus7.employeemanager.controller;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.File;
import java.io.FileNotFoundException;
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
import com.litmus7.employeemanager.constants.ErrorCodes;
import com.litmus7.employeemanager.constants.exception;


public class EmployeeController {
	EmployeeService employeeService=new EmployeeService();
    private static final List<Employee> employees = new ArrayList<>();
    
    public void getEmployeeDataFromTextFile(File readfile) {
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

                            employees.add(new Employee(id, firstname, lastname, mobile, email,LocalDate.parse(date), status));
                        } else {
                            System.out.println("Invalid data");
                        }}}
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        } else {
            System.out.println("File is either null or does not exist.");
        }}
    
    
    
    public void writeEmployeeDataToCSVFile(File readfile, File writefile) {
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
                                                   LocalDate.parse(date), status));
                        writer.println(String.join(",", data));
                    } else {
                        System.out.println("Invalid data: " + String.join(",", data));
                    }}}
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }}
    
    
    
	public Employee getEmployeeDataFromClient(boolean isUpdate) {
	    Scanner scanner = new Scanner(System.in);
	    
	    int id;
	    String firstName, lastName, mobile, email, date, status;

	    while (true) {
	        System.out.print("Enter employee id: ");
	            id = Integer.parseInt(scanner.nextLine().trim());
	            if (id > 0) 
	                break;
	        System.out.println(ErrorCodes.INVALID_ID + ": Employee ID is not valid");}

	    while (true) {
	        System.out.print("Enter first name: ");
	        firstName = scanner.nextLine().trim();
	        if (ValidationUtil.isnotnull(firstName)) 
	            break;
	        System.out.println(ErrorCodes.INVALID_NAME + ": Employee name cannot be empty");
	    }

	    while (true) {
	        System.out.print("Enter last name: ");
	        lastName = scanner.nextLine().trim();
	        if (ValidationUtil.isnotnull(lastName)) 
	            break;
	        System.out.println(ErrorCodes.INVALID_NAME + ": Employee name cannot be empty");
	    }

	    while (true) {
	        System.out.print("Enter mobile number: ");
	        mobile = scanner.nextLine().trim();
	        if (ValidationUtil.isvalidnumber(mobile)) 
	            break;
	        System.out.println(ErrorCodes.INVALID_MOBILE + ": Invalid phone number format");
	    }
	    
	    while (true) {
	        System.out.print("Enter email: ");
	        email = scanner.nextLine().trim();
	        if (ValidationUtil.isvalidemail(email)) 
	            break;
	        System.out.println(ErrorCodes.INVALID_EMAIL + ": Email format is incorrect");
	    }

	    while (true) {
	        System.out.print("Enter joining date (yyyy-MM-dd): ");
	        date = scanner.nextLine().trim();
	        if (ValidationUtil.isValidDate(date)) 
	            break;
	        System.out.println(ErrorCodes.INVALID_DATE + ": Invalid date format. Expected yyyy-MM-dd");
	    }

	    while (true) {
	        System.out.print("Enter active status (true/false): ");
	        status = scanner.nextLine().trim();
	        if ("true".equalsIgnoreCase(status) || "false".equalsIgnoreCase(status)) 
	            break;
	        System.out.println(ErrorCodes.INVALID_STATUS + ": Status must be true or false");
	    }
	    
	    Employee employee=new Employee(id, firstName, lastName, mobile, email,LocalDate.parse(date), status);
	    scanner.close();
	    if(isUpdate) {
	    	try (PrintWriter writer = new PrintWriter("employees.csv")) {
	            writer.println("id,firstName,lastName,email,mobile");
	            employees.add(employee);
	        } catch (FileNotFoundException e) {
	            e.printStackTrace();    
	        }}
	    return employee;
	    }

	
    public response<Void> createEmployee(Employee employee) {
		if (employee == null) return new response<>(ErrorCodes.INVALID_INPUT,"enter employee details",null);
		try {
			employeeService.createEmployee(employee);
		} catch (exception e) {
			return new response<>(ErrorCodes.UNKNOWN_ERROR,e.getMessage(),null);
		}
		return new response<>(null,"employee inserted successfully",null);
	}
	
    
	public response<List<Employee>>  getAllEmployees() {
		List<Employee> employees;
		try {
			employees = employeeService.getAllEmployees();
		} catch (exception e) {
			return new response<>(ErrorCodes.UNKNOWN_ERROR,e.getMessage(),null);
		}
		return new response<>(null,"employee list retrieved successfully",employees);
	}
	
	
	public response<Employee> getEmployeeById(int employeeId) {
		if (employeeId <= 0) return new response<>(ErrorCodes.INVALID_EMPLOYEE_ID,"invalid employee id",null);
		Employee employee = null;
		try {
			employee = employeeService.getEmployeeData(employeeId);
		} catch (exception e) {
			return new response<>(ErrorCodes.UNKNOWN_ERROR,e.getMessage(),null);
		}
		return new response<>(null,"employee data retrieved successfully",employee);
	}
	
	
	public response<Void> deleteEmployeebyId(int employeeId) {
		if (employeeId <= 0) return new response<>(ErrorCodes.INVALID_EMPLOYEE_ID,"invalid employee id",null);
		try {
			employeeService.deleteEmployeeData(employeeId);
		} catch (exception e) {
			return new response<>(ErrorCodes.UNKNOWN_ERROR,e.getMessage(),null);
		}
		return new response<>(null,"employee deleted successfully",null);
	}

	
	public response<Void> updateEmployee(Employee employee) {
		if (employee == null) return new response<>(ErrorCodes.INVALID_EMPLOYEE_ID,"enter correct employee details",null);
		try {
			employeeService.updateEmployee(employee);
		} catch (exception e) {
			return new response<>(ErrorCodes.UNKNOWN_ERROR,e.getMessage(),null);
		}
		return new response<>(null,"employee data updated successfully",null);
	}
}
