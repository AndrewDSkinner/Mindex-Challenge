package com.mindex.challenge.data;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class SalaryTest {

    @Test
    public void testConstructorAndGetSalary() {
        double expectedSalary = 50000.0;
        Salary salary = new Salary(expectedSalary);

        assertEquals(expectedSalary, salary.getSalary(), 0.001);
    }

    @Test
    public void testDefaultConstructorAndGetSalary() {
        Salary salary = new Salary();

        assertEquals(0.0, salary.getSalary(), 0.001);
    }

    @Test
    public void testEqualsAndHashCode() {
        double salaryValue = 60000.0;
        Salary salary1 = new Salary(salaryValue);
        Salary salary2 = new Salary(salaryValue);
        Salary salary3 = new Salary(70000.0);

        // Test equals method
        assertEquals(salary1, salary2);
        assertNotEquals(salary1, salary3);

        // Test hashCode method
        assertEquals(salary1.hashCode(), salary2.hashCode());
        assertNotEquals(salary1.hashCode(), salary3.hashCode());
    }

    @Test
    public void testEqualsWithDifferentObject() {
        Salary salary = new Salary(80000.0);

        assertNotEquals(salary, "NotASalaryObject");
    }
}
