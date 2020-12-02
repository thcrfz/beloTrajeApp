package com.example.belotrajeapp.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isEmpty
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.belotrajeapp.R
import com.example.belotrajeapp.service.constants.DatabaseConstants
import com.example.belotrajeapp.service.constants.ProductConstants
import com.example.belotrajeapp.view.adapter.ProductAdapter
import com.example.belotrajeapp.view.listener.ProductListener
import com.example.belotrajeapp.viewModel.HomeViewModel
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    private lateinit var mListener: ProductListener
    private lateinit var homeViewModel: HomeViewModel
    private val mAdapter: ProductAdapter = ProductAdapter()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        val root = inflater.inflate(R.layout.fragment_home, container, false)

        val recycler = root.findViewById<RecyclerView>(R.id.recycler_home)


        recycler.layoutManager = LinearLayoutManager(context)

        recycler.adapter = mAdapter

        mListener = object : ProductListener{
            override fun onClick(id: Int) {

                val intent = Intent(context, ProductFormActivity::class.java)

                val bundle = Bundle()

                bundle.putInt(ProductConstants.PRODUCTID, id)

                intent.putExtras(bundle)

                startActivity(intent)
            }

            override fun onDelete(id: Int) {
                homeViewModel.delete(id)
                homeViewModel.load()
            }

        }


        mAdapter.attachListener(mListener)

        observer()


        return root
    }

    override fun onResume() {
        super.onResume()
        homeViewModel.load()
    }

    private fun observer() {
        homeViewModel.productList.observe(viewLifecycleOwner, Observer {
            mAdapter.updateProducts(it)

        })
    }
}
