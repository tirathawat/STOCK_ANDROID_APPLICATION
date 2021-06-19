package com.thirathawat.mystock.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.thirathawat.mystock.R
import com.thirathawat.mystock.databinding.CustomProductListBinding
import com.thirathawat.mystock.models.ProductResponse
import com.thirathawat.mystock.services.APIClient

class CustomProductListAdapter(var productList: List<ProductResponse>?) : RecyclerView.Adapter<CustomProductListAdapter.ViewHolder>() {

    override fun getItemCount(): Int = productList?.size ?: 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            CustomProductListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding.root, binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val binding = holder.binding
        with(binding){
            productList?.let{ list ->
                val item = list[position]
                textViewName.text = item.name
                textViewDetail.text = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum."
                textViewPrice.text = "$ ${item.price}"
                textViewStock.text = "${item.stock} piece"
                Glide.with(imageViewProduct.context)
                    .load(APIClient.getImageURL() + item.image)
                    .error(R.drawable.logo)
                    .into(imageViewProduct)
            }
        }
    }

    inner class ViewHolder(view: View, val binding: CustomProductListBinding) :
        RecyclerView.ViewHolder(view) {

    }
}