package com.litmus7.employeemanager.dao;

import com.litmus7.employeemanager.constants.SqlConstants;
import com.litmus7.employeemanager.dto.Employee;
import com.litmus7.employeemanager.util.DatabaseConnectionUtil;
import com.litmus7.employeemanager.constants.AppException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;


public class employeeDAO {
        public int saveEmployee(Employee employee) throws AppException {
        try (Connection connection = DatabaseConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(SqlConstants.insert_employee)) {
            statement.setInt(1, employee.getId());
            statement.setString(2, employee.getFirstName());
            statement.setString(3, employee.getLastName());
            statement.setString(4, employee.getMobileNumber());
            statement.setString(5, employee.getEmail());
            statement.setDate(6, Date.valueOf(employee.getDateofJoin()));
            statement.setBoolean(7, employee.isActive());

            return statement.executeUpdate();

        }
        catch(SQLException e) {
        	throw new AppException("An error occurred while inserting the employee.",e);
        }
    }
        
    public List<Employee> selectAllEmployees() throws AppException{
        try (Connection connection = DatabaseConnectionUtil.getConnection();
        	 PreparedStatement statement = connection.prepareStatement(SqlConstants.select_all_employees);
             ResultSet resultset = statement.executeQuery()) {
        	 List<Employee> employees = new ArrayList<>();

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
        catch(SQLException e) {
        	throw new AppException("An error occurred while retrieving employee list", e);
    }
    }

    public Employee selectEmployeeById(int id) throws AppException{
        try (Connection connection = DatabaseConnectionUtil.getConnection();
        	PreparedStatement statement = connection.prepareStatement(SqlConstants.select_employee)) {
            statement.setInt(1, id);
            ResultSet resulset = statement.executeQuery();
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
                return null;
            }
            catch(SQLException e) {
                throw new AppException("An error occurred while retrieving employee details", e);
        } 
       
    }

    public int deleteEmployeeById(int id) throws AppException {
        try (Connection connection = DatabaseConnectionUtil.getConnection();
        	PreparedStatement statement = connection.prepareStatement(SqlConstants.delete_employee)) {
        	statement.setInt(1, id);
            return statement.executeUpdate();
        } 
        catch (SQLException e) {
            throw new AppException("An error occurred while deleting employee", e);
        }
    }

    public int updateEmployee(Employee emp) throws AppException{
        try (Connection connection = DatabaseConnectionUtil.getConnection();
        	PreparedStatement statement = connection.prepareStatement(SqlConstants.update_employee)) {
        	statement.setString(1, emp.getFirstName());
        	statement.setString(2, emp.getLastName());
            statement.setString(3, emp.getMobileNumber());
            statement.setString(4, emp.getEmail());
            statement.setDate(5, Date.valueOf(emp.getDateofJoin()));
            statement.setBoolean(6, emp.isActive());
            statement.setInt(7, emp.getId());
            return statement.executeUpdate();
        }
        catch (SQLException e) {
            throw new AppException("An error occurred while updating employee", e);
        }
        
    }

}


