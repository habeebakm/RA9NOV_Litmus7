package com.litmus7.employeemanager.service;

import com.litmus7.employeemanager.dao.employeeDAO;
import com.litmus7.employeemanager.dto.Employee;
import com.litmus7.employeemanager.constants.exception;
import com.litmus7.employeemanager.constants.ErrorCodes;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.util.List;


public class EmployeeService {
	
	private static final Logger logger = LogManager.getLogger(EmployeeService.class);
    private final employeeDAO dao = new employeeDAO();
    
    public int createEmployee(Employee employee) throws exception {
    	 logger.debug("Attempting to create employee: " + employee.getId());
		int rowInserted = dao.saveEmployee(employee);
		if (rowInserted==0) {
			logger.error("Failed to create employee: " + employee.getId());
			throw new exception(ErrorCodes.NO_ROWS_INSERTED, "Failed to insert employee, No rows affected.");
		}
		logger.info("Employee created successfully: " + employee.getId());
		return rowInserted;
	}
    
    public List<Employee> getAllEmployees() throws exception {
    	logger.debug("Fetching employee list");
		List<Employee> employees = dao.selectAllEmployees();
		if (employees.isEmpty())
			throw new exception(ErrorCodes.EMPLOYEE_NOT_FOUND, "No employees found in the database.");
		logger.info("Employee list retrieved successfully");
		return employees;
	}
	
    public Employee getEmployeeData(int id) throws exception {
    	logger.debug("Fetching employee data with ID"+id);
		Employee employee =dao.selectEmployeeById(id);
		if (employee == null) {
			logger.warn("Employee not found with ID " + id);
			throw new exception(ErrorCodes.EMPLOYEE_NOT_FOUND, "Employee with ID " + id + " not found.");
		}
		 logger.info("Employee data retrieved successfully with ID " + id);

		return employee;
	}

	public int deleteEmployeeData(int id) throws exception {
		logger.debug("deleting employee data with ID"+id);
		int rowsDeleted = dao.deleteEmployeeById(id);
		if (rowsDeleted==0) {
			logger.warn("No employee found to delete with ID " + id);
			throw new exception(ErrorCodes.NO_ROWS_DELETED,"No employee found to delete with ID " + id);
		}
		logger.info("Employee deleted successfully with ID " + id);
		return rowsDeleted;
	}
	
	public int updateEmployee(Employee employee) throws exception {
		logger.debug("updating employee data with ID "+employee.getId());
		int rowsUpdated = dao.updateEmployee(employee);
		if (rowsUpdated==0) {
			logger.warn("No employee found to update with ID "+employee.getId());
			throw new exception(ErrorCodes.NO_ROWS_UPDATED,"No employee found to update with ID " + employee.getId());
		}
		logger.info("Employee update successfully with ID " +employee.getId());
		return rowsUpdated;
	}

}