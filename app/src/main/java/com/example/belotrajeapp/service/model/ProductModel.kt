package com.example.belotrajeapp.service.model

data class ProductModel (
    var id: Int = 0,
    var name: String,
    var description: String,
    var price: String,
    var size: String,
    var category: String,
    var img: ByteArray
    )