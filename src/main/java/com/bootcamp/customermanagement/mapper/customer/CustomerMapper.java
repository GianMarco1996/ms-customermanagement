package com.bootcamp.customermanagement.mapper.customer;

import com.bootcamp.customermanagement.mapper.EntityMapper;
import com.bootcamp.customermanagement.mapper.person.PersonMapper;
import com.bootcamp.customermanagement.model.CustomerRequest;
import com.bootcamp.customermanagement.model.CustomerResponse;
import com.bootcamp.customermanagement.model.dto.CustomerDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper implements EntityMapper<CustomerDTO, CustomerResponse, CustomerRequest> {

    @Autowired
    private PersonMapper personMapper;

    @Override
    public CustomerResponse toModel(CustomerDTO domain) {
        CustomerResponse customer = new CustomerResponse();
        customer.setId(domain.getId());
        customer.setType(domain.getType());
        customer.setCompanyName(domain.getCompanyName());
        customer.setRuc(domain.getRuc());
        customer.setPhone(domain.getPhone());
        customer.setAddress(domain.getAddress());
        customer.setPersons(
                domain.getPersons()
                        .stream()
                        .map(person -> personMapper.toModel(person))
                        .toList()
        );
        return customer;
    }

    @Override
    public CustomerDTO toDocument(CustomerRequest model) {
        CustomerDTO customer = new CustomerDTO();
        customer.setType(getType(model.getType()));
        customer.setCategory(getCategory(model.getCategory()));
        customer.setCompanyName(model.getCompanyName());
        customer.setRuc(model.getRuc());
        customer.setPhone(model.getPhone());
        customer.setAddress(model.getAddress());
        return customer;
    }

    private String getType(CustomerRequest.TypeEnum typeEnum) {
        return switch (typeEnum) {
            case E -> "Empresarial";
            case P -> "Personal";
        };
    }

    private String getCategory(CustomerRequest.CategoryEnum categoryEnum) {
        return switch (categoryEnum) {
            case N -> "Normal";
            case V -> "VIP";
            case P -> "PYME";
        };
    }
}