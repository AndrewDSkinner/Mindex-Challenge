package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.CompensationRepository;
import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.data.Salary;
import com.mindex.challenge.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private static final Logger LOG = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private CompensationRepository compensationRepository;

    @Override
    public Employee create(Employee employee) {
        LOG.debug("Creating employee [{}]", employee);

        employee.setEmployeeId(UUID.randomUUID().toString());
        employeeRepository.insert(employee);

        return employee;
    }

    @Override
    public Employee read(String id) {
        LOG.debug("Retrieving employee with id [{}]", id);

        Employee employee = employeeRepository.findByEmployeeId(id);

        if (employee == null) {
            throw new RuntimeException("Invalid employeeId: " + id);
        }

        return employee;
    }

    @Override
    public Employee update(Employee employee) {
        LOG.debug("Updating employee [{}]", employee);

        return employeeRepository.save(employee);
    }
    @Override
    public ReportingStructure getReportingStructure(String id) {
        // Log the retrieval of reporting structure for the employee with the specified id
        LOG.debug("Retrieving reporting structure for employee with id [{}]", id);

        // Read the employee information from the repository using the provided id
        Employee employee = read(id);

        // Create and return a new ReportingStructure object based on the retrieved employee
        return new ReportingStructure(employee);
    }

    @Override
    public Compensation createCompensation(String id, Salary salary) {
        // Log the creation of compensation for the employee with the specified id
        LOG.debug("Creating compensation for id [{}]", id);

        // Read the employee information from the repository using the provided id
        Employee employee = read(id);

        // If repo already contains compensation for this ID, return that compensation
        if(compensationRepository.findByEmployee(employee).isPresent()) {
            return getCompensationByEmployee(id);
        }

        // Create a new Compensation object with the employee, provided salary, and the current date
        Compensation compensation = new Compensation(employee, salary, new Date());

        // Insert the created compensation into the compensation repository
        compensationRepository.insert(compensation);

        // Return the created compensation object
        return compensation;
    }

    @Override
    public Compensation getCompensationByEmployee(String id) {
        // Log the retrieval of compensation information with the specified id
        LOG.debug("Retrieving compensation with id [{}]", id);

        // Read the employee information from the repository using the provided id
        Employee employee = read(id);

        // Retrieve the compensation associated with the employee from the compensation repository
        Compensation compensation = compensationRepository.findByEmployee(employee).orElse(null);

        // If no compensation is found, throw a RuntimeException with an error message
        if (compensation == null) {
            throw new RuntimeException("Invalid employeeId: " + id);
        }

        // Return the retrieved compensation object
        return compensation;
    }

}
