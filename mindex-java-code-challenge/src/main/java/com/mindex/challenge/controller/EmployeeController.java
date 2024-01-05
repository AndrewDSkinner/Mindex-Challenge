package com.mindex.challenge.controller;

import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.data.Salary;
import com.mindex.challenge.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmployeeController {
    private static final Logger LOG = LoggerFactory.getLogger(EmployeeController.class);

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/employee")
    public Employee create(@RequestBody Employee employee) {
        LOG.debug("Received employee create request for [{}]", employee);

        return employeeService.create(employee);
    }

    @GetMapping("/employee/{id}")
    public Employee read(@PathVariable String id) {
        LOG.debug("Received employee read request for id [{}]", id);

        return employeeService.read(id);
    }

    @PutMapping("/employee/{id}")
    public Employee update(@PathVariable String id, @RequestBody Employee employee) {
        LOG.debug("Received employee update request for id [{}] and employee [{}]", id, employee);

        employee.setEmployeeId(id);
        return employeeService.update(employee);
    }

    @GetMapping("/employee/reporting-structure/{id}")
    public ReportingStructure getReportingStructure(@PathVariable String id) {
        // Log the request for employee reporting structure with the specified id
        LOG.debug("Received employee reporting structure update request for id [{}]", id);

        // Send the request to the employeeService to retrieve the reporting structure
        return employeeService.getReportingStructure(id);
    }

    @PostMapping("/employee/compensation/{id}")
    public ResponseEntity<Compensation> createCompensation(@PathVariable String id, @RequestBody Salary salary) {
        // Log the request for creating employee compensation with the specified id and salary
        LOG.debug("Received employee compensation creation request of [{}] for id [{}]", salary, id);

        // Send the request to the employeeService to create compensation
        return ResponseEntity.status(HttpStatus.CREATED).body(employeeService.createCompensation(id, salary));
    }

    @GetMapping("/employee/compensation/{id}")
    public Compensation readCompensation(@PathVariable String id) {
        // Log the request for reading employee compensation with the specified id
        LOG.debug("Received employee compensation read request for id [{}]", id);

        // Send the request to the employeeService to retrieve compensation data
        return employeeService.getCompensationByEmployee(id);
    }
}
