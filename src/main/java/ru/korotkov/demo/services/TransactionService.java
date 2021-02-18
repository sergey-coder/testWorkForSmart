package ru.korotkov.demo.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.korotkov.demo.dao.DaoService;
import ru.korotkov.demo.model.Account;
import ru.korotkov.demo.model.Transaction;
import ru.korotkov.demo.model.TransactionFullList;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

;

@Component
public class TransactionService {
    private final DaoService daoService;

    @Autowired
    public TransactionService(@Qualifier("transactionDaoServiceImpl") DaoService daoService) {
        this.daoService = daoService;
    }

    public Transaction getById(Long id) {
        Transaction transaction = (Transaction) daoService.getById(id);
        return transaction;
    }

    @Transactional
    public void createTransaction(Long accountIdCorect,
                                  Long accountIdAnother,
                                  BigDecimal summa,
                                  AccountService accountService,
                                  TypeOperation operation) {

        Transaction transactionEntity = new Transaction();
        Account accountCorect = accountService.getById(accountIdCorect);
        Account accountAnother = accountService.getById(accountIdAnother);

        switch (operation) {
            case DEPOSIT:
                operationDeposit(transactionEntity, accountCorect, summa, accountService);
                break;
            case WRITEDOWNS:
                operationWritedowns(transactionEntity, accountCorect, summa, accountService);
                break;
            case TRANSFER:
                operationTransfer(transactionEntity, accountCorect, accountAnother, summa, accountService);
                break;
            default:
                break;
        }
    }

    private void operationTransfer(Transaction transactionEntity, Account accountCorect, Account accountAnother, BigDecimal summa, AccountService accountService) {
        if (checkBallans(accountCorect, summa)) {
            transactionEntity.setAccountCorectId(accountCorect.getId());
            transactionEntity.setAccountIdAnotherId(accountAnother.getId());
            transactionEntity.setDateOperation(new Date(System.currentTimeMillis()));
            transactionEntity.setTypeOperation(3);
            transactionEntity.setBallansAccountCorect(accountCorect.getBallansAccount().subtract(summa));
            transactionEntity.setBallansAccountAnother(accountAnother.getBallansAccount().add(summa));
            transactionEntity.setSumOperation(summa);
            daoService.create(transactionEntity);
            accountCorect.setBallansAccount(accountCorect.getBallansAccount().subtract(summa));
            accountService.update(accountCorect.getId(), accountCorect);

            accountAnother.setBallansAccount(accountAnother.getBallansAccount().add(summa));
            accountService.update(accountAnother.getId(), accountAnother);
        }
    }

    private void operationWritedowns(Transaction transactionEntity, Account accountCorect, BigDecimal summa, AccountService accountService) {
        if (checkBallans(accountCorect, summa)) {
            transactionEntity.setAccountCorectId(accountCorect.getId());
            transactionEntity.setAccountIdAnotherId(accountCorect.getId());
            transactionEntity.setDateOperation(new Date(System.currentTimeMillis()));
            transactionEntity.setTypeOperation(1);
            transactionEntity.setBallansAccountCorect(accountCorect.getBallansAccount().subtract(summa));
            transactionEntity.setBallansAccountAnother(accountCorect.getBallansAccount().subtract(summa));
            transactionEntity.setSumOperation(summa);
            daoService.create(transactionEntity);
            accountCorect.setBallansAccount(accountCorect.getBallansAccount().subtract(summa));
            accountService.update(accountCorect.getId(), accountCorect);
        }
    }

    private void operationDeposit(Transaction transactionEntity, Account accountCorect, BigDecimal summa, AccountService accountService) {
        transactionEntity.setAccountCorectId(accountCorect.getId());
        transactionEntity.setAccountIdAnotherId(accountCorect.getId());
        transactionEntity.setDateOperation(new Date(System.currentTimeMillis()));
        transactionEntity.setTypeOperation(2);
        transactionEntity.setSumOperation(summa);
        transactionEntity.setBallansAccountCorect(accountCorect.getBallansAccount().add(summa));
        transactionEntity.setBallansAccountAnother(accountCorect.getBallansAccount().add(summa));
        daoService.create(transactionEntity);
        accountCorect.setBallansAccount(accountCorect.getBallansAccount().add(summa));
        accountService.update(accountCorect.getId(), accountCorect);
    }

    private boolean checkBallans(Account account, BigDecimal summa) {
        return account.getBallansAccount().compareTo(summa) > 0;
    }

    public List<Transaction> getAll() {
        return daoService.getAll();
    }

    public List getListForHTMLReestr(AccountService accountService, CustomerService customerService) {

        List<Transaction> transactionList = daoService.getAll();
        List<TransactionFullList> rezultList = new ArrayList<>();
        for (Transaction item : transactionList) {
            TransactionFullList transactionForList = new TransactionFullList();
            transactionForList.setId(item.getId());
            transactionForList.setNumberAccount(accountService.getById(item.getAccountCorectId()).getNumberAccount());
            transactionForList.setDateOperation(item.getDateOperation());
            transactionForList.setTypeOperation(TypeOperation.findByCode(item.getTypeOperation()).getLabel());
            transactionForList.setSumOperation(item.getSumOperation());
            transactionForList.setBallansAccountCorect(item.getBallansAccountCorect());
            transactionForList.setFIO(customerService.getById(accountService.getById(item.getAccountCorectId()).getCustomerId()).getFamily());
            rezultList.add(transactionForList);
        }
        return rezultList;
    }

    public List getListFilterForHTMLReestr(Map<String, Object> dataFilter, AccountService accountService, CustomerService customerService) {

        List<TransactionFullList> transactionList = getListForHTMLReestr(accountService, customerService);
        List<TransactionFullList> rezultList = transactionList;
        if (!dataFilter.get("customerFamily").equals(""))
            rezultList = transactionList.stream().filter(transaction -> transaction.getFIO().equals(dataFilter.get("customerFamily"))).collect(Collectors.toList());
        /*rezultList.stream().filter(transaction -> transaction.getDateOperation().after((Date) dataFilter.get("dataStart"))
                && transaction.getDateOperation().before((Date) dataFilter.get("dataFinish")))
                .collect(Collectors.toList());*/
        return rezultList;
    }


}
