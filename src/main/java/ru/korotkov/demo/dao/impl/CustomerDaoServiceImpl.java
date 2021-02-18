package ru.korotkov.demo.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.korotkov.demo.dao.DaoService;
import ru.korotkov.demo.model.Customer;
import ru.korotkov.demo.services.AccountService;
import ru.korotkov.demo.services.CustomerService;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Map;


@Component
public class CustomerDaoServiceImpl implements DaoService<Customer> {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public CustomerDaoServiceImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void create(Customer entity) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            String insertSql = "INSERT INTO \"Customer\"(family,name,patronymic,address)" + " VALUES( ?, ?, ?, ?)";

            PreparedStatement ps = connection.prepareStatement(insertSql, new String[]{"id"});
            ps.setString(1, entity.getFamily());
            ps.setString(2, entity.getName());
            ps.setString(3, entity.getPatronymic());
            ps.setString(4, entity.getAddress());
            return ps;
        }, keyHolder);
    }

    @Override
    public Customer getById(long id) {
        return jdbcTemplate.query("SELECT * FROM \"Customer\" WHERE id=?", new Object[]{id}, BeanPropertyRowMapper.newInstance(Customer.class))
                .stream().findAny().orElse(null);
    }

    @Override
    public void update(long id, Customer entity) {
    }

    @Override
    public void delete(long id) {
    }

    @Override
    public List<Customer> getAll() {
        return jdbcTemplate.query("SELECT * FROM \"Customer\"", BeanPropertyRowMapper.newInstance(Customer.class));
    }

    @Override
    public List<Customer> getAllById(long id) {
        return null;
    }

    @Override
    public Customer getOneByField(String field) {
        return null;
    }

    @Override
    public List<Customer> findByFamilyAndData(Map<String, Object> dataFilter, AccountService accountService, CustomerService customerService) {
        return null;
    }
}
