package com.mindex.challenge.data;

import java.util.Objects;

public class Salary {
    private double salary;

    public Salary(double salary) {
        this.salary = salary;
    }

    public Salary() {};

    public double getSalary() {
        return salary;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Salary salary1 = (Salary) o;
        return Double.compare(salary, salary1.salary) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(salary);
    }
}
