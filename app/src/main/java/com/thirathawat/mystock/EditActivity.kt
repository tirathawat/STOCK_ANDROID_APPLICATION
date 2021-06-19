package com.thirathawat.mystock

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.thirathawat.mystock.databinding.ActivityEditBinding
import com.thirathawat.mystock.models.ProductRequest
import com.thirathawat.mystock.models.ProductResponse
import com.thirathawat.mystock.services.APIClient
import com.thirathawat.mystock.services.APIService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditActivity : AppCompatActivity() {
    private var product: ProductResponse? = null
    private lateinit var binding: ActivityEditBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditBinding.inflate(layoutInflater)
        setContentView(binding.root)
        product = intent.getParcelableExtra<ProductResponse>(INTENT_PRODUCT)
        setupWidget()
    }

    private fun setupWidget() {
        product?.let { product ->
            binding.editTextName.setText(product.name)
            binding.editTextPrice.setText(product.price.toString())
            binding.editTextStock.setText(product.stock.toString())

            binding.buttonSubmit.setOnClickListener {
                val name = binding.editTextName.text.toString()
                val price = binding.editTextPrice.text.toString()
                val stock = binding.editTextStock.text.toString()

                val textToNumber: (String) -> Int = {text -> if (text.isEmpty()) 0 else text.toInt()}
                ProductRequest(name, textToNumber(price), textToNumber(stock)).run { editProduct(product.id, this) }
            }

            binding.buttonDelete.setOnClickListener {
                deleteProduct(product.id)
            }
        }

    }

    private fun editProduct(id: Int, product: ProductRequest) {

        APIClient.getClient().create(APIService::class.java).editProduct(id,product).enqueue(object :
            Callback<Any> {
            override fun onResponse(
                call: Call<Any>,
                response: Response<Any>
            ) {
                if (response.isSuccessful) {
                    finish()
                }else {
                    showToast("Edit Product Failure")
                }
            }

            override fun onFailure(call: Call<Any>, t: Throwable) {
                showToast(t.message.toString())
            }
        })
    }

    private fun deleteProduct(id: Int) {

        APIClient.getClient().create(APIService::class.java).deleteProduct(id).enqueue(object : Callback<Any> {
            override fun onResponse(
                call: Call<Any>,
                response: Response<Any>
            ) {
                if (response.isSuccessful) {
                    finish()
                }else {
                    showToast("Delete Product Failure")
                }
            }

            override fun onFailure(call: Call<Any>, t: Throwable) {
                showToast(t.message.toString())
            }
        })
    }
}