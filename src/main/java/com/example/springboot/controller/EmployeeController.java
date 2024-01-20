package com.example.springboot.controller;

import com.example.springboot.model.Employee;
import com.example.springboot.service.EmployeeService;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {
    private EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Employee createEmployee(@RequestBody Employee employee){
        return employeeService.saveEmployee(employee);
    }
    
    @GetMapping
    public List<Employee> getAllEmployees(){
    	return employeeService.getAllEmployees();
    }
    
    @GetMapping("{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable("id") long employeeId){
    	return employeeService.getEmployeeById(employeeId)
    			.map(ResponseEntity::ok)
    			.orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    @PutMapping("{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable("id") long employeeId,
    											   @RequestBody Employee employee){
    	return employeeService.getEmployeeById(employeeId)
    			.map(savedEmployee -> {
    				
    				savedEmployee.setFirstName(employee.getFirstName());
    				savedEmployee.setLastName(employee.getLastName());
    				savedEmployee.setEmail(employee.getEmail());
    				
    				Employee updatedEmployee = employeeService.updateEmployee(savedEmployee);
    				return new ResponseEntity<>(updatedEmployee, HttpStatus.OK);
    				
    			})
    			.orElseGet(() -> ResponseEntity.notFound().build());
    }
}
