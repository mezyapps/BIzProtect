package com.mezyapps.bizprotect.apicommon;


import com.mezyapps.bizprotect.model.SuccessModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiInterface {

    @POST(EndApi.LICENSE_KEY_SEND_APPROVE)
    @FormUrlEncoded
    Call<SuccessModel> licenseKeySend(@Field("key") String key,
                                      @Field("mac") String mac);

    @POST(EndApi.CHECK_LICENSE_KEY)
    @FormUrlEncoded
    Call<SuccessModel> checkLicenseKey(@Field("mac") String mac);


    @POST(EndApi.CLIENT_REGISTRATION)
    @FormUrlEncoded
    Call<SuccessModel> registrationClient(@Field("name") String name,
                                          @Field("company_name") String company_name,
                                          @Field("address") String address,
                                          @Field("gst_no") String gst_no,
                                          @Field("email") String email,
                                          @Field("mobile_no") String mobile_no,
                                          @Field("aadhar_no") String aadhar_no,
                                          @Field("pan_no") String pan_no,
                                          @Field("password") String password);


    @POST(EndApi.LOGIN)
    @FormUrlEncoded
    Call<SuccessModel> login(@Field("mobile_no") String mobile_no,
                             @Field("password") String password);


    @POST(EndApi.ALL_BLACKLISTED)
    Call<SuccessModel> allBlackListed();



    @POST(EndApi.MY_BLACKLISTED)
    @FormUrlEncoded
    Call<SuccessModel> myBlackListed(@Field("client_id") String client_id);


    @POST(EndApi.CUSTOMER_REGISTRATION)
    @FormUrlEncoded
    Call<SuccessModel> registrationCustomer(@Field("name") String name,
                                            @Field("contact_person") String contact_person,
                                            @Field("gst_no") String  gst_no,
                                            @Field("address") String  address,
                                            @Field("email") String  email,
                                            @Field("aadhar_no") String  aadhar_no,
                                            @Field("pan_no") String  pan_no,
                                            @Field("mobile_no") String  mobile_no,
                                            @Field("client_id") String  client_id);

    @POST(EndApi.MY_CUSTOMER)
    @FormUrlEncoded
    Call<SuccessModel> myCustomerList(@Field("client_id") String client_id);

}
