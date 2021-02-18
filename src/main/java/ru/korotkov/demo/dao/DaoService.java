package ru.korotkov.demo.dao;

import ru.korotkov.demo.services.AccountService;
import ru.korotkov.demo.services.CustomerService;

import java.util.List;
import java.util.Map;

public interface DaoService <T> {

    void create(T entity);
    T getById(long id);
    void update(long id, T entity);
    void delete(long id);
    List<T> getAll();
    List<T> getAllById(long id);
    T getOneByField(String field);
    List<T> findByFamilyAndData(Map<String,Object> dataFilter, AccountService accountService, CustomerService customerService);
}

