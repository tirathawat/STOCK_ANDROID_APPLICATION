package com.thirathawat.mystock

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.thirathawat.mystock.adapters.CustomStockListAdapter
import com.thirathawat.mystock.databinding.FragmentStockBinding
import com.thirathawat.mystock.models.ProductResponse
import com.thirathawat.mystock.services.APIClient
import com.thirathawat.mystock.services.APIService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class StockFragment : Fragment() {

    private lateinit var binding: FragmentStockBinding
    private lateinit var customAdapter: CustomStockListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStockBinding.inflate(layoutInflater)
        customAdapter = CustomStockListAdapter(null)
        setupWidget()

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        feedNetwork()
    }

    private fun feedNetwork() {
        binding.swipeRefresh.isRefreshing = true
        APIClient.getClient().create(APIService::class.java).getProduct().let { call ->
            Log.d("application_network", call.request().url().toString())
            call.enqueue(object : Callback<List<ProductResponse>> {
                override fun onResponse(
                    call: Call<List<ProductResponse>>,
                    response: Response<List<ProductResponse>>
                ) {
                    if (response.isSuccessful) {
                        binding.recyclerview.adapter = CustomStockListAdapter(response.body())
                    } else {
                        context?.showToast(response.message())
                    }
                    binding.swipeRefresh.isRefreshing = false
                }

                override fun onFailure(call: Call<List<ProductResponse>>, t: Throwable) {
                    context?.showToast(t.message.toString())
                    binding.swipeRefresh.isRefreshing = false
                }
            })
        }
    }

    private fun setupWidget() {
        binding.recyclerview.apply {
            adapter = customAdapter
            layoutManager = LinearLayoutManager(context)
        }.also {
            it.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            it.setHasFixedSize(true)
        }
        binding.swipeRefresh.setOnRefreshListener {
            feedNetwork()
        }
    }
}