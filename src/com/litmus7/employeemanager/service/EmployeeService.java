package com.litmus7.employeemanager.service;

import com.litmus7.employeemanager.controller.EmployeeController;
import com.litmus7.employeemanager.dao.employeeDAO;
import com.litmus7.employeemanager.dto.Employee;
import com.litmus7.employeemanager.dto.response;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class EmployeeService {

    private final employeeDAO dao = new employeeDAO();
    Scanner sc = new Scanner(System.in);
    


    public response<Boolean, String, String> addEmployeeData() {
        try {
            Employee employee = EmployeeController.getEmployeeData();
            if (employee == null) {
                return new response<>(false, "Invalid employee data.", null);
            }

            boolean result = dao.saveEmployee(employee);
            return new response<>(result,
                    result ? "Employee added successfully." : "Failed to add employee.",
                    null);
        } catch (SQLException e) {
            return new response<>(false, "Database error while adding employee.", e.getMessage());
        }
    }


    public response<List<Employee>, String, String> getAllEmployeesData() {
        try {
            List<Employee> employees = dao.selectAllEmployees();
            if (employees.isEmpty()) {
                return new response<>(employees, "No employees found.", null);
            }
            return new response<>(employees, "Employees retrieved successfully.", null);
        } catch (SQLException e) {
            return new response<>(null, "Database error while fetching employees.", e.getMessage());
        }
    }


    public response<Employee, String, String> getEmployeeData() {
        try {
        	System.out.println("enter employee id");
            int id= Integer.parseInt(sc.nextLine().trim());
            Employee emp = dao.selectEmployeeById(id);
            if (emp == null) {
                return new response<>(null, "Employee not found.", null);
            }
            return new response<>(emp, "Employee retrieved successfully.", null);
        } catch (SQLException e) {
            return new response<>(null, "Database error while fetching employee.", e.getMessage());
        }
    }

    public response<Boolean, String, String> deleteEmployeeData() {
        try {
        	System.out.println("enter employee id");
            int id= Integer.parseInt(sc.nextLine().trim());
            boolean deleted = dao.deleteEmployeeById(id);
            return new response<>(deleted,
                    deleted ? "Employee deleted successfully." : "Employee not found.",
                    null);
        } catch (SQLException e) {
            return new response<>(false, "Database error while deleting employee.", e.getMessage());
        }
    }


    public response<Boolean, String, String> updateEmployeeData() {
        try {
            Employee employee = EmployeeController.getEmployeeData();
            if (employee == null) {
                return new response<>(false, "Invalid employee data.", null);
            }

            boolean updated = dao.updateEmployee(employee);
            return new response<>(updated,
                    updated ? "Employee updated successfully." : "Employee not found.",
                    null);
        } catch (SQLException e) {
            return new response<>(false, "Database error while updating employee.", e.getMessage());
        }
    }
}
