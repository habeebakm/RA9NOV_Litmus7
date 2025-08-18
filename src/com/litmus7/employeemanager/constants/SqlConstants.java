package com.litmus7.employeemanager.constants;

public class SqlConstants {

    private SqlConstants() {}
    
    public static final String name_regex = "^[A-za-z\\s'-]{2,50}$";
    public static final String mobilenumber_regex = "^[1-9][0-9]{9}$";
    public static final String email_regex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.(com|in|org|net)$";


    public static final String insert_employee =
        "INSERT INTO employee (id, first_name, last_name, mobile_number, email, joining_date, status) VALUES (?, ?, ?, ?, ?, ?, ?)";

    public static final String select_all_employees =
        "SELECT id, first_name, last_name, mobile_number, email, joining_date, status FROM employee";
    

    public static final String select_employee =
        "SELECT id, first_name, last_name, mobile_number, email, joining_date, status FROM employee WHERE id = ?";

    public static final String delete_employee =
        "DELETE FROM employee WHERE id = ?";

    public static final String update_employee =
        "UPDATE employee SET first_name=?, last_name=?, mobile_number=?, email=?, joining_date=?, status=? WHERE id=?";
}
