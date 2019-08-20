package com.mezyapps.bizprotect.model;

public class OnlineDataBackUpModel {

    String Date;
    String Income_Amount;
    String Expense_Amount;
    String Description;
    String status;


    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getIncome_Amount() {
        return Income_Amount;
    }

    public void setIncome_Amount(String income_Amount) {
        Income_Amount = income_Amount;
    }

    public String getExpense_Amount() {
        return Expense_Amount;
    }

    public void setExpense_Amount(String expense_Amount) {
        Expense_Amount = expense_Amount;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
