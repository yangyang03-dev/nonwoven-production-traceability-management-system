package com.example.product
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.product.barcode.Location
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDateTime

data class RunDetails(val title: String,val miles: Int)
class RunViewModel(private val apiService: RunApiService = RetrofitClient.getRunClient("http://192.168.43.120:8080/api/")) : ViewModel() {
    fun getRunById(id: Int, onResult: (RunDetails) -> Unit) {
        apiService.getRunById(id).enqueue(object : Callback<Run> {
            override fun onResponse(call: Call<Run>, response: Response<Run>) {
                if (response.isSuccessful) {
                    val Responsee = response.body()
                    if (Responsee!= null){
                        onResult(RunDetails(Responsee.title ?: "No title found", Responsee.miles))
                    }
                    else{
                        onResult(RunDetails("Error: ${response.code()}", 0))
                    }
                } else {
                    onResult(RunDetails("Error: ${response.code()}",0))
                }
            }

            override fun onFailure(call: Call<Run>, t: Throwable) {
                onResult(RunDetails("Failure: ${t.message}",0))
            }
        })

    }
    fun createRun(runDetails: RunDetails, id: Int, onResult: (RunDetails) -> Unit) {
        val run = Run(
            id,
            runDetails.title,
            "",
            "",
            runDetails.miles,
            Location.OUTDOOR,
            null
        )
        apiService.createRun(run).enqueue(object : Callback<Run> {
            override fun onResponse(call: Call<Run>, response: Response<Run>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        onResult(RunDetails(it.title, it.miles))
                    } ?: run {
                        onResult(RunDetails("Error: No data after creation", 0))
                    }
                } else {
                    onResult(RunDetails("Error: ${response.code()} - ${response.message()}", 0))
                }
            }

            override fun onFailure(call: Call<Run>, t: Throwable) {
                onResult(RunDetails("Failure: ${t.message}", 0))
            }
        })
    }
    fun deleteRun(id: Int, onResult: (Boolean) -> Unit) {
        apiService.deleteRun(id).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    onResult(true)
                } else {
                    onResult(false)
                    Log.e("DeleteRun", "Failed to delete run: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                onResult(false)
                Log.e("DeleteRun", "Error when trying to delete run: ${t.message}")
            }
        })
    }
    fun updateRun(id: Int, title: String?, miles: Int?, onResult: (RunDetails) -> Unit) {
        // Fetch the current run first to get other properties we're not updating
        apiService.getRunById(id).enqueue(object : Callback<Run> {
            override fun onResponse(call: Call<Run>, response: Response<Run>) {
                if (response.isSuccessful) {
                    val currentRun = response.body()
                    if (currentRun != null) {
                        // Update the title and miles if new values are provided, otherwise use existing values
                        val updatedRun = Run(
                            currentRun.id,
                            title ?: currentRun.title,
                            currentRun.startedOn,
                            currentRun.completedOn,
                            miles ?: currentRun.miles,
                            currentRun.location,
                            currentRun.version
                        )

                        // Now call the update API
                        apiService.updateRun(id, updatedRun).enqueue(object : Callback<Run> {
                            override fun onResponse(call: Call<Run>, updateResponse: Response<Run>) {
                                if (updateResponse.isSuccessful) {
                                    updateResponse.body()?.let {
                                        onResult(RunDetails(it.title, it.miles))
                                    } ?: run {
                                        onResult(RunDetails("Error: No data after update", 0))
                                    }
                                } else {
                                    onResult(RunDetails("Error: ${updateResponse.code()} - ${updateResponse.message()}", 0))
                                }
                            }

                            override fun onFailure(call: Call<Run>, t: Throwable) {
                                onResult(RunDetails("Failure: ${t.message}", 0))
                            }
                        })
                    } else {
                        onResult(RunDetails("Error: Run not found", 0))
                    }
                } else {
                    onResult(RunDetails("Error: ${response.code()} - ${response.message()}", 0))
                }
            }

            override fun onFailure(call: Call<Run>, t: Throwable) {
                onResult(RunDetails("Failure: ${t.message}", 0))
            }
        })
    }
}

