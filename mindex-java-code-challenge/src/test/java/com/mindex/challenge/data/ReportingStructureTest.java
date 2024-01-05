package com.mindex.challenge.data;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ReportingStructureTest {

    @Test
    public void testGetEmployee() {
        Employee employee = new Employee();
        employee.setFirstName("John");
        employee.setLastName("Doe");
        employee.setPosition("Software Engineer");

        ReportingStructure reportingStructure = new ReportingStructure(employee);

        assertEquals(employee, reportingStructure.getEmployee());
    }

    @Test
    public void testGetNumberOfReports_NoDirectReports() {
        Employee employee = new Employee();
        employee.setFirstName("John");
        employee.setLastName("Doe");
        employee.setPosition("Software Engineer");

        ReportingStructure reportingStructure = new ReportingStructure(employee);

        assertEquals(0, reportingStructure.getNumberOfReports());
    }

    @Test
    public void testGetNumberOfReports_SingleLevelDirectReports() {
        Employee directReport1 = new Employee();
        directReport1.setFirstName("Alice");
        directReport1.setLastName("Staley");
        directReport1.setPosition("Developer");

        Employee directReport2 = new Employee();
        directReport2.setFirstName("Bob");
        directReport2.setLastName("Builder");
        directReport2.setPosition("Tester");

        Employee manager = new Employee();
        manager.setFirstName("John");
        manager.setLastName("Bonham");
        manager.setPosition("Software Engineer");
        manager.setDirectReports(List.of(directReport1, directReport2));

        ReportingStructure reportingStructure = new ReportingStructure(manager);

        assertEquals(2, reportingStructure.getNumberOfReports());
    }

    @Test
    public void testGetNumberOfReports_MultiLevelDirectReports() {
        Employee directReport1 = new Employee();
        directReport1.setFirstName("Alice");
        directReport1.setLastName("Wonderland");
        directReport1.setPosition("Developer");

        Employee directReport2 = new Employee();
        directReport2.setFirstName("Bob");
        directReport2.setLastName("Builder");
        directReport2.setPosition("Tester");

        Employee manager = new Employee();
        manager.setFirstName("John");
        manager.setLastName("Doe");
        manager.setPosition("Software Engineer");
        manager.setDirectReports(List.of(directReport1, directReport2));

        Employee grandDirectReport1 = new Employee();
        grandDirectReport1.setFirstName("Eve");
        grandDirectReport1.setLastName("EveLast");
        grandDirectReport1.setPosition("QA Engineer");

        Employee grandDirectReport2 = new Employee();
        grandDirectReport2.setFirstName("Charlie");
        grandDirectReport2.setLastName("Chocolate");
        grandDirectReport2.setPosition("Designer");

        directReport1.setDirectReports(List.of(grandDirectReport1, grandDirectReport2));

        ReportingStructure reportingStructure = new ReportingStructure(manager);

        // John Doe -> Alice (1) -> Eve (2), Charlie (3) | Bob (4)
        assertEquals(4, reportingStructure.getNumberOfReports());
    }
}