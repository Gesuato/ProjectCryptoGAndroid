package com.cryptog.cryptog.model;


import java.io.Serializable;

public class Coin implements Serializable{

    private int id;
    private String coinName;
    private String fullName;
    private String symbol;
    private double conversionValueCAD;
    private double conversionValueBRL;
    private double conversionCountryOfVisit;

    public Coin() {
    }

    public Coin(int id, String coinName, String fullName, String symbol, double conversionValueCAD, double conversionValueBRL, double conversionCountryOfVisit) {
        this.id = id;
        this.coinName = coinName;
        this.fullName = fullName;
        this.symbol = symbol;
        this.conversionValueCAD = conversionValueCAD;
        this.conversionValueBRL = conversionValueBRL;
        this.conversionCountryOfVisit = conversionCountryOfVisit;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCoinName() {
        return coinName;
    }

    public void setCoinName(String coinName) {
        this.coinName = coinName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public double getConversionValueCAD() {
        return conversionValueCAD;
    }

    public void setConversionValueCAD(double conversionValueCAD) {
        this.conversionValueCAD = conversionValueCAD;
    }

    public double getConversionValueBRL() {
        return conversionValueBRL;
    }

    public void setConversionValueBRL(double conversionValueBRL) {
        this.conversionValueBRL = conversionValueBRL;
    }

    public double getConversionCountryOfVisit() {
        return conversionCountryOfVisit;
    }

    public void setConversionCountryOfVisit(double conversionCountryOfVisit) {
        this.conversionCountryOfVisit = conversionCountryOfVisit;
    }
}
