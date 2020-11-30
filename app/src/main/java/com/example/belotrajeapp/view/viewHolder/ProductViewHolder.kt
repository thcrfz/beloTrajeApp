package com.example.belotrajeapp.view.viewHolder

import android.app.AlertDialog
import android.graphics.BitmapFactory
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.belotrajeapp.R
import com.example.belotrajeapp.service.model.ProductModel
import com.example.belotrajeapp.view.listener.ProductListener
import kotlinx.android.synthetic.main.row_home.view.*

class ProductViewHolder(item: View, private val listener: ProductListener) : RecyclerView.ViewHolder(item) {
    fun bind(product: ProductModel){
        val linearLayout = itemView.findViewById<LinearLayout>(R.id.linear_layout_clickable)
        val img = itemView.findViewById<ImageView>(R.id.row_image_photo)
        val textName = itemView.findViewById<TextView>(R.id.row_text_name)
        val textDescription = itemView.findViewById<TextView>(R.id.row_text_description)
        val textSize = itemView.findViewById<TextView>(R.id.row_text_size)
        val textPrice = itemView.findViewById<TextView>(R.id.row_text_price)

        img.setImageBitmap(BitmapFactory.decodeByteArray(product.img, 0, product.img.size))
        textName.text = product.name
        textDescription.text = product.description
        textSize.text = product.size
        textPrice.text = product.price

        linearLayout.setOnClickListener {
            listener.onClick(product.id)
        }
        linearLayout.setOnLongClickListener{

            AlertDialog.Builder(itemView.context)
                .setTitle("Remover produto")
                .setMessage("Deseja remover produto?")
                .setPositiveButton("Sim"){dialog, wich ->
                    listener.onDelete(product.id)
                }
                .setNeutralButton("Cancelar", null)
                .show()
            true
        }

    }
}