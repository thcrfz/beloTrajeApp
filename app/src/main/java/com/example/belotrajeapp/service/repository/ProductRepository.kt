package com.example.belotrajeapp.service.repository

import android.content.ContentValues
import android.content.Context
import com.example.belotrajeapp.service.constants.DatabaseConstants
import com.example.belotrajeapp.service.model.ProductModel

class ProductRepository private constructor(context: Context) {
    //Pegar banco de dados
    private var mProductDatabaseHelper: ProductDatabaseHelper = ProductDatabaseHelper(context)

    //singleton
    companion object {
        private lateinit var repository: ProductRepository

        //Instânciar a classe privada
        fun getInstance(context: Context): ProductRepository {
            if (!::repository.isInitialized) {
                repository = ProductRepository(context)
            }
            return repository
        }
    }

    //CRUD
    fun  get(id: Int): ProductModel?{

        var product: ProductModel? = null

        return try {
            val db = mProductDatabaseHelper.readableDatabase

            val projection = arrayOf(
                DatabaseConstants.PRODUCT.COLUMNS.NAME,
                DatabaseConstants.PRODUCT.COLUMNS.DESCRIPTION,
                DatabaseConstants.PRODUCT.COLUMNS.PRICE,
                DatabaseConstants.PRODUCT.COLUMNS.SIZE,
                DatabaseConstants.PRODUCT.COLUMNS.CATEGORY,
                DatabaseConstants.PRODUCT.COLUMNS.IMG
            )

            val selection = DatabaseConstants.PRODUCT.COLUMNS.ID + " = ?"
            val args = arrayOf(id.toString())

            val cursor = db.query(
                DatabaseConstants.PRODUCT.TABLE_NAME,
                projection,
                selection,
                args,
                null,
                null,
                null
            )

            if (cursor != null && cursor.count > 0){
                cursor.moveToFirst()

                val name = cursor.getString(cursor.getColumnIndex(DatabaseConstants.PRODUCT.COLUMNS.NAME))
                val description = cursor.getString(cursor.getColumnIndex(DatabaseConstants.PRODUCT.COLUMNS.DESCRIPTION))
                val price = cursor.getString(cursor.getColumnIndex(DatabaseConstants.PRODUCT.COLUMNS.PRICE))
                val size = cursor.getString(cursor.getColumnIndex(DatabaseConstants.PRODUCT.COLUMNS.SIZE))
                val category = cursor.getString(cursor.getColumnIndex(DatabaseConstants.PRODUCT.COLUMNS.CATEGORY))
                val img = cursor.getBlob(cursor.getColumnIndex(DatabaseConstants.PRODUCT.COLUMNS.IMG))

                product = ProductModel(id, name, description, price, size, category, img)
            }
            cursor.close()

            product
        }catch (e: Exception){
            product
        }
    }

    fun save(product: ProductModel): Boolean {
        return try {
            val db = mProductDatabaseHelper.writableDatabase

            val contentValues = ContentValues()
            contentValues.put(DatabaseConstants.PRODUCT.COLUMNS.NAME, product.name)
            contentValues.put(DatabaseConstants.PRODUCT.COLUMNS.DESCRIPTION, product.description)
            contentValues.put(DatabaseConstants.PRODUCT.COLUMNS.PRICE, product.price)
            contentValues.put(DatabaseConstants.PRODUCT.COLUMNS.SIZE, product.size)
            contentValues.put(DatabaseConstants.PRODUCT.COLUMNS.CATEGORY, product.category)
            contentValues.put(DatabaseConstants.PRODUCT.COLUMNS.IMG, product.img)

            db.insert(DatabaseConstants.PRODUCT.TABLE_NAME, null, contentValues)
            true
        }catch (e: Exception){
            false
        }
    }

    fun update(product: ProductModel): Boolean {
        return try {
            val db = mProductDatabaseHelper.writableDatabase

            val contentValues = ContentValues()
            contentValues.put(DatabaseConstants.PRODUCT.COLUMNS.NAME, product.name)
            contentValues.put(DatabaseConstants.PRODUCT.COLUMNS.DESCRIPTION, product.description)
            contentValues.put(DatabaseConstants.PRODUCT.COLUMNS.PRICE, product.price)
            contentValues.put(DatabaseConstants.PRODUCT.COLUMNS.SIZE, product.size)
            contentValues.put(DatabaseConstants.PRODUCT.COLUMNS.CATEGORY, product.category)
            contentValues.put(DatabaseConstants.PRODUCT.COLUMNS.IMG, product.img)

            val selection = DatabaseConstants.PRODUCT.COLUMNS.ID + " = ?"
            val args = arrayOf(product.id.toString())

            db.update(DatabaseConstants.PRODUCT.TABLE_NAME, contentValues, selection, args)
            true
        }catch (e: Exception){
            false
        }
    }

    fun delete(id: Int): Boolean {
        return try {
            val db = mProductDatabaseHelper.writableDatabase

            val selection = DatabaseConstants.PRODUCT.COLUMNS.ID + " = ?"
            val args = arrayOf(id.toString())

            db.delete(DatabaseConstants.PRODUCT.TABLE_NAME, selection, args)
            true
        }catch (e: Exception){
            false
        }

    }

    //Listar na home
    fun getAll(): List<ProductModel> {
        val list: MutableList<ProductModel> = ArrayList()

        return try {
            val db = mProductDatabaseHelper.readableDatabase

            val projection = arrayOf(
                DatabaseConstants.PRODUCT.COLUMNS.ID,
                DatabaseConstants.PRODUCT.COLUMNS.NAME,
                DatabaseConstants.PRODUCT.COLUMNS.DESCRIPTION,
                DatabaseConstants.PRODUCT.COLUMNS.PRICE,
                DatabaseConstants.PRODUCT.COLUMNS.SIZE,
                DatabaseConstants.PRODUCT.COLUMNS.CATEGORY,
                DatabaseConstants.PRODUCT.COLUMNS.IMG
            )

            val cursor = db.query(
                DatabaseConstants.PRODUCT.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
            )

            if (cursor != null && cursor.count > 0){
               while (cursor.moveToNext()) {
                   val id =
                       cursor.getInt(cursor.getColumnIndex(DatabaseConstants.PRODUCT.COLUMNS.ID))
                   val name =
                       cursor.getString(cursor.getColumnIndex(DatabaseConstants.PRODUCT.COLUMNS.NAME))
                   val description =
                       cursor.getString(cursor.getColumnIndex(DatabaseConstants.PRODUCT.COLUMNS.DESCRIPTION))
                   val price =
                       cursor.getString(cursor.getColumnIndex(DatabaseConstants.PRODUCT.COLUMNS.PRICE))
                   val size =
                       cursor.getString(cursor.getColumnIndex(DatabaseConstants.PRODUCT.COLUMNS.SIZE))
                   val category =
                       cursor.getString(cursor.getColumnIndex(DatabaseConstants.PRODUCT.COLUMNS.CATEGORY))
                   val img =
                       cursor.getBlob(cursor.getColumnIndex(DatabaseConstants.PRODUCT.COLUMNS.IMG))
                   val product = ProductModel(id, name, description, price, size, category, img)
                   list.add(product)
               }
            }
            cursor.close()

            list
        }catch (e: Exception){
            list
        }
    }

}