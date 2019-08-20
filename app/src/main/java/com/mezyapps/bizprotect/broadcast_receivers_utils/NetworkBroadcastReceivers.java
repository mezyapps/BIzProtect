package com.mezyapps.bizprotect.broadcast_receivers_utils;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.mezyapps.bizprotect.apicommon.ApiClient;
import com.mezyapps.bizprotect.apicommon.ApiInterface;
import com.mezyapps.bizprotect.database.DatabaseHandler;
import com.mezyapps.bizprotect.model.ClientProfileModel;
import com.mezyapps.bizprotect.model.SuccessModel;
import com.mezyapps.bizprotect.utils.ConstantFields;
import com.mezyapps.bizprotect.utils.NetworkUtils;
import com.mezyapps.bizprotect.utils.SharedLoginUtils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NetworkBroadcastReceivers extends BroadcastReceiver {

    @SuppressLint("UnsafeProtectedBroadcastReceiver")
    @Override
    public void onReceive(final Context context, Intent intent) {

        if (NetworkUtils.isNetworkAvailable(context)) {
            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            DatabaseHandler databaseHandler=new DatabaseHandler(context);
            SQLiteDatabase  database=databaseHandler.getWritableDatabase();
            ArrayList<ClientProfileModel> clientProfileModelArrayList=new ArrayList<>();
            clientProfileModelArrayList = SharedLoginUtils.getUserDetails(context);
            String client_id = clientProfileModelArrayList.get(0).getClient_id();
            Cursor cursor=databaseHandler.readLocalDatabase();

            while (cursor.moveToNext())
            {

                String sync_status=cursor.getString(cursor.getColumnIndex(ConstantFields.DatabaseConstant.SYNC_STATUS));
                if(sync_status.equalsIgnoreCase(ConstantFields.CommonConstant.sync_failed))
                {

                    String date=cursor.getString(cursor.getColumnIndex(ConstantFields.DatabaseConstant.DATE));
                    String status=cursor.getString(cursor.getColumnIndex(ConstantFields.DatabaseConstant.DATE));
                    String income_amount=cursor.getString(cursor.getColumnIndex(ConstantFields.DatabaseConstant.INCOME_AMOUNT));
                    String expense_amount=cursor.getString(cursor.getColumnIndex(ConstantFields.DatabaseConstant.EXPENSE_AMOUNT));
                    String description=cursor.getString(cursor.getColumnIndex(ConstantFields.DatabaseConstant.DESCRIPTION));
                    String amount="0";
                    if(income_amount.equalsIgnoreCase("0"))
                    {
                     amount=expense_amount;
                    }
                    if(expense_amount.equalsIgnoreCase("0"))
                    {
                        amount=income_amount;
                    }

                    Call<SuccessModel> call = apiInterface.addIncomeExpense(client_id, date, amount, description, status);

                    call.enqueue(new Callback<SuccessModel>() {
                        @Override
                        public void onResponse(Call<SuccessModel> call, Response<SuccessModel> response) {
                            String str_response = new Gson().toJson(response.body());
                            Log.d("Response >>", str_response);

                            try {
                                if (response.isSuccessful()) {
                                    SuccessModel successModule = response.body();

                                    String message = null, code = null;
                                    if (successModule != null) {
                                        message = successModule.getMessage();
                                        code = successModule.getCode();
                                        if (code.equalsIgnoreCase("1")) {
                                            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show();
                                            context.sendBroadcast(new Intent(ConstantFields.DatabaseConstant.UI_UPDATE_BROADCAST));
                                        } else {

                                        }

                                    } else {

                                    }
                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFailure(Call<SuccessModel> call, Throwable t) {

                        }
                    });
                }
            }

        }
    }
}
