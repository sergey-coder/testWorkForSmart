package ru.korotkov.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.korotkov.demo.dao.DaoService;
import ru.korotkov.demo.model.Customer;

import java.util.List;

/**
 * @author Korotkov Sergey
 */

@Component
public class CustomerService {

    private final DaoService daoService;

    @Autowired
    public CustomerService(@Qualifier("customerDaoServiceImpl") DaoService daoService) {
        this.daoService = daoService;
    }

    public List<Customer> getAll() {
        List<Customer> customerList = daoService.getAll();
        return customerList;
    }

    public void createCustomer(Customer customer) {
        daoService.create(customer);
    }

    public Customer getById(Long id) {
        Customer customer = (Customer) daoService.getById(id);
        return customer;
    }


}
