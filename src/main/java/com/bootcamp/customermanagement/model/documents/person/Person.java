package com.bootcamp.customermanagement.model.documents.person;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "persons")
public class Person {
    @Id
    private String id;
    private String name;
    private String lastName;
    private String typePerson;
    private String mobile;
    private String email;
    private String documentNumber;
    private String documentType;
    private String address;
    private String birthdate;
    private String customerId;
}