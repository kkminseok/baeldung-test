package com.my.springwebfluxguide.employee;

import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class EmployeeWebClient {

    WebClient client = WebClient.create("http://localhost:8080");

    public void getEmployeeMono() {
        Mono<Employee> employeeMono = client.get()
                .uri("/employee/{id}", 1)
                .retrieve()
                .bodyToMono(Employee.class);
        employeeMono.subscribe(System.out::println);
    }
    
    public void getEmployeeFlux() {
        Flux<Employee> employeeFlux = client.get()
                .uri("/employee")
                .retrieve()
                .bodyToFlux(Employee.class);
        employeeFlux.subscribe(System.out::println);
    }
}
