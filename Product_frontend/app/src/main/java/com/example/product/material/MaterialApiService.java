package com.example.product.material;

import com.example.product.material.Material;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface MaterialApiService {
    @GET("materials")
    Call<List<Material>> getAllMaterials();

    @GET("materials/{id}")
    Call<Material> getMaterialById(@Path("id") Integer id);

    @POST("materials")
    Call<Material> createMaterial(@Body Material material);

    @PUT("materials/{id}")
    Call<Material> updateMaterial(@Path("id") Integer id, @Body Material material);

    @DELETE("materials/{id}")
    Call<Void> deleteMaterial(@Path("id") Integer id);
}