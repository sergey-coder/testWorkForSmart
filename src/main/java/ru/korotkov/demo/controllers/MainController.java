package ru.korotkov.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.korotkov.demo.model.Account;
import ru.korotkov.demo.model.Customer;
import ru.korotkov.demo.model.Transaction;
import ru.korotkov.demo.services.AccountService;
import ru.korotkov.demo.services.CustomerService;
import ru.korotkov.demo.services.TransactionService;
import ru.korotkov.demo.services.TypeOperation;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
public class MainController {
    private final AccountService accountService;
    private final CustomerService customerService;
    private final TransactionService transactionService;

    @Autowired
    public MainController(AccountService accountService, CustomerService customerService, TransactionService transactionService) {
        this.accountService = accountService;
        this.customerService = customerService;
        this.transactionService = transactionService;
    }

    @GetMapping("/")
    private String getMainJSP() {
        return "main";
    }

    @GetMapping("/customers")
    private String getListCustomer(Model model) {
        model.addAttribute("customerList", customerService.getAll());
        return "reestrCustomers";
    }

    @PostMapping("/customer")
    private String createCustomer(@ModelAttribute("customer") Customer customer) {
        customerService.createCustomer(customer);
        return "successful/successfulAddCustomer";
    }

    @PostMapping("/customer/{id}/account")
    private String createCustomerAccount(@ModelAttribute("account") Account account,
                                         @PathVariable("id") Long id, Model model) {
        model.addAttribute("customerId", id);
        accountService.createAccount(account, id);
        return "successful/successfulAddAccount";
    }

    @GetMapping("/customer/{id}/accounts")
    private String getListCustomerAccounts(@PathVariable("id") long id, Model model) {
        model.addAttribute("customer", customerService.getById(id));
        model.addAttribute("accountList", accountService.getAllByCustomerId(id));
        return "reestrCustomerAccounts";
    }

    @GetMapping("/account{id}")
    private String createTransaction(@PathVariable("id") long id,
                                     Model model) {
        model.addAttribute("account", transactionService.getById(id));
        model.addAttribute("transaction", new Transaction());
        return "transaction";
    }

    @GetMapping("/addCustomer")
    private String addCustomer(@ModelAttribute("customer") Customer customer) {
        return "addCustomer";
    }

    @GetMapping("/customer/{id}/account")
    private String getFormAddCustomerAccount(@PathVariable("id") long id,
                                             Model model) {
        model.addAttribute("customer", customerService.getById(id));
        model.addAttribute("account", new Account());
        return "addCustomerAccounts";
    }

    // реестр транзакций
    @GetMapping("/transaction")
    private String getListTransaction(Model model) {
        model.addAttribute("transactionList", transactionService.getListForHTMLReestr(accountService, customerService));
        return "reestrTransaction";
    }

    @PostMapping("/transaction")
    private String getFilterTransaction(@RequestParam(value = "customerFamily", required = false) String customerFamily,
                                        @RequestParam(value = "dataStart", required = false) String dataStart,
                                        @RequestParam(value = "dataFinish", required = false) String dataFinish,
                                        Model model) {
        Map<String, Object> dataFilter = new HashMap<>();
        dataFilter.put("customerFamily", customerFamily);
        /*dataFilter.put("dataStart", Date.valueOf(dataStart));
        dataFilter.put("dataFinish", Date.valueOf(dataFinish));*/
        model.addAttribute("transactionList", transactionService.getListFilterForHTMLReestr(dataFilter, accountService, customerService));
        return "reestrTransaction";
    }

    //операции со счетами
    @GetMapping("/customer/{customerId}/deposit/{accountId}")
    private String getDeposit(@PathVariable("customerId") long customerId,
                              @PathVariable("accountId") long accountId,
                              Model model) {
        model.addAttribute("account", accountService.getById(accountId));
        model.addAttribute("customerId", customerId);
        return "operations/deposit";
    }

    @PostMapping("/customer/{customerId}/deposit/{accountId}")
    private String getDepositResult(@PathVariable("customerId") long customerId,
                                    @PathVariable("accountId") long accountId,
                                    @RequestParam(value = "summa") String summa,
                                    Model model) {
        model.addAttribute("customer", customerService.getById(customerId));
        model.addAttribute("accountList", accountService.getAllByCustomerId(customerId));

        BigDecimal bigDecimalSumma = new BigDecimal(summa);
        if (bigDecimalSumma.compareTo(new BigDecimal(0.00)) > 0) {
            transactionService.createTransaction(accountId, accountId, bigDecimalSumma, accountService, TypeOperation.DEPOSIT);
        }
        return "successful/successfulOperation";
    }

    @GetMapping("/customer/{customerId}/writedown/{accountId}")
    private String getWriteDown(@PathVariable("customerId") long customerId,
                                @PathVariable("accountId") long accountId,
                                Model model) {
        model.addAttribute("account", accountService.getById(accountId));
        model.addAttribute("customerId", customerId);
        return "operations/writedown";
    }

    @PostMapping("/customer/{customerId}/writedown/{accountId}")
    private String getWriteDownResult(@PathVariable("customerId") long customerId,
                                      @PathVariable("accountId") long accountId,
                                      @RequestParam(value = "summa") String summa,
                                      Model model) {
        model.addAttribute("customer", customerService.getById(customerId));
        model.addAttribute("accountList", accountService.getAllByCustomerId(customerId));

        BigDecimal bigDecimalSumma = new BigDecimal(summa);

        if (bigDecimalSumma.compareTo(new BigDecimal(0.00)) > 0 && accountService.getById(accountId).getBallansAccount().compareTo(bigDecimalSumma) > 0) {
            transactionService.createTransaction(accountId, accountId, bigDecimalSumma, accountService, TypeOperation.WRITEDOWNS);
        } else return "error";
        return "successful/successfulOperation";
    }

    @GetMapping("/customer/{customerId}/transfer/{accountId}")
    private String getTransfer(@PathVariable("customerId") long customerId,
                               @PathVariable("accountId") long accountId,
                               Model model) {
        model.addAttribute("account", accountService.getById(accountId));
        model.addAttribute("customerId", customerId);
        return "operations/transfer";
    }

    @PostMapping("/customer/{customerId}/transfer/{accountId}")
    private String getTransferResult(@PathVariable("customerId") long customerId,
                                     @PathVariable("accountId") long accountId,
                                     @RequestParam(value = "summa") String summa,
                                     @RequestParam(value = "anotherNumberAccount") String anotherNumberAccount,
                                     Model model) {
        model.addAttribute("customer", customerService.getById(customerId));
        model.addAttribute("accountList", accountService.getAllByCustomerId(customerId));

        if (accountService.getByNumberAccount(anotherNumberAccount) == null) {
            return "error";
        }

        BigDecimal bigDecimalSumma = new BigDecimal(summa);
        if (bigDecimalSumma.compareTo(new BigDecimal(0.00)) > 0 && accountService.getById(accountId).getBallansAccount().compareTo(bigDecimalSumma) > 0) {
            transactionService.createTransaction(accountId, accountService.getByNumberAccount(anotherNumberAccount).getId(), bigDecimalSumma, accountService, TypeOperation.TRANSFER);
        } else return "error";
        return "successful/successfulOperation";
    }

    @GetMapping("/error")
    private String getErrorPage() {
        return "error";
    }
}
