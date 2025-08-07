package com.litmus7.employeemanager.util;

import java.util.List;
import java.util.regex.Pattern;


import com.litmus7.employeemanager.dto.Employee;

public class ValidationUtil {

    public static boolean isunique(int id, List<Employee> employees) {
        if (id <= 0) return false;
        for (Employee emp : employees) {
            if (emp.getId() == id) return false;
        }
        return true;
    }

    public static boolean isnotnull(String s) {
        return s != null && !s.trim().isEmpty();
    }

    public static boolean isvalidnumber(String mobile) {
        return mobile != null && mobile.matches("^[1-9][0-9]{9}$");
    }

    public static boolean isvalidemail(String email) {
        
        return (email !=null && Pattern.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$", email));
    }
    
    public static boolean isValidDate(String date) {
        return date != null && Pattern.matches("^\\d{4}-\\d{2}-\\d{2}$", date);
    }

    public static boolean isvalidstatus(String status) {
        return status != null && (status.equalsIgnoreCase("true") || status.equalsIgnoreCase("false"));
    }
}
