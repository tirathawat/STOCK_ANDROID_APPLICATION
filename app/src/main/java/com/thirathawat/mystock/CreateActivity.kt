package com.thirathawat.mystock

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.thirathawat.mystock.databinding.ActivityCreateBinding
import com.thirathawat.mystock.models.ProductRequest
import com.thirathawat.mystock.services.APIClient
import com.thirathawat.mystock.services.APIService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CreateActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCreateBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupWidget()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupWidget() {
        binding.buttonSubmit.setOnClickListener {
            val name = binding.editTextName.text.toString()
            val price = binding.editTextPrice.text.toString()
            val stock = binding.editTextStock.text.toString()

            val textToNumber: (String) -> Int = {text -> if (text.isEmpty()) 0 else text.toInt()}
            ProductRequest(name, textToNumber(price), textToNumber(stock)).run { addProduct(this) }
        }
    }

    private fun addProduct(product: ProductRequest) {

        APIClient.getClient().create(APIService::class.java).addProduct(product).enqueue(object : Callback<Any> {
            override fun onResponse(
                call: Call<Any>,
                response: Response<Any>
            ) {
                if (response.isSuccessful) {
                    finish()
                }else {
                    showToast("Add Product Failure")
                }
            }

            override fun onFailure(call: Call<Any>, t: Throwable) {
               showToast(t.message.toString())
            }
        })
    }
}