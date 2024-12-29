package com.bootcamp.customermanagement.repository.customer;

import com.bootcamp.customermanagement.model.documents.customer.Customer;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends ReactiveMongoRepository<Customer, String> {
}