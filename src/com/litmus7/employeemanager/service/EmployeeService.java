package com.litmus7.employeemanager.service;

import com.litmus7.employeemanager.dao.employeeDAO;
import com.litmus7.employeemanager.dto.Employee;
import com.litmus7.employeemanager.constants.exception;

import java.util.List;


public class EmployeeService {

    private final employeeDAO dao = new employeeDAO();
    
    public int createEmployee(Employee employee) throws exception {
		int rowInserted = dao.saveEmployee(employee);
		if (rowInserted==0) throw new exception("failed to insert employee");
		return rowInserted;
	}
    
    public List<Employee> getAllEmployees() throws exception {
		List<Employee> employees = dao.selectAllEmployees();
		if (employees.isEmpty()) throw new exception("failed to retrieve employee list");
		return employees;
	}
	
    public Employee getEmployeeData(int id) throws exception {
		Employee employee =dao.selectEmployeeById(id);
		if (employee == null) throw new exception("failed to retrieve employee details");
		return employee;
	}

	public int deleteEmployeeData(int id) throws exception {
		int rowsDeleted = dao.deleteEmployeeById(id);
		if (rowsDeleted==0) throw new exception("failed to delete employee");
		return rowsDeleted;
	}
	
	public int updateEmployee(Employee employee) throws exception {
		int rowsUpdated = dao.updateEmployee(employee);
		if (rowsUpdated==0) throw new exception("failed to delete employee");
		return rowsUpdated;
	}

}