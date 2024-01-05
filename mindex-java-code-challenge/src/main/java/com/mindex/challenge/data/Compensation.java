package com.mindex.challenge.data;

import java.util.Date;
import java.util.Objects;

public class Compensation {

    private final Employee employee;
    private final Salary salary;
    private final Date effectiveDate;

    public Compensation(Employee employee, Salary salary, Date effectiveDate) {
        this.employee = employee;
        this.salary = salary;
        this.effectiveDate = effectiveDate;
    }

    public Employee getEmployee() {
        return employee;
    }

    public Salary getSalary() {
        return salary;
    }

    public Date getEffectiveDate() {
        return effectiveDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Compensation that = (Compensation) o;
        return Objects.equals(employee, that.employee) && Objects.equals(salary, that.salary) && Objects.equals(effectiveDate, that.effectiveDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(employee, salary, effectiveDate);
    }
}
