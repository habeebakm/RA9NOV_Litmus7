package com.litmus7.employeemanager.dao;

import com.litmus7.employeemanager.dto.Employee;
import com.litmus7.employeemanager.util.DatabaseConnectionUtil;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class employeeDAO {
    private Connection conn = DatabaseConnectionUtil.getConnection();
    public boolean createEmployee(Employee emp) {
    	
        String insertquery = "INSERT INTO employee (emp_id, first_name, last_name, mobile_number, email, joining_date,status) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = conn.prepareStatement(insertquery)) {
        	statement.setInt(1, emp.getId());
        	statement.setString(2, emp.getFirstName());
            statement.setString(3, emp.getLastName());
            statement.setString(4, emp.getMobileNumber());
            statement.setString(5, emp.getEmail());
            statement.setDate(6, Date.valueOf(emp.getDateofJoin()));
            statement.setBoolean(7, emp.isActive());

            System.out.println("Employee added to DB.");
            return statement.executeUpdate() > 0;
           
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public List<Employee> getAllEmployees() {
        List<Employee> employees = new ArrayList<>();
        String query = "SELECT emp_id, first_name, last_name, mobile_number, email, joining_date,status FROM employees";
        try (Statement statement = conn.createStatement();
             ResultSet resultset = statement.executeQuery(query)) {
            while (resultset.next()) {
                Employee emp = new Employee(
                		resultset.getInt("emp_id"),
                		resultset.getString("first_name"),
                		resultset.getString("last_name"),
                		resultset.getString("mobile_number"),
                		resultset.getString("email"),
                		resultset.getDate("joining_date").toLocalDate(),
                		resultset.getBoolean("status")
                );
                employees.add(emp);
            }
            return employees;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Employee getEmployeeById(int id) {
        String query = "SELECT emp_id, first_name, last_name, mobile_number, email, joining_date,status FROM employee WHERE emp_id = ?";
        try (PreparedStatement statement = conn.prepareStatement(query)) {
        	statement.setInt(1, id);
            try (ResultSet resulset = statement.executeQuery()) {
                if (resulset.next()) {
                    return new Employee(
                    		resulset.getInt("emp_id"),
                    		resulset.getString("first_name"),
                    		resulset.getString("last_name"),
                            resulset.getString("mobile_number"),
                            resulset.getString("email"),
                            resulset.getDate("joining_date").toLocalDate(),
                            resulset.getBoolean("active_status")
                            );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public boolean deleteEmployeeById(int empId) {
        String query = "DELETE FROM employees WHERE emp_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, empId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Failed to delete employee.");
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean updateEmployee(Employee emp) {
        String query = "UPDATE employees SET first_name=?, last_name=?, mobile_number=?, email=?, joining_date=?, status=? WHERE emp_id=?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, emp.getFirstName());
            pstmt.setString(2, emp.getLastName());
            pstmt.setString(3, emp.getMobileNumber());
            pstmt.setString(4, emp.getEmail());
            pstmt.setDate(5, Date.valueOf(emp.getDateofJoin()));
            pstmt.setBoolean(6, emp.isActive());
            pstmt.setInt(7, emp.getId());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}