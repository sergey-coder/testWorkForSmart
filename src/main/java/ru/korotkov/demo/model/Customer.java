package ru.korotkov.demo.model;

public class Customer {
    private Long id;
    private String family;
    private String name;
    private String patronymic;
    private String address;

    public Customer() {
    }

    public Customer(Long id, String family, String name, String address) {
        this.id = id;
        this.family = family;
        this.name = name;
        this.patronymic = patronymic;
        this.address = address;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
