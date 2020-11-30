package com.example.belotrajeapp.service.repository

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.belotrajeapp.service.constants.DatabaseConstants


class ProductDatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_TABLE_PRODUCT)
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }

    companion object{
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "Product.db"

        private const val CREATE_TABLE_PRODUCT =
        ("create table " + DatabaseConstants.PRODUCT.TABLE_NAME + " ("
                + DatabaseConstants.PRODUCT.COLUMNS.ID + " integer primary key autoincrement, "
                + DatabaseConstants.PRODUCT.COLUMNS.NAME + " text, "
                + DatabaseConstants.PRODUCT.COLUMNS.DESCRIPTION + " text, "
                + DatabaseConstants.PRODUCT.COLUMNS.PRICE + " text, "
                + DatabaseConstants.PRODUCT.COLUMNS.SIZE + " text, "
                + DatabaseConstants.PRODUCT.COLUMNS.CATEGORY + " text, "
                + DatabaseConstants.PRODUCT.COLUMNS.IMG + " blob);")
    }
}