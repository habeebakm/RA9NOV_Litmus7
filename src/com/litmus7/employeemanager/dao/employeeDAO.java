package com.litmus7.employeemanager.dao;

import com.litmus7.employeemanager.constants.SqlConstants;
import com.litmus7.employeemanager.dto.Employee;
import com.litmus7.employeemanager.util.DatabaseConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class employeeDAO {
        public boolean saveEmployee (Employee emp) throws SQLException {
        try (Connection connection = DatabaseConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(SqlConstants.insert_employee)) {
            statement.setInt(1, emp.getId());
            statement.setString(2, emp.getFirstName());
            statement.setString(3, emp.getLastName());
            statement.setString(4, emp.getMobileNumber());
            statement.setString(5, emp.getEmail());
            statement.setDate(6, Date.valueOf(emp.getDateofJoin()));
            statement.setBoolean(7, emp.isActive());

            System.out.println("Employee added to DB.");
            return statement.executeUpdate() > 0;

        }
    }

    public List<Employee> selectAllEmployees() throws SQLException{
        List<Employee> employees = new ArrayList<>();
        try (Connection connection = DatabaseConnectionUtil.getConnection();
        	Statement statement = connection.createStatement();
             ResultSet resultset = statement.executeQuery(SqlConstants.select_all_employees)) {

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
        } 
    }

    public Employee selectEmployeeById(int id) throws SQLException{
        try (Connection connection = DatabaseConnectionUtil.getConnection();
        	PreparedStatement statement = connection.prepareStatement(SqlConstants.select_employee)) {
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
                        resulset.getBoolean("status")
                    );
                }
            }
        } 
        return null;
    }

    public boolean deleteEmployeeById(int empId) throws SQLException {
        try (Connection connection = DatabaseConnectionUtil.getConnection();
        	PreparedStatement pstmt = connection.prepareStatement(SqlConstants.delete_employee)) {
            pstmt.setInt(1, empId);
            return pstmt.executeUpdate() > 0;
        } 
    }

    public boolean updateEmployee(Employee emp) throws SQLException{
        try (Connection connection = DatabaseConnectionUtil.getConnection();
        	PreparedStatement pstmt = connection.prepareStatement(SqlConstants.update_employee)) {
            pstmt.setString(1, emp.getFirstName());
            pstmt.setString(2, emp.getLastName());
            pstmt.setString(3, emp.getMobileNumber());
            pstmt.setString(4, emp.getEmail());
            pstmt.setDate(5, Date.valueOf(emp.getDateofJoin()));
            pstmt.setBoolean(6, emp.isActive());
            pstmt.setInt(7, emp.getId());
            return pstmt.executeUpdate() > 0;
        }
    }
}
