package com.example.belotrajeapp.viewModel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.belotrajeapp.service.repository.ProductRepository
import com.example.belotrajeapp.service.model.ProductModel

class ProductFormViewModel(application: Application) : AndroidViewModel(application) {

    private val mContext = application.applicationContext
    private val mProductRepository: ProductRepository = ProductRepository.getInstance(mContext)

    private var mSaveProduct = MutableLiveData<Boolean>()
    val saveProduct: LiveData<Boolean> = mSaveProduct

    private var mProduct = MutableLiveData<ProductModel>()
    val product: LiveData<ProductModel> = mProduct

    fun save(
        id: Int,
        name: String,
        description: String,
        price: String,
        size: String,
        category: String,
        img: ByteArray
    ) {
        val product = ProductModel(id, name, description, price, size, category, img)

        if (id == 0) {
            mSaveProduct.value = mProductRepository.save(product)
        } else {
            mSaveProduct.value = mProductRepository.update(product)
        }


    }

    fun load(id: Int) {
        mProduct.value = mProductRepository.get(id)
    }

}