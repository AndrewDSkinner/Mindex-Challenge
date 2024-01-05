package com.mindex.challenge.dao;

import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CompensationRepository extends MongoRepository<Compensation, String> {
    // returns an Optional of Compensation
    Optional<Compensation> findByEmployee(Employee employee);
}
