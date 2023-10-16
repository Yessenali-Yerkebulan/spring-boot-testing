package com.example.springboot.repository;

import com.example.springboot.model.Employee;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

@DataJpaTest
public class EmployeeRepositoryTests {
    @Autowired
    private EmployeeRepository employeeRepository;

    //JUnit test for save employee operation
    @Test
    @DisplayName("JUnit test for save employee operation")
    public void givenEmployeeObject_whenSave_thenReturnSavedEmployee(){
        //given - precondition or setup
        Employee employee = Employee.builder()
                .firstName("Yerkebulan")
                .lastName("Yessenali")
                .email("yerkebulan@gmail.com")
                .build();


        //when - action or the behavior that we are going test

        Employee savedEmployee = employeeRepository.save(employee);
        //then - verify the output
        assertThat(savedEmployee).isNotNull();
        assertThat(savedEmployee.getId()).isGreaterThan(0);
    }

    // JUnit test for get all employees operation
    @Test
    @DisplayName("JUnit test for get all employees operation")
    public void givenEmployeesList_whenFindAll_thenEmployeesList(){
        // given - precondition or setup
        Employee employee1 = Employee.builder()
                .firstName("Yerkebulan")
                .lastName("Yessenali")
                .email("yerkebulan@gmail.com")
                .build();

        Employee employee2 = Employee.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john@gmail.com")
                .build();

        employeeRepository.save(employee1);
        employeeRepository.save(employee2);

        //when - action or the behavior that we are going test
        List<Employee> employeeList = employeeRepository.findAll();

        //then - verify the output
        assertThat(employeeList).isNotNull();
        assertThat(employeeList.size()).isEqualTo(2);
    }

    //JUnit test for get employee by id operation
    @Test
    @DisplayName("JUnit test for get employee by id operation")
    public void givenEmployeeObject_whenFindById_thenReturnEmployeeObject(){
        // given - precondition or setup
        Employee employee = Employee.builder()
                .firstName("Yerkebulan")
                .lastName("Yessenali")
                .email("yerkebulan@gmail.com")
                .build();
        employeeRepository.save(employee);
        //when - action or the behavior that we are going test
        Employee employeeDB = employeeRepository.findById(employee.getId()).get();
        //then - verify the output
        assertThat(employeeDB).isNotNull();
    }

    //JUnit test for get employee by email operation
    @Test
    @DisplayName("JUnit test for get employee by email operation")
    public void givenEmployeeEmail_whenFindByEmail_thenReturnEmployeeObject(){
        // given - precondition or setup
        Employee employee = Employee.builder()
                .firstName("Yerkebulan")
                .lastName("Yessenali")
                .email("yerkebulan@gmail.com")
                .build();
        employeeRepository.save(employee);
        //when - action or the behavior that we are going test
        Employee employeeDB = employeeRepository.findByEmail(employee.getEmail()).get();
        //then - verify the output
        assertThat(employeeDB).isNotNull();
    }
}
