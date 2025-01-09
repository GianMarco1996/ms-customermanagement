package com.bootcamp.customermanagement.mapper.person;

import com.bootcamp.customermanagement.mapper.EntityMapper;
import com.bootcamp.customermanagement.model.documents.person.Person;
import com.bootcamp.customermanagement.model.PersonRequest;
import com.bootcamp.customermanagement.model.PersonResponse;
import org.springframework.stereotype.Component;

@Component
public class PersonMapper implements EntityMapper<Person, PersonResponse, PersonRequest> {

    @Override
    public PersonResponse toModel(Person domain) {
        PersonResponse person = new PersonResponse();
        person.setId(domain.getId());
        person.setName(domain.getName());
        person.setLastName(domain.getLastName());
        person.setTypePerson(domain.getTypePerson());
        person.setMobile(domain.getMobile());
        person.setEmail(domain.getEmail());
        person.setDocumentNumber(domain.getDocumentNumber());
        person.setDocumentType(domain.getDocumentType());
        person.setAddress(domain.getAddress());
        person.setBirthdate(domain.getBirthdate());
        person.setCustomerId(domain.getCustomerId());
        return person;
    }

    @Override
    public Person toDocument(PersonRequest model) {
        Person person = new Person();
        person.setName(model.getName());
        person.setLastName(model.getLastName());
        person.setTypePerson(getTypePerson(model.getTypePerson()));
        person.setMobile(model.getMobile());
        person.setEmail(model.getEmail());
        person.setDocumentNumber(model.getDocumentNumber());
        person.setDocumentType(getDocumentType(model.getDocumentType()));
        person.setAddress(model.getAddress());
        person.setBirthdate(model.getBirthdate());
        person.setCustomerId(model.getCustomerId());
        return person;
    }

    private String getTypePerson(PersonRequest.TypePersonEnum typePersonEnum) {
        return switch (typePersonEnum) {
            case T -> "Titular";
            case FA -> "Firmate Autorizado";
        };
    }

    private String getDocumentType(PersonRequest.DocumentTypeEnum documentTypeEnum) {
        return switch (documentTypeEnum) {
            case D -> "DNI";
            case CE -> "Carnet de Extranjeria";
            case P -> "Pasaporte";
        };
    }
}