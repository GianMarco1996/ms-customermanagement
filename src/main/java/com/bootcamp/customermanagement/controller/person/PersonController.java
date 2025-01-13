package com.bootcamp.customermanagement.controller.person;

import com.bootcamp.customermanagement.api.PersonApi;
import com.bootcamp.customermanagement.mapper.person.PersonMapper;
import com.bootcamp.customermanagement.model.PersonRequest;
import com.bootcamp.customermanagement.model.PersonResponse;
import com.bootcamp.customermanagement.service.person.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RefreshScope
@RestController
public class PersonController implements PersonApi {

    @Autowired
    private PersonService personService;

    @Autowired
    private PersonMapper personMapper;

    @Value(value = "${nombre.clente-persona}")
    private String value;

    @Override
    public Mono<ResponseEntity<PersonResponse>> getPerson(String id, ServerWebExchange exchange) {
        System.err.println("Value: " + value);
        return personService.getPerson(id)
                .map(person -> personMapper.toModel(person))
                .map(ResponseEntity::ok);
    }

    @Override
    public Mono<ResponseEntity<Flux<PersonResponse>>> getPersons(ServerWebExchange exchange) {
        return Mono.just(ResponseEntity.ok().body(personService.getPersons()
                .map(person -> personMapper.toModel(person))));
    }

    @Override
    public Mono<ResponseEntity<Object>> registerPerson(Mono<PersonRequest> personRequest, ServerWebExchange exchange) {
        return personService.registerPerson(
                        personRequest.map(person -> personMapper.toDocument(person)))
                .map(ResponseEntity::ok);
    }

    @Override
    public Mono<ResponseEntity<Void>> removePerson(String id, ServerWebExchange exchange) {
        return personService.removePerson(id)
                .then(Mono.just(new ResponseEntity<Void>(HttpStatus.NO_CONTENT)))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Override
    public Mono<ResponseEntity<Object>> updatePerson(String id, Mono<PersonRequest> personRequest, ServerWebExchange exchange) {
        return personService.updatePerson(id,
                        personRequest.map(person -> personMapper.toDocument(person)))
                .map(ResponseEntity::ok);
    }
}