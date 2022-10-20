package com.example.springdemo.controller;

import com.example.springdemo.model.Employee;
import com.example.springdemo.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class EmployeeController {
    @Autowired
    private EmployeeService service;

    @GetMapping("/search")
    public String add(Model model) {
        //List<Employee> listemployee = service.listAll();
        model.addAttribute("employee", new Employee());
        return "employee_page";
    }


    @PostMapping("/search")
    public String doSearchEmployee(@ModelAttribute("employeeSearchFormData") Employee formData, Model model) {
        Employee emp = service.get(formData.getId());
        model.addAttribute("employee", emp);
        return "employee_page";
    }

    /*@GetMapping("/all")
    public String add(Model model) {
        List<Employee> listemployee = service.listAll();
        model.addAttribute("employee", new Employee());
        return "employee_page";
    }*/



}

