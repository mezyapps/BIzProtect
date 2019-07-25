package com.mezyapps.bizprotect.model;

import android.os.Parcel;
import android.os.Parcelable;

public class MyBlackListedCustomerModel implements Parcelable {

    private String customer_name;
    private String contact_person;
    private String gst_no;
    private String address;
    private String email;
    private String mobile_no;
    private String aadhar_no;
    private String pan_no;
    private String status;
    private String description;
    private String client_id;
    private String inserted_date_time;
    private String updated_date_time;



    public static  final Parcelable.Creator CREATOR=new Parcelable.Creator()
    {


        @Override
        public MyBlackListedCustomerModel createFromParcel(Parcel source) {
            return new MyBlackListedCustomerModel(source);
        }

        @Override
        public MyBlackListedCustomerModel[] newArray(int size) {
            return new MyBlackListedCustomerModel[0];
        }
    };


    public MyBlackListedCustomerModel(String customer_name, String contact_person, String gst_no, String address, String email, String mobile_no, String aadhar_no, String pan_no, String status, String description, String client_id, String inserted_date_time, String updated_date_time) {
        this.customer_name = customer_name;
        this.contact_person = contact_person;
        this.gst_no = gst_no;
        this.address = address;
        this.email = email;
        this.mobile_no = mobile_no;
        this.aadhar_no = aadhar_no;
        this.pan_no = pan_no;
        this.status = status;
        this.description = description;
        this.client_id = client_id;
        this.inserted_date_time = inserted_date_time;
        this.updated_date_time = updated_date_time;
    }



    public MyBlackListedCustomerModel(Parcel source) {
        this.customer_name = source.readString();
        this.contact_person = source.readString();
        this.gst_no = source.readString();
        this.address = source.readString();
        this.email = source.readString();
        this.mobile_no = source.readString();
        this.aadhar_no = source.readString();
        this.pan_no = source.readString();
        this.status = source.readString();
        this.description = source.readString();
        this.client_id = source.readString();
        this.inserted_date_time = source.readString();
        this.updated_date_time = source.readString();

    }


    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public String getContact_person() {
        return contact_person;
    }

    public void setContact_person(String contact_person) {
        this.contact_person = contact_person;
    }

    public String getGst_no() {
        return gst_no;
    }

    public void setGst_no(String gst_no) {
        this.gst_no = gst_no;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile_no() {
        return mobile_no;
    }

    public void setMobile_no(String mobile_no) {
        this.mobile_no = mobile_no;
    }

    public String getAadhar_no() {
        return aadhar_no;
    }

    public void setAadhar_no(String aadhar_no) {
        this.aadhar_no = aadhar_no;
    }

    public String getPan_no() {
        return pan_no;
    }

    public void setPan_no(String pan_no) {
        this.pan_no = pan_no;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    public String getInserted_date_time() {
        return inserted_date_time;
    }

    public void setInserted_date_time(String inserted_date_time) {
        this.inserted_date_time = inserted_date_time;
    }

    public String getUpdated_date_time() {
        return updated_date_time;
    }

    public void setUpdated_date_time(String updated_date_time) {
        this.updated_date_time = updated_date_time;
    }



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(this.customer_name);
        dest.writeString(this.contact_person);
        dest.writeString(this.gst_no);
        dest.writeString(this.address);
        dest.writeString(this.email);
        dest.writeString(this.mobile_no);
        dest.writeString(this.aadhar_no);
        dest.writeString(this.pan_no);
        dest.writeString(this.status);
        dest.writeString(this.description);
        dest.writeString(this.client_id);
        dest.writeString(this.inserted_date_time);
        dest.writeString(this.updated_date_time);

    }
}
