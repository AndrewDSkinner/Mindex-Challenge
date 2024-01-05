package com.mindex.challenge.data;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class ReportingStructure {
    private Employee employee;
    private long numberOfReports;

    public ReportingStructure(Employee employee) {
        this.employee = employee;
        this.numberOfReports = createEmployeeSet();
    }

    public Employee getEmployee() {
        return employee;
    }

    public long getNumberOfReports() {
        return numberOfReports;
    }

    private long createEmployeeSet() {
        Set<Employee> uniqueEmployees = new HashSet<>();

        createEmployeeSet(employee, uniqueEmployees);

        numberOfReports = uniqueEmployees.size();

        return numberOfReports;
    }

    private void createEmployeeSet(Employee employee, Set<Employee> uniqueEmployees) {
        // if the employee is null then return
        if (employee == null) {
            return;
        }

        // If employee is not top level employee then add the current employee to the set
        if (employee != this.employee) {
            uniqueEmployees.add(employee);
        }

        // if employee has no direct reports, return
        if(employee.getDirectReports() == null) {
            return;
        }

        // Recursively calculate for each employee
        for (Employee directReport : employee.getDirectReports()) {
            createEmployeeSet(directReport, uniqueEmployees);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReportingStructure that = (ReportingStructure) o;
        return numberOfReports == that.numberOfReports && Objects.equals(employee, that.employee);
    }

    @Override
    public int hashCode() {
        return Objects.hash(employee, numberOfReports);
    }
}
