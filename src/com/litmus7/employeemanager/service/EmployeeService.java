package com.litmus7.employeemanager.service;

import com.litmus7.employeemanager.dao.employeeDAO;
import com.litmus7.employeemanager.dto.Employee;
import com.litmus7.employeemanager.dto.response;
import com.litmus7.employeemanager.controller.EmployeeController;

import java.util.List;
import java.util.Scanner;

public class EmployeeService {

    private employeeDAO dao = new employeeDAO();
    Scanner scanner = new Scanner(System.in);

    public response<Boolean, String, String> addEmployeeData() {
        EmployeeController controller = new EmployeeController();
        Employee employee = controller.GetEmployeeData();
        boolean result = dao.createEmployee(employee);
        if (result) {
            return new response<>(true, "success", "Employee added successfully.");
        } else {
            return new response<>(false, "error", "Failed to add employee.");
        }
    }

    public response<List<Employee>, String, String> getAllEmployeesData() {
        List<Employee> employees = dao.getAllEmployees();
        if (employees != null && !employees.isEmpty()) {
            return new response<>(employees, "success", "Employee data fetched successfully.");
        } else {
            return new response<>(null, "error", "No employee data found.");
        }
    }

    public response<Employee, String, String> getEmployeeData() {
        System.out.println("Enter the employee id:");
        int id = scanner.nextInt();
        Employee employee = dao.getEmployeeById(id);
        if (employee != null) {
            return new response<>(employee, "success", "Employee found.");
        } else {
            return new response<>(null, "error", "Employee not found.");
        }
    }

    public response<Boolean, String, String> deleteEmployeeData() {
    	System.out.println("Enter the employee id:");
        int id = scanner.nextInt();
        boolean deleted =dao.deleteEmployeeById(id);
        if (deleted) {
            return new response<>(true, "success", "Employee deleted successfully.");
        } else {
            return new response<>(false, "error", "Failed to delete employee.");
        }
    }

    public response<Boolean, String, String> updateEmployeeData() {
        response<Employee, String, String> employeeResponse = getEmployeeData();
        Employee employee = employeeResponse.getData();
        if (employee == null) {
            return new response<>(false, "error", "Cannot update. Employee not found.");
        }
        boolean updated = dao.updateEmployee(employee);
        if (updated) {
            return new response<>(true, "success", "Employee updated successfully.");
        } else {
            return new response<>(false, "error", "Failed to update employee.");
        }
    }
}
