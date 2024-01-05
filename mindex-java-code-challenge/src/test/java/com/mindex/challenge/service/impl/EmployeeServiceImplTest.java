package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.CompensationRepository;
import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.data.Salary;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EmployeeServiceImplTest {

    private String employeeUrl;
    private String employeeIdUrl;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    @Mock
    private EmployeeRepository employeeRepository;
    @Mock
    private CompensationRepository compensationRepository;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Before
    public void setup() {
        employeeUrl = "http://localhost:" + port + "/employee";
        employeeIdUrl = "http://localhost:" + port + "/employee/{id}";
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreateReadUpdate() {
        Employee testEmployee = new Employee();
        testEmployee.setFirstName("John");
        testEmployee.setLastName("Doe");
        testEmployee.setDepartment("Engineering");
        testEmployee.setPosition("Developer");

        // Create checks
        Employee createdEmployee = restTemplate.postForEntity(employeeUrl, testEmployee, Employee.class).getBody();

        assertNotNull(createdEmployee.getEmployeeId());
        assertEmployeeEquivalence(testEmployee, createdEmployee);


        // Read checks
        Employee readEmployee = restTemplate.getForEntity(employeeIdUrl, Employee.class, createdEmployee.getEmployeeId()).getBody();
        assertEquals(createdEmployee.getEmployeeId(), readEmployee.getEmployeeId());
        assertEmployeeEquivalence(createdEmployee, readEmployee);


        // Update checks
        readEmployee.setPosition("Development Manager");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Employee updatedEmployee =
                restTemplate.exchange(employeeIdUrl,
                        HttpMethod.PUT,
                        new HttpEntity<Employee>(readEmployee, headers),
                        Employee.class,
                        readEmployee.getEmployeeId()).getBody();

        assertEmployeeEquivalence(readEmployee, updatedEmployee);
    }

    private static void assertEmployeeEquivalence(Employee expected, Employee actual) {
        assertEquals(expected.getFirstName(), actual.getFirstName());
        assertEquals(expected.getLastName(), actual.getLastName());
        assertEquals(expected.getDepartment(), actual.getDepartment());
        assertEquals(expected.getPosition(), actual.getPosition());
    }

    @Test
    public void testGetReportingStructure() {
        String employeeId = UUID.randomUUID().toString();
        Employee employee = new Employee();
        employee.setFirstName("John");
        employee.setLastName("Doe");
        employee.setPosition("Software Engineer");
        ReportingStructure expectedReportingStructure = new ReportingStructure(employee);

        when(employeeRepository.findByEmployeeId(anyString())).thenReturn(employee);

        ReportingStructure result = employeeService.getReportingStructure(employeeId);

        assertEquals(expectedReportingStructure, result);
        verify(employeeRepository, times(1)).findByEmployeeId(employeeId);
        verifyNoMoreInteractions(employeeRepository);
    }

    @Test
    public void testCreateCompensation() {
        String employeeId = UUID.randomUUID().toString();
        Employee employee = new Employee();
        employee.setFirstName("John");
        employee.setLastName("Doe");
        employee.setPosition("Software Engineer");
        Compensation expectedCompensation = new Compensation(employee, new Salary(100000.0), new Date());

        when(employeeRepository.findByEmployeeId(employeeId)).thenReturn(employee);
        when(compensationRepository.insert(expectedCompensation)).thenReturn(expectedCompensation);

        Compensation result = employeeService.createCompensation(employeeId, new Salary(100000.0));

        assertEquals(expectedCompensation.getEmployee(), result.getEmployee());
        assertEquals(expectedCompensation.getSalary(), result.getSalary());
        verify(employeeRepository, times(1)).findByEmployeeId(employeeId);
        verify(compensationRepository, times(1)).insert(any(Compensation.class));
        verifyNoMoreInteractions(employeeRepository, compensationRepository);
    }

    @Test
    public void testGetCompensationByEmployee() {
        String employeeId = UUID.randomUUID().toString();
        Employee employee = new Employee();
        employee.setFirstName("John");
        employee.setLastName("Doe");
        employee.setPosition("Software Engineer");
        Compensation expectedCompensation = new Compensation(employee, new Salary(90000.0), new Date());

        when(employeeRepository.findByEmployeeId(employeeId)).thenReturn(employee);
        when(compensationRepository.findByEmployee(employee)).thenReturn(Optional.of(expectedCompensation));

        Compensation result = employeeService.getCompensationByEmployee(employeeId);

        assertEquals(expectedCompensation, result);
        verify(employeeRepository, times(1)).findByEmployeeId(employeeId);
        verify(compensationRepository, times(1)).findByEmployee(employee);
        verifyNoMoreInteractions(employeeRepository, compensationRepository);
    }
}
