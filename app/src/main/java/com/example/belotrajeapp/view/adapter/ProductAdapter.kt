package com.example.belotrajeapp.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.belotrajeapp.R
import com.example.belotrajeapp.service.model.ProductModel
import com.example.belotrajeapp.view.listener.ProductListener
import com.example.belotrajeapp.view.viewHolder.ProductViewHolder

class ProductAdapter : RecyclerView.Adapter<ProductViewHolder>() {

    private lateinit var mListener: ProductListener
    private var mProductList: List<ProductModel> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val item = LayoutInflater.from(parent.context).inflate(R.layout.row_home, parent,false)
        return ProductViewHolder(item, mListener)
    }

    override fun getItemCount(): Int {
        return mProductList.count()
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(mProductList[position])

    }

    fun updateProducts(list: List<ProductModel>){
        mProductList = list
        notifyDataSetChanged()
    }

    fun attachListener(listener: ProductListener){
        mListener = listener
    }
}