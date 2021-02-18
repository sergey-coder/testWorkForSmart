package ru.korotkov.demo.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
import ru.korotkov.demo.dao.DaoService;
import ru.korotkov.demo.model.Account;
import ru.korotkov.demo.model.Customer;
import ru.korotkov.demo.services.AccountService;
import ru.korotkov.demo.services.CustomerService;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Korotkov Sergey
 */

@Component
public class AccountDaoServiceImpl implements DaoService<Account> {
    private JdbcTemplate jdbcTemplate;
    private final TransactionTemplate transactionTemplate;

    @Autowired
    public AccountDaoServiceImpl(JdbcTemplate jdbcTemplate, TransactionTemplate transactionTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.transactionTemplate = transactionTemplate;
    }

    @Override
    public void create(Account account) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            String insertSql = "INSERT INTO \"Account\"(\"customerId\",\"ballansAccount\",\"numberAccount\")" + " VALUES( ?, ?, ?)";

            PreparedStatement ps = connection.prepareStatement(insertSql, new String[]{"id"});
            ps.setLong(1, account.getCustomerId());
            ps.setBigDecimal(2, account.getBallansAccount());
            ps.setString(3, account.getNumberAccount());
            return ps;
        }, keyHolder);
    }

    @Override
    public Account getById(long id) {
        return jdbcTemplate.query("SELECT * FROM \"Account\" WHERE id=?", new Object[]{id}, BeanPropertyRowMapper.newInstance(Account.class))
                .stream().findAny().orElse(null);
    }

    @Override
    public void update(long id, Account account) {
        jdbcTemplate.update("UPDATE \"Account\" SET \"ballansAccount\"=? WHERE id=?", account.getBallansAccount(), id);
    }


    @Override
    public List<Account> getAllById(long customerId) {
        return getAll().stream().filter(account -> account.getCustomerId() == customerId).collect(Collectors.toList());
    }

    @Override
    public Account getOneByField(String field) {
        return getAll().stream().filter(account -> account.getNumberAccount().equals(field)).findFirst().orElse(null);
    }

    @Override
    public List<Account> findByFamilyAndData(Map<String, Object> dataFilter, AccountService accountService, CustomerService customerService) {
        return null;
    }

    @Override
    public void delete(long id) {
    }

    @Override
    public List<Account> getAll() {
        return jdbcTemplate.query("SELECT * FROM \"Account\"", BeanPropertyRowMapper.newInstance(Account.class));
    }

}
