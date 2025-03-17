package com.example.product.material

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.product.RetrofitClient
import com.example.product.RunApiService
import com.example.product.product.ProductDetails
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
data class MaterialDetails(
    val id: Int,
    val title: String,
    val type: String,
    val supplier: String,
    val passRate: Float,
    val goodRate: Float,
    val qualityLevel: String,
    val inspector: String
)

class MaterialViewModel(private val apiService: MaterialApiService = RetrofitClient.getMaterialClient("http://192.168.0.111:8080/api/")) : ViewModel() {
    private val _materialList = MutableStateFlow<List<MaterialDetails>>(emptyList())
    val materialList: StateFlow<List<MaterialDetails>> = _materialList
    init {
        getAllMaterials() // Fetch materials when ViewModel is initialized
    }
    fun getAllMaterials() {
        apiService.getAllMaterials().enqueue(object : Callback<List<Material>> {
            override fun onResponse(call: Call<List<Material>>, response: Response<List<Material>>) {
                if (response.isSuccessful) {
                    val materials = response.body()
                    materials?.let {
                        val materialDetailsList = it.map { material ->
                            MaterialDetails(
                                material.id,
                                material.title,
                                material.type,
                                material.supplier,
                                material.passRate,
                                material.goodRate,
                                material.qualityLevel,
                                material.inspector
                            )
                        }
                        _materialList.value = materialDetailsList
                    }
                } else {
                    handleFailure("Failed to get materials: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<Material>>, t: Throwable) {
                handleFailure("Failed to get materials: ${t.message}")
            }
        })
    }
    fun getMaterialById(id: Int,onResult: (MaterialDetails) -> Unit) {
        apiService.getMaterialById(id).enqueue(object : Callback<Material> {
            override fun onResponse(call: Call<Material>, response: Response<Material>) {
                if (response.isSuccessful) {
                    val material = response.body()
                    material?.let {
                        val materialDetails = MaterialDetails(
                            material.id,
                            material.title,
                            material.type,
                            material.supplier,
                            material.passRate,
                            material.goodRate,
                            material.qualityLevel,
                            material.inspector,
                        )
                        (onResult(materialDetails))
                    } ?: run {
                        handleFailure("未找到此材料！")
                    }
                } else {
                    handleFailure("Failed to get material: ${response.code()}")
                }
            }
            override fun onFailure(call: Call<Material>, t: Throwable) {
                handleFailure("Failed to get material: ${t.message}")
            }
        })
    }

    fun createMaterial(materialDetails: MaterialDetails, onResult: (MaterialDetails) -> Unit) {
        val material = Material(
            materialDetails.id,
            materialDetails.title,
            materialDetails.type,
            materialDetails.supplier,
            materialDetails.passRate,
            materialDetails.goodRate,
            materialDetails.qualityLevel,
            materialDetails.inspector,
            null
        )

        apiService.createMaterial(material).enqueue(object : Callback<Material> {
            override fun onResponse(call: Call<Material>, response: Response<Material>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        onResult(MaterialDetails(
                            it.id,
                            it.title,
                            it.type,
                            it.supplier,
                            it.passRate,
                            it.goodRate,
                            it.qualityLevel,
                            it.inspector
                        ))
                    } ?: run {
                        handleFailure("Failed to create material: No response body")
                    }
                } else {
                    handleFailure("Failed to create material: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<Material>, t: Throwable) {
                handleFailure("Failed to create material: ${t.message}")
            }
        })
    }

    fun updateMaterial(id: Int, materialDetails: MaterialDetails, onResult: (MaterialDetails) -> Unit) {
        apiService.getMaterialById(id).enqueue(object : Callback<Material> {
            override fun onResponse(call: Call<Material>, response: Response<Material>) {
                if (response.isSuccessful) {
                    val currentMaterial = response.body()
                    if (currentMaterial != null) {
                        val updatedMaterial = Material(
                            id,
                            materialDetails.title,
                            materialDetails.type,
                            materialDetails.supplier,
                            materialDetails.passRate,
                            materialDetails.goodRate,
                            materialDetails.qualityLevel,
                            materialDetails.inspector,
                            null
                        )

                        apiService.updateMaterial(id, updatedMaterial).enqueue(object : Callback<Material> {
                            override fun onResponse(call: Call<Material>, updateResponse: Response<Material>) {
                                if (updateResponse.isSuccessful) {
                                    updateResponse.body()?.let {
                                        onResult(MaterialDetails(
                                            it.id,
                                            it.title,
                                            it.type,
                                            it.supplier,
                                            it.passRate,
                                            it.goodRate,
                                            it.qualityLevel,
                                            it.inspector
                                        ))
                                    } ?: run {
                                        handleFailure("Failed to update material: No response body")
                                    }
                                } else {
                                    handleFailure("Failed to update material: ${updateResponse.code()}")
                                }
                            }

                            override fun onFailure(call: Call<Material>, t: Throwable) {
                                handleFailure("Failed to update material: ${t.message}")
                            }
                        })
                    } else {
                        handleFailure("Material not found for update")
                    }
                } else {
                    handleFailure("Failed to get material for update: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<Material>, t: Throwable) {
                handleFailure("Failed to get material for update: ${t.message}")
            }
        })
    }

    fun deleteMaterial(id: Int, onResult: (Boolean) -> Unit) {
        apiService.deleteMaterial(id).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    onResult(true)
                } else {
                    onResult(false)
                    Log.e("MaterialViewModel", "Failed to delete material: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                onResult(false)
                Log.e("MaterialViewModel", "Error when trying to delete material: ${t.message}")
            }
        })
    }

    private fun handleFailure(message: String) {
        // Handle failure
        Log.e("MaterialViewModel", message)
    }
}
