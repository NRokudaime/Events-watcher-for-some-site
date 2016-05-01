package com.testapp.nrokudaime.testapp.ApiRequest;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BaseApiRequest {
    public static final String baseURL = "http://expoforum-center.ru/ru/calendar/api/v2/";
    public static final String imagetBaseURL = "http://expoforum-center.ru/";
    public static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(baseURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    }
