package ru.korotkov.demo.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.korotkov.demo.dao.DaoService;
import ru.korotkov.demo.model.Transaction;
import ru.korotkov.demo.services.AccountService;
import ru.korotkov.demo.services.CustomerService;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Transactional
@Component
public class TransactionDaoServiceImpl implements DaoService<Transaction> {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public TransactionDaoServiceImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void create(Transaction transaction) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            String insertSql = "INSERT INTO \"Transaction\"(\"accountCorectId\",\"accountIdAnotherId\"," +
                    "\"dateOperation\",\"typeOperation\",\"sumOperation\",\"ballansAccountCorect\",\"ballansAccountAnother\")" + " VALUES( ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement ps = connection.prepareStatement(insertSql, new String[]{"id"});
            ps.setLong(1, transaction.getAccountCorectId());
            ps.setLong(2, transaction.getAccountIdAnotherId());
            ps.setDate(3, transaction.getDateOperation());
            ps.setInt(4, transaction.getTypeOperation());
            ps.setBigDecimal(5, transaction.getSumOperation());
            ps.setBigDecimal(6, transaction.getBallansAccountCorect());
            ps.setBigDecimal(7, transaction.getBallansAccountAnother());
            return ps;
        }, keyHolder);
    }

    @Override
    public Transaction getById(long id) {
        return jdbcTemplate.query("SELECT * FROM \"Transaction\" WHERE id=?", new Object[]{id}, BeanPropertyRowMapper.newInstance(Transaction.class))
                .stream().findAny().orElse(null);
    }

    @Override
    public void update(long id, Transaction entity) {
    }

    @Override
    public void delete(long id) {
    }

    @Override
    public List<Transaction> getAll() {
        return jdbcTemplate.query("SELECT * FROM \"Transaction\"", BeanPropertyRowMapper.newInstance(Transaction.class));
    }

    @Override
    public List<Transaction> getAllById(long id) {
        return null;
    }

    @Override
    public Transaction getOneByField(String field) {
        return null;
    }

    @Override
    public List<Transaction> findByFamilyAndData(Map<String, Object> dataFilter, AccountService accountService, CustomerService customerService) {

        if (!dataFilter.get("customerFamily").equals("")) {
            List<Transaction> listFilterFamily = getAll().stream().filter(transaction ->
                    customerService.getById(accountService.getById(transaction.getAccountCorectId()).getCustomerId()).getFamily()
                            .equals(dataFilter.get("customerFamily"))).collect(Collectors.toList());
            return listFilterFamily;
        }
        return getAll();
    }

}
