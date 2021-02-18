package ru.korotkov.demo.model;

import java.math.BigDecimal;
import java.sql.Date;

public class TransactionFullList {
    private long id;
    private String numberAccount;
    private String FIO;
    private Date dateOperation;
    private String typeOperation;
    private BigDecimal sumOperation;
    private BigDecimal ballansAccountCorect;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNumberAccount() {
        return numberAccount;
    }

    public void setNumberAccount(String numberAccount) {
        this.numberAccount = numberAccount;
    }

    public String getFIO() {
        return FIO;
    }

    public void setFIO(String FIO) {
        this.FIO = FIO;
    }

    public Date getDateOperation() {
        return dateOperation;
    }

    public void setDateOperation(Date dateOperation) {
        this.dateOperation = dateOperation;
    }

    public String getTypeOperation() {
        return typeOperation;
    }

    public void setTypeOperation(String typeOperation) {
        this.typeOperation = typeOperation;
    }

    public BigDecimal getSumOperation() {
        return sumOperation;
    }

    public void setSumOperation(BigDecimal sumOperation) {
        this.sumOperation = sumOperation;
    }

    public BigDecimal getBallansAccountCorect() {
        return ballansAccountCorect;
    }

    public void setBallansAccountCorect(BigDecimal ballansAccountCorect) {
        this.ballansAccountCorect = ballansAccountCorect;
    }
}
