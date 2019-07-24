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
}
