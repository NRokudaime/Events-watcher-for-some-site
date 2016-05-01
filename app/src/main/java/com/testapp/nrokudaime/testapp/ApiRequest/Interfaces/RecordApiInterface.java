package com.testapp.nrokudaime.testapp.ApiRequest.Interfaces;

import com.testapp.nrokudaime.testapp.Models.Record;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RecordApiInterface {
    @GET("events/")
    Call<ArrayList<Record>>getRecords(@Query("page") int pageNumb);
}
