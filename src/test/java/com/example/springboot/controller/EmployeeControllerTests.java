package com.example.springboot.controller;

import com.example.springboot.model.Employee;
import com.example.springboot.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@WebMvcTest
public class EmployeeControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;

    @Autowired
    private ObjectMapper objectMapper;

    //JUnit test for create employee REST API
    @Test
    public void givenEmployeeObject_whenCreateEmployee_thenReturnSavedEmployee() throws Exception {
        // given
        Employee employee = Employee.builder()
                .firstName("Yerkebulan")
                .lastName("Yessenali")
                .email("yerkebulan@gmail.com")
                .build();
        BDDMockito.given(employeeService.saveEmployee(ArgumentMatchers.any(Employee.class)))
                .willAnswer((invocation) -> invocation.getArgument(0));

        // when
        ResultActions response = mockMvc.perform(post("/api/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(employee)));
        // then
        response.andDo(print())
        		.andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName", is(employee.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(employee.getLastName())))
                .andExpect(jsonPath("$.email", is(employee.getEmail())));

    }
    
    // JUnit test for get all employees REST API
    @Test
    public void givenListOfEmployees_whenGetAllEmployees_thenReturnEmployeesList() throws Exception {
    	// given
    	List<Employee> listOfEmployees = new ArrayList<>();
    	listOfEmployees.add(Employee.builder().firstName("Yerkebulan").lastName("Yessenali").email("yerkebulan@gmail.com").build());
    	listOfEmployees.add(Employee.builder().firstName("Tony").lastName("Stark").email("tony@gmail.com").build());
    	BDDMockito.given(employeeService.getAllEmployees()).willReturn(listOfEmployees);
    	// when
    	ResultActions response = mockMvc.perform(get("/api/employees"));
    	// then
    	response.andExpect(status().isOk())
    			.andDo(print())
    			.andExpect(jsonPath("$.size()", 
    					is(listOfEmployees.size())));
    }
    
    // positive scenario - valid employee id
    // JUnit test for GET employee by id REST API
    @Test
    public void givenEmployeeId_whenGetEmployeeById_thenReturnEmployeeObject() throws Exception {
    	// given
    	long employeeId = 1L;
        Employee employee = Employee.builder()
                .firstName("Yerkebulan")
                .lastName("Yessenali")
                .email("yerkebulan@gmail.com")
                .build();
        BDDMockito.given(employeeService.getEmployeeById(employeeId)).willReturn(Optional.of(employee));
    	// when
        ResultActions response = mockMvc.perform(get("/api/employees/{id}", employeeId));
    	// then
        response.andExpect(status().isOk())
        		.andDo(print())
        		.andExpect(jsonPath("$.firstName", is(employee.getFirstName())))
        		.andExpect(jsonPath("$.lastName", is(employee.getLastName())))
        		.andExpect(jsonPath("$.email", is(employee.getEmail())));
    }
    
    // negative scenario - invalid employee id
    // JUnit test for GET employee by id REST API
    @Test
    public void givenInvalidEmployeeId_whenGetEmployeeById_thenReturnEmpty() throws Exception {
    	// given
    	long employeeId = 1L;
        Employee employee = Employee.builder()
                .firstName("Yerkebulan")
                .lastName("Yessenali")
                .email("yerkebulan@gmail.com")
                .build();
        BDDMockito.given(employeeService.getEmployeeById(employeeId)).willReturn(Optional.empty());
    	// when
        ResultActions response = mockMvc.perform(get("/api/employees/{id}", employeeId));
    	// then
        response.andExpect(status().isNotFound())
        		.andDo(print());
    }
}
