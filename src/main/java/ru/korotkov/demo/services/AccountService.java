package ru.korotkov.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.korotkov.demo.dao.DaoService;
import ru.korotkov.demo.model.Account;
import ru.korotkov.demo.model.Customer;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Korotkov Sergey
 */

@Component
public class AccountService {
    private final DaoService daoService;

    @Autowired
    public AccountService(@Qualifier("accountDaoServiceImpl") DaoService daoService) {
        this.daoService = daoService;
    }

    public List<Account> getAll() {
        List<Account> accountList = daoService.getAll();
        return accountList;
    }

    public void createAccount(Account account, Long customerId) {
        Account creatAccount = new Account();
        creatAccount.setBallansAccount(BigDecimal.valueOf(0, 0));
        creatAccount.setNumberAccount(account.getNumberAccount());
        creatAccount.setCustomerId(customerId);
        daoService.create(creatAccount);
    }

    public void update(long id, Account account) {
        daoService.update(id, account);
    }

    public Account getById(Long id) {
        Account account = (Account) daoService.getById(id);
        return account;
    }

    public Account getByNumberAccount(String numberAccount) {
        return (Account) daoService.getOneByField(numberAccount);
    }

    public List<Account> getAllByCustomerId(long id) {
        return daoService.getAllById(id);
    }
}
