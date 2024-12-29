package com.bootcamp.customermanagement.model.dto;

import com.bootcamp.customermanagement.model.documents.person.Person;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CustomerDTO {
    private String id;
    private String type;
    private String companyName;
    private String ruc;
    private String phone;
    private String address;
    private List<Person> persons = new ArrayList<>();
}