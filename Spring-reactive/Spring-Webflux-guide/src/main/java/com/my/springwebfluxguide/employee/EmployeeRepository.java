package com.my.springwebfluxguide.employee;

import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public interface EmployeeRepository {
    Mono<Employee> findEmployeeById(int id);

    Flux<Employee> getAllEmployees();
}
