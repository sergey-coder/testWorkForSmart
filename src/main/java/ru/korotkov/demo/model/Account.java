package ru.korotkov.demo.model;

import java.math.BigDecimal;

public class Account {
    private Long id;
    private Long customerId;
    private BigDecimal ballansAccount;
    private String numberAccount;

    public Account() {
    }

    public Account(Long id,
                   Long customerId,
                   String numberAccount) {
        this.id = id;
        this.customerId = customerId;
        this.ballansAccount = ballansAccount;
        this.numberAccount = numberAccount;
    }

    public String getNumberAccount() {
        return numberAccount;
    }

    public void setNumberAccount(String numberAccount) {
        this.numberAccount = numberAccount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public BigDecimal getBallansAccount() {
        return ballansAccount;
    }

    public void setBallansAccount(BigDecimal ballansAccount) {
        this.ballansAccount = ballansAccount;
    }
}
