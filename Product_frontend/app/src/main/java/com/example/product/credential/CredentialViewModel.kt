package com.example.product.credential

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.product.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

data class CredentialDetails(
        val id:Int,
        val name:String,
        val password:String,
        val isLoggedIn:Int
        )
class CredentialViewModel (private val apiService: CredentialApiService= RetrofitClient.getCredentialClient("http://192.168.0.111:8080/api/")): ViewModel() {
    fun getCredentialById(id: Int, onResult: (CredentialDetails) -> Unit) {
        apiService.getCredentialById(id).enqueue(object : Callback<Credential> {
            override fun onResponse(call: Call<Credential>, response: Response<Credential>) {
                if (response.isSuccessful) {
                    val credential = response.body()
                    credential?.let {
                        val credentialDetails = CredentialDetails(
                            it.id,
                            it.name,
                            it.password,
                            it.isLoggedIn
                        )
                        onResult(credentialDetails)
                    } ?: run {
                        handleFailure("Credential not found")
                    }
                } else {
                    handleFailure("Failed to get credential: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<Credential>, t: Throwable) {
                handleFailure("Failed to get credential: ${t.message}")
            }
        })
    }

    fun updateCredential(id: Int, credentialDetails: CredentialDetails, onResult: (Boolean) -> Unit) {
        apiService.getCredentialById(id).enqueue(object : Callback<Credential> {
            override fun onResponse(call: Call<Credential>, response: Response<Credential>) {
                if (response.isSuccessful) {
                    val currentCredential = response.body()
                    if (currentCredential != null) {
                        val updatedCredential = Credential(
                            id,
                            credentialDetails.name,
                            credentialDetails.password,
                            credentialDetails.isLoggedIn
                        )

                        apiService.updateCredential(id, updatedCredential).enqueue(object : Callback<Credential> {
                            override fun onResponse(call: Call<Credential>, updateResponse: Response<Credential>) {
                                if (updateResponse.isSuccessful) {
                                    onResult(true)
                                } else {
                                    onResult(false)
                                    handleFailure("Failed to update credential: ${updateResponse.code()}")
                                }
                            }

                            override fun onFailure(call: Call<Credential>, t: Throwable) {
                                onResult(false)
                                handleFailure("Failed to update credential: ${t.message}")
                            }
                        })
                    } else {
                        handleFailure("Credential not found for update")
                    }
                } else {
                    handleFailure("Failed to get credential for update: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<Credential>, t: Throwable) {
                handleFailure("Failed to get credential for update: ${t.message}")
            }
        })
    }
}
private fun handleFailure(message: String) {
    // Handle failure
    Log.e("MaterialViewModel", message)
}