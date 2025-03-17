package com.example.product.product
import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import androidx.lifecycle.ViewModel
import com.example.product.RetrofitClient

data class ProductDetails(
    val id: Int,
    val mCompany: String,
    val mDestination: String,
    val mQualityLevel: String,
    val mTime: String,
    val materialId1: Int,
    val materialId2: Int,
    val wsName1: String,
    val wsStartTime1: String,
    val wsEndTime1: String,
    val wsName2: String,
    val wsStartTime2: String,
    val wsEndTime2: String,
    val wsName3: String,
    val wsStartTime3: String,
    val wsEndTime3: String,
    val inspectionStartTime: String,
    val inspectionEndTime: String,
    val inspectionResult: String,
    val responsiblePerson: String,
)

class ProductViewModel(private val apiService: ProductApiService = RetrofitClient.getProductClient("http://192.168.0.111:8080/api/")) : ViewModel() {

    fun getProductById(id: Int, onResult: (ProductDetails) -> Unit) {
        apiService.getProductById(id).enqueue(object : Callback<Product> {
            override fun onResponse(call: Call<Product>, response: Response<Product>) {
                if (response.isSuccessful) {
                    val product = response.body()
                    if (product != null) {
                        val productDetails = ProductDetails(
                            product.id,
                            product.mCompany,
                            product.mDestination,
                            product.mQualityLevel,
                            product.mTime,
                            product.materialId1,
                            product.materialId2,
                            product.wsName1,
                            product.wsStartTime1,
                            product.wsEndTime1,
                            product.wsName2,
                            product.wsStartTime2,
                            product.wsEndTime2,
                            product.wsName3,
                            product.wsStartTime3,
                            product.wsEndTime3,
                            product.inspectionStartTime,
                            product.inspectionEndTime,
                            product.inspectionResult,
                            product.responsiblePerson
                        )
                        onResult(productDetails)
                    } else {
                        onResult(ProductDetails(0, "ID不存在！", "", "", "", 0, 0, "", "", "", "", "", "", "", "", "", "", "", "", ""))
                    }
                } else {
                    onResult(ProductDetails(0, "ID不存在！", "", "", "", 0, 0, "", "", "", "", "", "", "", "", "", "", "", "", ""))
                }
            }

            override fun onFailure(call: Call<Product>, t: Throwable) {
                onResult(ProductDetails(0, "", "Failure: ${t.message}", "", "", 0, 0, "", "", "", "", "", "", "", "", "", "", "", "", ""))
            }
        })
    }

    // Function to create a new product
    fun createProduct(productDetails: ProductDetails, onResult: (ProductDetails) -> Unit) {
        val product = Product(
            productDetails.id,
            productDetails.mCompany,
            productDetails.mDestination,
            productDetails.mQualityLevel,
            productDetails.mTime,
            productDetails.materialId1,
            productDetails.materialId2,
            productDetails.wsName1,
            productDetails.wsStartTime1,
            productDetails.wsEndTime1,
            productDetails.wsName2,
            productDetails.wsStartTime2,
            productDetails.wsEndTime2,
            productDetails.wsName3,
            productDetails.wsStartTime3,
            productDetails.wsEndTime3,
            productDetails.inspectionStartTime,
            productDetails.inspectionEndTime,
            productDetails.inspectionResult,
            productDetails.responsiblePerson,
            null
        )

        apiService.createProduct(product).enqueue(object : Callback<Product> {
            override fun onResponse(call: Call<Product>, response: Response<Product>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        onResult(ProductDetails(
                            it.id,
                            it.mCompany,
                            it.mDestination,
                            it.mQualityLevel,
                            it.mTime,
                            it.materialId1,
                            it.materialId2,
                            it.wsName1,
                            it.wsStartTime1,
                            it.wsEndTime1,
                            it.wsName2,
                            it.wsStartTime2,
                            it.wsEndTime2,
                            it.wsName3,
                            it.wsStartTime3,
                            it.wsEndTime3,
                            it.inspectionStartTime,
                            it.inspectionEndTime,
                            it.inspectionResult,
                            it.responsiblePerson
                        ))
                    }
                } else {
                    onResult(ProductDetails(0, "", "", "", "", 0, 0, "", "", "", "", "", "", "", "", "", "", "", "", ""))
                }
            }

            override fun onFailure(call: Call<Product>, t: Throwable) {
                onResult(ProductDetails(0, "", "", "", "", 0, 0, "", "", "", "", "", "", "", "", "", "", "", "", ""))
            }
        })
    }

    // Function to delete a product by ID
    fun deleteProduct(id: Int, onResult: (Boolean) -> Unit) {
        apiService.deleteProduct(id).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    onResult(true)
                } else {
                    onResult(false)
                    Log.e("ProductViewModel", "Failed to delete product: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                onResult(false)
                Log.e("ProductViewModel", "Error when trying to delete product: ${t.message}")
            }
        })
    }

    // Function to update a product by ID
    fun updateProduct(id: Int, productDetails: ProductDetails, onResult: (ProductDetails) -> Unit) {
        apiService.getProductById(id).enqueue(object : Callback<Product> {
            override fun onResponse(call: Call<Product>, response: Response<Product>) {
                if (response.isSuccessful) {
                    val currentProduct = response.body()
                    if (currentProduct != null) {
                        val updatedProduct = Product(
                            productDetails.id,
                            productDetails.mCompany,
                            productDetails.mDestination,
                            productDetails.mQualityLevel,
                            productDetails.mTime,
                            productDetails.materialId1,
                            productDetails.materialId2,
                            productDetails.wsName1,
                            productDetails.wsStartTime1,
                            productDetails.wsEndTime1,
                            productDetails.wsName2,
                            productDetails.wsStartTime2,
                            productDetails.wsEndTime2,
                            productDetails.wsName3,
                            productDetails.wsStartTime3,
                            productDetails.wsEndTime3,
                            productDetails.inspectionStartTime,
                            productDetails.inspectionEndTime,
                            productDetails.inspectionResult,
                            productDetails.responsiblePerson,
                            null
                        )

                        apiService.updateProduct(id, updatedProduct).enqueue(object : Callback<Product> {
                            override fun onResponse(call: Call<Product>, updateResponse: Response<Product>) {
                                if (updateResponse.isSuccessful) {
                                    updateResponse.body()?.let {
                                        onResult(ProductDetails(
                                            it.id,
                                            it.mCompany,
                                            it.mDestination,
                                            it.mQualityLevel,
                                            it.mTime,
                                            it.materialId1,
                                            it.materialId2,
                                            it.wsName1,
                                            it.wsStartTime1,
                                            it.wsEndTime1,
                                            it.wsName2,
                                            it.wsStartTime2,
                                            it.wsEndTime2,
                                            it.wsName3,
                                            it.wsStartTime3,
                                            it.wsEndTime3,
                                            it.inspectionStartTime,
                                            it.inspectionEndTime,
                                            it.inspectionResult,
                                            it.responsiblePerson,
                                        ))
                                    }
                                } else {
                                    onResult(ProductDetails(0, "", "", "", "", 0, 0, "", "", "", "", "", "", "", "", "", "", "", "", ""))
                                }
                            }

                            override fun onFailure(call: Call<Product>, t: Throwable) {
                                onResult(ProductDetails(0, "", "", "", "", 0, 0, "", "", "", "", "", "", "", "", "", "", "", "", ""))
                            }
                        })
                    } else {
                        onResult(ProductDetails(0, "", "", "", "", 0, 0, "", "", "", "", "", "", "", "", "", "", "", "", ""))
                    }
                } else {
                    onResult(ProductDetails(0, "", "", "", "", 0, 0, "", "", "", "", "", "", "", "", "", "", "", "", ""))
                }
            }

            override fun onFailure(call: Call<Product>, t: Throwable) {
                onResult(ProductDetails(0, "", "", "", "", 0, 0, "", "", "", "", "", "", "", "", "", "", "", "", ""))
            }
        })
    }
}


