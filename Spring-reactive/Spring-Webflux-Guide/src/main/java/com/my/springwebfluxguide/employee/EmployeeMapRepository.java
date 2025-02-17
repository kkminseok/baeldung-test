package com.my.springwebfluxguide.employee;

import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class EmployeeMapRepository implements EmployeeRepository{

    @Override
    public Mono<Employee> findEmployeeById(int id) {
        return Mono.just(new Employee(1, "minseok")) ;
    }

    @Override
    public Flux<Employee> getAllEmployees() {
        return Flux.just(new Employee(1, "minseok"), new Employee(2, "chill guy"));
    }

    @Override
    public Mono<Employee> updateEmployee(Employee employee) {
        return Mono.just(new Employee(1, "minseok updated"));
    }
}
