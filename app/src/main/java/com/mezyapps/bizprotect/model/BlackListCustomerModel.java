package com.mezyapps.bizprotect.model;

public class BlackListCustomerModel {
    String customer_name;
    String gst_no;
    String status;


    public BlackListCustomerModel(String customer_name, String gst_no, String status) {
        this.customer_name = customer_name;
        this.gst_no = gst_no;
        this.status = status;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public String getGst_no() {
        return gst_no;
    }

    public void setGst_no(String gst_no) {
        this.gst_no = gst_no;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
