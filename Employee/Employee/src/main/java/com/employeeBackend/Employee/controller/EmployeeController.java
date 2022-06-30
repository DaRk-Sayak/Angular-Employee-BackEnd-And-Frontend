package com.employeeBackend.Employee.controller;

import com.employeeBackend.Employee.Exception.ResourceNotFoundException;
import com.employeeBackend.Employee.model.Employee;
import com.employeeBackend.Employee.repository.EmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/v1")
public class EmployeeController {
    @Autowired
    EmployeeRepo employeeRepo;

    @PostMapping("/employees")
    public Employee addemployee(@RequestBody  Employee employee){
        return employeeRepo.save(employee);
    }

    @GetMapping("/employees")
    public List<Employee> getemployee(){
        return employeeRepo.findAll();
    }

    @GetMapping("/employees/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable int id){
        Employee employee=employeeRepo.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Employee Dont Exist With This ID:- "+id));
        return ResponseEntity.ok(employee);
    }

    @PutMapping("/employees/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable int id,@RequestBody Employee employeeDetails){
        Employee employee=employeeRepo.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Employee Dont Exist With This ID:- "+id));
        employee.setFirstName(employeeDetails.getFirstName());
        employee.setLastName(employeeDetails.getLastName());
        employee.setEmailId(employeeDetails.getEmailId());
        Employee updateEmp=employeeRepo.save(employee);
        return ResponseEntity.ok(updateEmp);
    }

    @DeleteMapping("/employees/{id}")
    public ResponseEntity<Map<String,Boolean>>deleteEmployee(@PathVariable int id){
        Employee employee=employeeRepo.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Employee Dont Exist With This ID:- "+id));
        employeeRepo.delete(employee);
        Map<String,Boolean> response=new HashMap<>();
        response.put("deleted",Boolean.TRUE);
        return ResponseEntity.ok(response);
    }
}
