package com.mezyapps.bizprotect.apicommon;


import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiInterface {

   /* @GET(EndApi.LIST_AGRI_VIDEO)
    Call<SuccessModule> agriVideoGallery();

    @POST(EndApi.USER_REGISTRATION)
    @FormUrlEncoded
    Call<SuccessModule> registration(@Field("name") String name,
                                     @Field("email") String email,
                                     @Field("birthday") String birthday,
                                     @Field("gender") String gender,
                                     @Field("phone") String phone,
                                     @Field("password") String password,
                                     @Field("int_id") String int_id,
                                     @Field("image_url") String image_url);


    @POST(EndApi.USER_LOGIN)
    @FormUrlEncoded
    Call<SuccessModule> login(@Field("phone") String phone,
                              @Field("password") String password);


    @GET(LicenseEndApi.WEATHER)
    Call<WeatherResponseModule> getCurrentWeatherData(@Query("lat") String lat,
                                                      @Query("lon") String lon,
                                                      @Query("APPID") String app_id);

    @POST(EndApi.INTEGRATION_LOGIN)
    @FormUrlEncoded
    Call<SuccessModule> loginIntegration(@Field("name") String name,
                                         @Field("email") String email,
                                         @Field("birthday") String birthday,
                                         @Field("gender") String gender,
                                         @Field("phone") String phone,
                                         @Field("password") String password,
                                         @Field("int_id") String int_id,
                                         @Field("image_url") String image_url);*/
}
