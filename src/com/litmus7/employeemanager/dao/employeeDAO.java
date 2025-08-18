package com.litmus7.employeemanager.dao;

import com.litmus7.employeemanager.constants.ErrorCodes;
import com.litmus7.employeemanager.constants.SqlConstants;
import com.litmus7.employeemanager.dto.Employee;
import com.litmus7.employeemanager.util.DatabaseConnectionUtil;
import com.litmus7.employeemanager.constants.exception;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;



public class employeeDAO {
	private static final Logger logger = LogManager.getLogger(employeeDAO.class);
        public int saveEmployee(Employee employee) throws exception {
        try (Connection connection = DatabaseConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(SqlConstants.insert_employee)) {
            statement.setInt(1, employee.getId());
            statement.setString(2, employee.getFirstName());
            statement.setString(3, employee.getLastName());
            statement.setString(4, employee.getMobileNumber());
            statement.setString(5, employee.getEmail());
            statement.setDate(6, java.sql.Date.valueOf(employee.getDateofJoin()));
            statement.setString(7, employee.isActive());

            return statement.executeUpdate();

        }
        catch (SQLIntegrityConstraintViolationException e) {
        	logger.error("Integrity violation while inserting employee ID: " + employee.getId(), e);
             throw new exception(ErrorCodes.EMPLOYEE_ALREADY_EXISTS, 
                "Employee with the given ID or Email already exists.");
        } catch (SQLException e) {
        	 logger.error("SQL error while creating employee with ID: " + employee.getId(), e);
            throw new exception(ErrorCodes.DATABASE_ERROR, 
                "An error occurred while inserting the employee.");
        }
    }
        
    public List<Employee> selectAllEmployees() throws exception{
        try (Connection connection = DatabaseConnectionUtil.getConnection();
        	 PreparedStatement statement = connection.prepareStatement(SqlConstants.select_all_employees);
             ResultSet resultset = statement.executeQuery()) {
        	 List<Employee> employees = new ArrayList<>();

            while (resultset.next()) {
                Employee emp = new Employee(
                    resultset.getInt("id"),
                    resultset.getString("first_name"),
                    resultset.getString("last_name"),
                    resultset.getString("mobile_number"),
                    resultset.getString("email"),
                    resultset.getDate("joining_date").toLocalDate(),
                    resultset.getString("status")
                );
                employees.add(emp);
            }
            logger.info("DAO: Retrieved employee list, count=" + employees.size());
            return employees;
        } 
        catch(SQLException e) {
        	logger.error("SQL error while retrieving employee list: "+e);
        	throw new exception(ErrorCodes.DATABASE_ERROR,"An error occurred while retrieving employee list");
    }
    }

    public Employee selectEmployeeById(int id) throws exception{
        try (Connection connection = DatabaseConnectionUtil.getConnection();
        		PreparedStatement statement = connection.prepareStatement(SqlConstants.select_employee);
        	) {
        	statement.setInt(1, id);
            ResultSet resulset = statement.executeQuery();
                if (resulset.next()) {
                	logger.info("DAO: Employee found with ID: " + id);
                    return new Employee(
                        resulset.getInt("id"),
                        resulset.getString("first_name"),
                        resulset.getString("last_name"),
                        resulset.getString("mobile_number"),
                        resulset.getString("email"),
                        resulset.getDate("joining_date").toLocalDate(),
                        resulset.getString("status")
                    );
                }return null;
            }
            catch(SQLException e) {
            	logger.error("SQL error while retrieving employee data: "+e);
                throw new exception(ErrorCodes.DATABASE_ERROR,"An error occurred while retrieving employee details");
        }}

    public int deleteEmployeeById(int id) throws exception {
        try (Connection connection = DatabaseConnectionUtil.getConnection();
        	PreparedStatement statement = connection.prepareStatement(SqlConstants.delete_employee)) {
        	statement.setInt(1, id);
            return statement.executeUpdate();
        } 
        catch (SQLException e) {
        	logger.error("SQL error while deleting employee: "+e);
            throw new exception(ErrorCodes.DATABASE_ERROR,"An error occurred while deleting employee");
        }}

    public int updateEmployee(Employee emp) throws exception{
        try (Connection connection = DatabaseConnectionUtil.getConnection();
        	PreparedStatement statement = connection.prepareStatement(SqlConstants.update_employee)) {
        	statement.setString(1, emp.getFirstName());
        	statement.setString(2, emp.getLastName());
            statement.setString(3, emp.getMobileNumber());
            statement.setString(4, emp.getEmail());
            statement.setDate(5, Date.valueOf(emp.getDateofJoin()));
            statement.setString(6, emp.isActive());
            statement.setInt(7, emp.getId());
            return statement.executeUpdate();
        }
        catch (SQLException e) {
        	logger.error("SQL error while updating employee data: "+e);
            throw new exception(ErrorCodes.DATABASE_ERROR,"An error occurred while updating employee");
        }}

}


