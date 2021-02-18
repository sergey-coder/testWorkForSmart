package ru.korotkov.demo.services;

public enum TypeOperation {
    WRITEDOWNS(1, "Списание"),
    DEPOSIT(2, "Внесение"),
    TRANSFER(3, "Перевод");

    private final int code;
    private final String label;

    TypeOperation(int code, String label) {
        this.code = code;
        this.label = label;
    }

    public static TypeOperation findByCode(int code) {
        for (TypeOperation item : TypeOperation.values()) {
            if (code == item.code) return item;
        }
        throw new IllegalArgumentException("Unknown TypeStatusLic: " + code);
    }

    public int getCode() {
        return this.code;
    }

    public String getLabel() {
        return this.label;
    }
}
