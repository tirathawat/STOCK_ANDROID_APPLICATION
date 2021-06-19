package com.thirathawat.mystock

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.thirathawat.mystock.adapters.CustomProductListAdapter
import com.thirathawat.mystock.databinding.FragmentProductBinding
import com.thirathawat.mystock.models.ProductResponse
import com.thirathawat.mystock.services.APIClient
import com.thirathawat.mystock.services.APIService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ProductFragment : Fragment() {

    private lateinit var binding: FragmentProductBinding
    private lateinit var customAdapter: CustomProductListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProductBinding.inflate(layoutInflater)
        customAdapter = CustomProductListAdapter(null)
        setupWidget()
        feedNetwork()
        return binding.root
    }

    private fun setupWidget() {
        binding.recyclerview.apply {
            adapter = customAdapter
            layoutManager = GridLayoutManager(context, 2)

        }.also {
            it.addItemDecoration(GridSpacingItemDecoration(2, 20, true))
            it.setHasFixedSize(true)
        }
        binding.swipeRefresh.setOnRefreshListener {
            feedNetwork()
        }
    }

    private fun feedNetwork() {
        binding.swipeRefresh.isRefreshing = true
        APIClient.getClient().create(APIService::class.java).getProduct().let { call ->
            call.enqueue(object : Callback<List<ProductResponse>> {
                override fun onResponse(
                    call: Call<List<ProductResponse>>,
                    response: Response<List<ProductResponse>>
                ) {
                    if (response.isSuccessful) {
                        binding.recyclerview.adapter = CustomProductListAdapter(response.body())
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
}