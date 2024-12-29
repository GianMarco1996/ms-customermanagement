package com.bootcamp.customermanagement.repository.person;

import com.bootcamp.customermanagement.model.documents.person.Person;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface PersonRepository extends ReactiveMongoRepository<Person, String> {

    Flux<Person> findPersonByCustomerId(String customerId);
}