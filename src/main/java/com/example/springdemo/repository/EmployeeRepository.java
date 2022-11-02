package com.example.springdemo.repository;

import com.example.springdemo.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    Optional<Employee> findEmployeeById(int Id);

    //Optional<Employee> findEmployeeByUsername(String username);
}