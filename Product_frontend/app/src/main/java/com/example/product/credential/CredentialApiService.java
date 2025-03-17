package com.example.product.credential;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface CredentialApiService {

@GET("credentials/{id}")
Call<Credential> getCredentialById(@Path("id") Integer id);
    @PUT("credentials/{id}")
    Call<Credential> updateCredential(@Path("id") Integer id, @Body Credential credential);
}
