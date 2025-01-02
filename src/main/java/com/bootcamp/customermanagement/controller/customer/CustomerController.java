package com.bootcamp.customermanagement.controller.customer;

import com.bootcamp.customermanagement.api.CustomerApi;
import com.bootcamp.customermanagement.mapper.customer.CustomerMapper;
import com.bootcamp.customermanagement.model.CustomerRequest;
import com.bootcamp.customermanagement.model.CustomerResponse;
import com.bootcamp.customermanagement.service.customer.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class CustomerController implements CustomerApi {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerMapper customerMapper;

    @Override
    public Mono<ResponseEntity<CustomerResponse>> getCustomer(String id, ServerWebExchange exchange) {
        return customerService.getCustomer(id)
                .map(customer -> customerMapper.toModel(customer))
                .map(ResponseEntity::ok);
    }

    @Override
    public Mono<ResponseEntity<Flux<CustomerResponse>>> getCustomers(ServerWebExchange exchange) {
        return Mono.just(ResponseEntity.ok().body(customerService.getCustomers()
                .map(customer -> customerMapper.toModel(customer))));
    }

    @Override
    public Mono<ResponseEntity<Object>> registerCustomer(Mono<CustomerRequest> customerRequest, ServerWebExchange exchange) {
        return customerService.registerCustomer(
                        customerRequest.map(customer -> customerMapper.toDocument(customer)))
                .map(ResponseEntity::ok);
                //.onErrorResume(IllegalArgumentException.class, e -> Mono.just(ResponseEntity.badRequest().body(e)));
    }

    @Override
    public Mono<ResponseEntity<Void>> removeCustomer(String id, ServerWebExchange exchange) {
        return customerService.removeCustomer(id)
                .then(Mono.just(new ResponseEntity<Void>(HttpStatus.NO_CONTENT)))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Override
    public Mono<ResponseEntity<Object>> updateCustomer(String id, Mono<CustomerRequest> customerRequest, ServerWebExchange exchange) {
        return customerService.updateCustomer(id,
                        customerRequest.map(customer -> customerMapper.toDocument(customer)))
                .map(ResponseEntity::ok);
    }
}