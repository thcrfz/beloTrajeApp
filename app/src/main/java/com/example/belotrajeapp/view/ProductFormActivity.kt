package com.example.belotrajeapp.view

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.belotrajeapp.R
import com.example.belotrajeapp.service.constants.ProductConstants
import com.example.belotrajeapp.viewModel.ProductFormViewModel
import kotlinx.android.synthetic.main.activity_product_form.*
import kotlinx.android.synthetic.main.row_home.*
import java.io.ByteArrayOutputStream

class ProductFormActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var radioGroup: RadioGroup
    private var mProductId: Int = 0
    private lateinit var selectedRadioButton: RadioButton
    private lateinit var mViewModel: ProductFormViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_form)
        if (supportActionBar != null) {
            supportActionBar!!.hide()
        }

        mViewModel = ViewModelProvider(this).get(ProductFormViewModel::class.java)

        setListeners()
        observe()
        loadData()
    }

    private fun loadData() {
        val bundle = intent.extras
        if (bundle != null){
            val id = bundle.getInt(ProductConstants.PRODUCTID)
            mProductId = bundle.getInt(ProductConstants.PRODUCTID)
            mViewModel.load(id)
        }
    }

    override fun onClick(v: View) {
        val id = v.id
        if (id == R.id.button_save){

            val name = edit_productName.text.toString()
            val description = edit_descriptionName.text.toString()
            val price = edit_price.text.toString()
            val size = checkSize()
            val category = checkCategory()
            val img = imageViewToByte(findViewById<ImageView>(R.id.image_view_gallery)as ImageView)


            mViewModel.save(
                mProductId,
                name,
                description,
                price,
                size,
                category,
                img
            )
        }
    }

    //Function to listeners click events
    private fun setListeners(){
        button_save.setOnClickListener(this)
        button_pick_img.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                    val permissions = arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                    requestPermissions(permissions, PERMISSION_CODE)
                } else {
                    pickImageFromGallery()
                }
            } else {
                pickImageFromGallery()
            }
        }
    }

    private fun observe(){
        mViewModel.saveProduct.observe(this, Observer {
            if(it){
                Toast.makeText(applicationContext, "Salvo", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(applicationContext, "Falha", Toast.LENGTH_SHORT).show()
            }
            finish()
        })

        mViewModel.product.observe(this, Observer {
            edit_productName.setText(it.name)
            edit_descriptionName.setText(it.description)
            edit_price.setText(it.price)
            image_view_gallery.setImageBitmap(BitmapFactory.decodeByteArray(it.img, 0, it.img.size))
        })
    }

    //Function get string from radio button category
    private fun checkCategory(): String {
        radioGroup = findViewById(R.id.radio_group_category)
        val selectedRadioButtonId: Int = radioGroup.checkedRadioButtonId
        if (selectedRadioButtonId != -1) {
          selectedRadioButton = findViewById(selectedRadioButtonId)
        }
        return selectedRadioButton.text.toString()
    }

    //Function get string from radio button size
    private fun checkSize(): String {
        radioGroup = findViewById(R.id.radio_group_size)
        val selectedRadioButtonId: Int = radioGroup.checkedRadioButtonId
        if (selectedRadioButtonId != -1) {
            selectedRadioButton = findViewById(selectedRadioButtonId)
        }
        return selectedRadioButton.text.toString()
    }

    //Methods to pick image from galery
    private fun imageViewToByte(image: ImageView): ByteArray {
        val bitmap = (image.drawable as BitmapDrawable).bitmap
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 90, stream)
        return stream.toByteArray()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            PERMISSION_CODE -> {
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    pickImageFromGallery()
                } else {
                    Toast.makeText(this, "Acesso negado", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            image_view_gallery.setImageURI(data?.data)
        }
    }

    private fun pickImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }

    companion object {
        private const val IMAGE_PICK_CODE = 1000
        private const val PERMISSION_CODE = 1001
    }

    //end of the method
}
