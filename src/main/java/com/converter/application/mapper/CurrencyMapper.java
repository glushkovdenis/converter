package com.converter.application.mapper;

import java.sql.Date;

public class CurrencyMapper {

    private String id;
    private String NumCode;
    private String CharCode;
    private int Nominal;
    private String Name;
    private double Value;
    private Date date;

    public CurrencyMapper() {}

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public String getNumCode() {
        return NumCode;
    }

    public void setNumCode(String numCode) {
        NumCode = numCode;
    }

    public String getCharCode() {
        return CharCode;
    }

    public void setCharCode(String charCode) {
        CharCode = charCode;
    }

    public int getNominal() {
        return Nominal;
    }

    public void setNominal(int nominal) {
        Nominal = nominal;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public double getValue() {
        return Value;
    }

    public void setValue(double value) {
        Value = value;
    }

    public Date getDate() { return date; }

    public void setDate(Date date) { this.date = date; }

    public String getAllValues() {
        return getId() +
                "\n" + getNumCode() +
                "\n" + getCharCode() +
                "\n" + getNominal() +
                "\n" + getName() +
                "\n" + getValue();
    }
}
