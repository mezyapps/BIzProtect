package com.mezyapps.bizprotect.apicommon;


import com.mezyapps.bizprotect.model.SuccessModule;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiInterface {

    @POST(EndApi.LICENSE_KEY_SEND_APPROVE)
    @FormUrlEncoded
    Call<SuccessModule> licenseKeySend(@Field("key") String key,
                                       @Field("mac") String mac);

    @POST(EndApi.CHECK_LICENSE_KEY)
    @FormUrlEncoded
    Call<SuccessModule> checkLicenseKey(@Field("mac") String mac);
}
