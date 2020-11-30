package com.example.belotrajeapp.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.belotrajeapp.service.model.ProductModel
import com.example.belotrajeapp.service.repository.ProductRepository

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private val mProductRepository = ProductRepository.getInstance(application.applicationContext)

    private val mProductList= MutableLiveData<List<ProductModel>>()
    val productList: LiveData<List<ProductModel>> =  mProductList

    fun load(){
        mProductList.value = mProductRepository.getAll()
    }

    fun delete(id: Int){
       mProductRepository.delete(id)
    }
}