package com.example.product.product;

import com.example.product.Run;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ProductApiService {
    @GET("products")
    Call<List<Product>> getAllProducts();

    @GET("products/{id}")
    Call<Product> getProductById(@Path("id") Integer id);

    @POST("products")
    Call<Product> createProduct(@Body Product product);

    @PUT("products/{id}")
    Call<Product> updateProduct(@Path("id") Integer id, @Body Product product);

    @DELETE("products/{id}")
    Call<Void> deleteProduct(@Path("id") Integer id);
}
