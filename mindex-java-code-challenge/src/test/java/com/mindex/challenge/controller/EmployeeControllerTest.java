package com.mindex.challenge.controller;

import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.data.Salary;
import com.mindex.challenge.service.EmployeeService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Date;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class EmployeeControllerTest {

    @Mock
    private EmployeeService employeeService;

    @InjectMocks
    private EmployeeController employeeController;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetReportingStructure() {
        String employeeId = "123";
        Employee employee = new Employee();
        employee.setFirstName("John");
        employee.setLastName("Doe");
        employee.setPosition("Software Engineer");
        ReportingStructure reportingStructure = new ReportingStructure(employee);

        when(employeeService.getReportingStructure(employeeId)).thenReturn(reportingStructure);

        ReportingStructure result = employeeController.getReportingStructure(employeeId);

        assertEquals(reportingStructure, result);
        verify(employeeService, times(1)).getReportingStructure(employeeId);
        verifyNoMoreInteractions(employeeService);
    }

    @Test
    public void testCreateCompensation() {
        String employeeId = UUID.randomUUID().toString();
        Salary salary = new Salary(50000.00);
        Employee employee = new Employee();
        employee.setFirstName("John");
        employee.setLastName("Doe");
        employee.setPosition("Software Engineer");
        Compensation compensation = new Compensation(employee ,salary, new Date());

        when(employeeService.createCompensation(employeeId, salary)).thenReturn(compensation);

        Compensation result = employeeController.createCompensation(employeeId, salary).getBody();

        assertEquals(compensation, result);
        verify(employeeService, times(1)).createCompensation(employeeId, salary);
        verifyNoMoreInteractions(employeeService);
    }

    @Test
    public void testReadCompensation() {
        String employeeId = UUID.randomUUID().toString();
        Salary salary = new Salary(50000.00);
        Employee employee = new Employee();
        employee.setFirstName("John");
        employee.setLastName("Doe");
        employee.setPosition("Software Engineer");
        Compensation compensation = new Compensation(employee , salary, new Date());

        when(employeeService.getCompensationByEmployee(employeeId)).thenReturn(compensation);

        Compensation result = employeeController.readCompensation(employeeId);

        assertEquals(compensation, result);
        verify(employeeService, times(1)).getCompensationByEmployee(employeeId);
        verifyNoMoreInteractions(employeeService);
    }
}