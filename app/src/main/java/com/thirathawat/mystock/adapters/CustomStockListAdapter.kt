package com.thirathawat.mystock.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.thirathawat.mystock.EditActivity
import com.thirathawat.mystock.INTENT_PRODUCT
import com.thirathawat.mystock.R
import com.thirathawat.mystock.databinding.CustomStockListBinding
import com.thirathawat.mystock.models.ProductResponse
import com.thirathawat.mystock.services.APIClient

class CustomStockListAdapter(private var stockList: List<ProductResponse>?) : RecyclerView.Adapter<CustomStockListAdapter.ViewHolder>() {

    override fun getItemCount(): Int = stockList?.size ?: 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CustomStockListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding.root, binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val binding = holder.binding
        with(binding){
            stockList?.let{ list ->
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

    inner class ViewHolder(view: View, val binding: CustomStockListBinding) : RecyclerView.ViewHolder(view) {
        init{
            binding.buttonEdit.setOnClickListener(){
                val item = stockList?.let { list -> list[adapterPosition] }
                Intent(view.context, EditActivity::class.java).apply {
                    putExtra(INTENT_PRODUCT, item)
                }.run {
                    view.context.startActivity(this)
                }
            }
        }
    }
}