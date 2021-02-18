package ru.korotkov.demo.model;

import java.math.BigDecimal;
import java.sql.Date;


public class Transaction {

    private Long id;
    private Long accountCorectId;
    private Long accountIdAnotherId;
    private Date dateOperation;
    private int typeOperation;
    private BigDecimal sumOperation;
    private BigDecimal ballansAccountCorect;
    private BigDecimal ballansAccountAnother;

    public Transaction() {
    }

    public BigDecimal getSumOperation() {
        return sumOperation;
    }

    public void setSumOperation(BigDecimal sumOperation) {
        this.sumOperation = sumOperation;
    }

    public Transaction(Long id, Long accountCorectId, Long accountIdAnotherId, Date dateOperation, int typeOperation, BigDecimal sumOperation, BigDecimal ballansAccountCorect, BigDecimal ballansAccountAnother) {
        this.id = id;
        this.accountCorectId = accountCorectId;
        this.accountIdAnotherId = accountIdAnotherId;
        this.dateOperation = dateOperation;
        this.typeOperation = typeOperation;
        this.sumOperation = sumOperation;
        this.ballansAccountCorect = ballansAccountCorect;
        this.ballansAccountAnother = ballansAccountAnother;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAccountCorectId() {
        return accountCorectId;
    }

    public void setAccountCorectId(Long accountCorectId) {
        this.accountCorectId = accountCorectId;
    }

    public Long getAccountIdAnotherId() {
        return accountIdAnotherId;
    }

    public void setAccountIdAnotherId(Long accountIdAnotherId) {
        this.accountIdAnotherId = accountIdAnotherId;
    }

    public Date getDateOperation() {
        return dateOperation;
    }

    public void setDateOperation(Date dateOperation) {
        this.dateOperation = dateOperation;
    }

    public int getTypeOperation() {
        return typeOperation;
    }

    public void setTypeOperation(int typeOperation) {
        this.typeOperation = typeOperation;
    }

    public BigDecimal getBallansAccountCorect() {
        return ballansAccountCorect;
    }

    public void setBallansAccountCorect(BigDecimal ballansAccountCorect) {
        this.ballansAccountCorect = ballansAccountCorect;
    }

    public BigDecimal getBallansAccountAnother() {
        return ballansAccountAnother;
    }

    public void setBallansAccountAnother(BigDecimal ballansAccountAnother) {
        this.ballansAccountAnother = ballansAccountAnother;
    }
}
