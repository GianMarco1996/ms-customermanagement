package com.bootcamp.customermanagement.service.customer;

import com.bootcamp.customermanagement.model.documents.customer.Customer;
import com.bootcamp.customermanagement.model.dto.CustomerDTO;
import com.bootcamp.customermanagement.repository.customer.CustomerRepository;
import com.bootcamp.customermanagement.repository.person.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PersonRepository personRepository;

    @Override
    public Flux<CustomerDTO> getCustomers() {
        return customerRepository.findAll()
                .map(this::mapperDTO)
                .flatMap(customer -> Mono.just(customer)
                        .zipWith(personRepository.findPersonByCustomerId(customer.getId())
                                .collectList(), (c, p) -> {
                            c.setPersons(p);
                            return c;
                        })
                );
    }

    @Override
    public Mono<CustomerDTO> getCustomer(String id) {
        return customerRepository.findById(id)
                .map(this::mapperDTO)
                .flatMap(customer -> Mono.just(customer)
                        .zipWith(personRepository.findPersonByCustomerId(customer.getId())
                                .collectList(), (c, p) -> {
                            c.setPersons(p);
                            return c;
                        }));
    }

    @Override
    public Mono<Customer> registerCustomer(Mono<CustomerDTO> customer) {
        return customer.map(c -> {
                    if (!(c.getType().equals("E") || c.getType().equals("P"))) {
                        throw new IllegalArgumentException("Los clientes son de tipo: Personal (P) y Empresarial (E)");
                    }
                    return c;
                })
                .map(this::mapper)
                .flatMap(customerRepository::insert);
    }

    @Override
    public Mono<Customer> updateCustomer(String id, Mono<CustomerDTO> customer) {
        return customerRepository.findById(id)
                .flatMap(c -> customer.map(this::mapper))
                .doOnNext(e -> e.setId(id))
                .flatMap(customerRepository::save);
    }

    @Override
    public Mono<Void> removeCustomer(String id) {
        return customerRepository.deleteById(id);
    }

    private CustomerDTO mapperDTO(Customer customer) {
        CustomerDTO newCustomer = new CustomerDTO();
        newCustomer.setId(customer.getId());
        newCustomer.setType(customer.getType());
        newCustomer.setCompanyName(customer.getCompanyName());
        newCustomer.setRuc(customer.getRuc());
        newCustomer.setPhone(customer.getPhone());
        newCustomer.setAddress(customer.getAddress());
        return newCustomer;
    }

    private Customer mapper(CustomerDTO customer) {
        Customer newCustomer = new Customer();
        newCustomer.setId(customer.getId());
        newCustomer.setType(customer.getType());
        newCustomer.setCompanyName(customer.getCompanyName());
        newCustomer.setRuc(customer.getRuc());
        newCustomer.setPhone(customer.getPhone());
        newCustomer.setAddress(customer.getAddress());
        return newCustomer;
    }
}