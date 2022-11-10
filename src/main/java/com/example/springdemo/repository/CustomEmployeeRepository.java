package com.example.springdemo.repository;

import com.example.springdemo.model.Employee;
//import com.example.springdemo.service.EmployeeService;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomEmployeeRepository {

    Optional<Employee> findEmployeeById(String Id);

    //Optional<Employee> findEmployeeById(int Id);

    //Optional<Employee> findEmployeeByUsername(String username);
}

//extends JpaRepository<Employee, Integer>