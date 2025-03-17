package com.example.product;

import com.example.product.barcode.LocalDateTimeDeserializer;
import com.example.product.credential.Credential;
import com.example.product.credential.CredentialApiService;
import com.example.product.material.Material;
import com.example.product.material.MaterialApiService;
import com.example.product.product.Product;
import com.example.product.product.ProductApiService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializer;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

public class RetrofitClient {
    private static Retrofit runRetrofit = null;
    private static Retrofit productRetrofit = null;
    private static Retrofit materialRetrofit =null;
    private static Retrofit credentialRetrofit = null;
    public static RunApiService getRunClient(String baseUrl) {
        if (runRetrofit == null) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(LocalDateTime.class, (JsonSerializer<LocalDateTime>) (src, typeOfSrc, context) -> {
                        return new JsonPrimitive(DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(src)); // Format to ISO_LOCAL_DATE_TIME
                    })
                    .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeDeserializer())
                    .create();

            runRetrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(client)
                    .build();
        }
        return runRetrofit.create(RunApiService.class);
    }

    public static ProductApiService getProductClient(String baseUrl) {
        if (productRetrofit == null) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(LocalDateTime.class, (JsonSerializer<LocalDateTime>) (src, typeOfSrc, context) -> {
                        return new JsonPrimitive(DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(src)); // Format to ISO_LOCAL_DATE_TIME
                    })
                    .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeDeserializer())
                    .create();

            productRetrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(client)
                    .build();
        }
        return productRetrofit.create(ProductApiService.class);
    }
    public static MaterialApiService getMaterialClient(String baseUrl) {
        if (materialRetrofit == null) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(LocalDateTime.class, (JsonSerializer<LocalDateTime>) (src, typeOfSrc, context) -> {
                        return new JsonPrimitive(DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(src)); // Format to ISO_LOCAL_DATE_TIME
                    })
                    .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeDeserializer())
                    .create();

            materialRetrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(client)
                    .build();
        }
        return materialRetrofit.create(MaterialApiService.class);
    }
    public static CredentialApiService getCredentialClient(String baseUrl) {
        if (credentialRetrofit == null) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(LocalDateTime.class, (JsonSerializer<LocalDateTime>) (src, typeOfSrc, context) -> {
                        return new JsonPrimitive(DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(src)); // Format to ISO_LOCAL_DATE_TIME
                    })
                    .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeDeserializer())
                    .create();

            credentialRetrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(client)
                    .build();
        }
        return credentialRetrofit.create(CredentialApiService.class);
    }
}
