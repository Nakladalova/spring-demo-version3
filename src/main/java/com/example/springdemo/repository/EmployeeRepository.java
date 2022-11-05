package com.example.springdemo.repository;

import com.example.springdemo.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Integer>, CustomEmployeeRepository{
}
