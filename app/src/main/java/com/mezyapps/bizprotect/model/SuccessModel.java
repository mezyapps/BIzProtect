package com.mezyapps.bizprotect.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class SuccessModel {
    private String message;
    private String code;

    @SerializedName("client_details")
    private ArrayList<ClientProfileModel> clientProfileModelArrayList;

    @SerializedName("list_all_blacklisted")
    private ArrayList<BlackListCustomerModel>  blackListCustomerModelArrayList;

    @SerializedName("list_my_blacklisted")
    private ArrayList<MyBlackListedCustomerModel> myBlackListedCustomerModelArrayList;

    @SerializedName("list_my_customer")
    private ArrayList<MyCustomerModel> myCustomerModelArrayList;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public ArrayList<ClientProfileModel> getClientProfileModelArrayList() {
        return clientProfileModelArrayList;
    }

    public void setClientProfileModelArrayList(ArrayList<ClientProfileModel> clientProfileModelArrayList) {
        this.clientProfileModelArrayList = clientProfileModelArrayList;
    }

    public ArrayList<BlackListCustomerModel> getBlackListCustomerModelArrayList() {
        return blackListCustomerModelArrayList;
    }

    public void setBlackListCustomerModelArrayList(ArrayList<BlackListCustomerModel> blackListCustomerModelArrayList) {
        this.blackListCustomerModelArrayList = blackListCustomerModelArrayList;
    }

    public ArrayList<MyBlackListedCustomerModel> getMyBlackListedCustomerModelArrayList() {
        return myBlackListedCustomerModelArrayList;
    }

    public void setMyBlackListedCustomerModelArrayList(ArrayList<MyBlackListedCustomerModel> myBlackListedCustomerModelArrayList) {
        this.myBlackListedCustomerModelArrayList = myBlackListedCustomerModelArrayList;
    }

    public ArrayList<MyCustomerModel> getMyCustomerModelArrayList() {
        return myCustomerModelArrayList;
    }

    public void setMyCustomerModelArrayList(ArrayList<MyCustomerModel> myCustomerModelArrayList) {
        this.myCustomerModelArrayList = myCustomerModelArrayList;
    }
}
