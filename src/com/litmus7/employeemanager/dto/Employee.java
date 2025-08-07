
package com.litmus7.employeemanager.dto;
import java.time.LocalDate;

public class Employee {
private int  id;
private String firstname;
private String lastname;
private String mobile;
private String email;
private LocalDate joiningdate ;
private boolean status;

public Employee(int id,String firstname,String lastname,String mobile,String email,LocalDate joiningdate,boolean status){
	this.id=id;
	this.firstname=firstname;
	this.lastname=lastname;	
	this.mobile=mobile;
	this.email=email;
	this.joiningdate=joiningdate;
	this.status=status;
}

public Integer getId() {
    return this.id;
}

public String getFirstName() {
    return this.firstname;
}

public String getLastName() {
    return this.lastname;
}

public String getMobileNumber() {
    return this.mobile;
}

public String getEmail() {
    return this.email;
}

public LocalDate getDateofJoin() {
    return this.joiningdate;
}

public boolean isActive() {
    return this.status;
}
}
