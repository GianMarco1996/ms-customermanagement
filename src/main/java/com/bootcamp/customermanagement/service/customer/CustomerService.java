package com.bootcamp.customermanagement.service.customer;

import com.bootcamp.customermanagement.model.documents.customer.Customer;
import com.bootcamp.customermanagement.model.dto.CustomerDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CustomerService {
    Flux<CustomerDTO> getCustomers();
    Mono<CustomerDTO> getCustomer(String id);
    Mono<Customer> registerCustomer(Mono<CustomerDTO> customer);
    Mono<Customer> updateCustomer(String id, Mono<CustomerDTO> customer);
    Mono<Void> removeCustomer(String id);
}