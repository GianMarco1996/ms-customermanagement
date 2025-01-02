package com.bootcamp.customermanagement.service.person;

import com.bootcamp.customermanagement.model.documents.person.Person;
import com.bootcamp.customermanagement.repository.person.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class PersonServiceImpl implements PersonService {

    @Autowired
    private PersonRepository personRepository;

    @Override
    public Flux<Person> getPersons() {
        return personRepository.findAll();
    }

    @Override
    public Mono<Person> getPerson(String id)  {
        return personRepository.findById(id);
    }

    @Override
    public Mono<Person> registerPerson(Mono<Person> person) {
        return person.flatMap(personRepository::insert);
    }

    @Override
    public Mono<Person> updatePerson(String id, Mono<Person> person) {
        return personRepository.findById(id)
                .flatMap(p -> person)
                .doOnNext(e -> e.setId(id))
                .flatMap(personRepository::save);
    }

    @Override
    public Mono<Void> removePerson(String id)  {
        return personRepository.deleteById(id);
    }
}