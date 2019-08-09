package com.mezyapps.bizprotect.model;

public class BalanceAmountListModel {
    private String name;
    private String address;
    private String mobile_no;
    private String balance_amt;

    public BalanceAmountListModel(String name, String address, String mobile_no, String balance_amt) {
        this.name = name;
        this.address = address;
        this.mobile_no = mobile_no;
        this.balance_amt = balance_amt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobile_no() {
        return mobile_no;
    }

    public void setMobile_no(String mobile_no) {
        this.mobile_no = mobile_no;
    }

    public String getBalance_amt() {
        return balance_amt;
    }

    public void setBalance_amt(String balance_amt) {
        this.balance_amt = balance_amt;
    }
}
