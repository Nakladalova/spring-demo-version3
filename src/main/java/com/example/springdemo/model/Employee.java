package com.example.springdemo.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;
import javax.persistence.Id;

@Entity
@Table(name= "employees")
public class Employee{

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
    private String ename;
    private int mobile;
    private int salary;
    private String username;
    private String employee_id;

    public Employee() {
    }
    public Employee(int id, String ename, int mobile, int salary) {
        this.id = id;
        this.ename = ename;
        this.mobile = mobile;
        this.salary = salary;
        this.username = username;
        this.employee_id= employee_id;
    }


    public String getEname() {
        return ename;
    }
    public void setEname(String ename) {
        this.ename = ename;
    }
    public int getMobile() {
        return mobile;
    }
    public void setMobile(int mobile) {
        this.mobile = mobile;
    }
    public int getSalary() {
        return salary;
    }
    public void setSalary(int salary) {
        this.salary = salary;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
    public String getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(String employee_id) {
        this.employee_id = employee_id;
    }

    @Override
    public String toString() {
        return "Employee [id=" + id + ", ename=" + ename + ", mobile=" + mobile + ", salary=" + salary + "]";
    }


}
