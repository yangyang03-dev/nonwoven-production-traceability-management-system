package com.example.product;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.DELETE;
import retrofit2.http.Path;
import retrofit2.http.Body;

import java.util.List;

public interface RunApiService {

    @GET("runs")
    Call<List<Run>> getAllRuns();

    @GET("runs/{id}")
    Call<Run> getRunById(@Path("id") Integer id);

    @POST("runs")
    Call<Run> createRun(@Body Run run);

    @PUT("runs/{id}")
    Call<Run> updateRun(@Path("id") Integer id, @Body Run run);

    @DELETE("runs/{id}")
    Call<Void> deleteRun(@Path("id") Integer id);
}

