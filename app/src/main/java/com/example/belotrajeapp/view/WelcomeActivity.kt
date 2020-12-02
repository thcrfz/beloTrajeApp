package com.example.belotrajeapp.view

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.viewpager.widget.ViewPager
import com.example.belotrajeapp.R
import com.example.belotrajeapp.service.model.OnBoardingData
import com.example.belotrajeapp.view.adapter.OnBoardingViewPagerAdapter
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_welcome.*
import java.nio.file.Files.size

class WelcomeActivity : AppCompatActivity() {

    private var onBoardingViewPagerAdapter: OnBoardingViewPagerAdapter? = null
    private var tabLayout: TabLayout? = null
    private var onBoardingViewPager : ViewPager? = null
    var next: TextView? = null
    var position = 0
    var sharedPreferences: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        if (supportActionBar != null) {
            supportActionBar!!.hide()
        }

        tabLayout = findViewById(R.id.tab_indicator)
        next = findViewById(R.id.text_next)

        val onBoardingData: MutableList<OnBoardingData> = ArrayList()
        onBoardingData.add(OnBoardingData("Produtos","Cadastre e edite seus produtos", R.drawable.ic_moda_masculina_by_vexels ))
        onBoardingData.add(OnBoardingData("Catálogo","Veja seu catálogo de produtos", R.drawable.ic_catalogosvg))
        onBoardingData.add(OnBoardingData("Gerenciar","Gerencie sua loja", R.drawable.ic_loja_virtual))

        setOnBoardingViewAdapter(onBoardingData)

        position = onBoardingViewPager!!.currentItem

        next?.setOnClickListener {
            if(position < onBoardingData.size){
                position++
                onBoardingViewPager!!.currentItem = position
            }
            if(position == onBoardingData.size){
                savePrefData()
                val i = Intent(applicationContext, MainActivity::class.java)
                startActivity(i)
            }
        }
        tabLayout!!.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
               position = tab!!.position
                if (tab.position == onBoardingData.size - 1){
                    next!!.text = "Vamos começar"
                }else{
                    next!!.text = "Proximo"
                }
            }
        })
    }

    private fun setOnBoardingViewAdapter(onBoardingData: List<OnBoardingData>){
        onBoardingViewPager = findViewById(R.id.screenPager)
        onBoardingViewPagerAdapter = OnBoardingViewPagerAdapter(this, onBoardingData)
        onBoardingViewPager!!.adapter = onBoardingViewPagerAdapter
        tabLayout?.setupWithViewPager(onBoardingViewPager)
    }

    private fun savePrefData(){
        sharedPreferences = applicationContext.getSharedPreferences("pref", Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor? = sharedPreferences!!.edit()
        editor?.putBoolean("isFirstTimeRun", true)
        editor?.apply()
    }

    private fun restorePrefData(): Boolean{
        sharedPreferences = applicationContext.getSharedPreferences("pref", Context.MODE_PRIVATE)
        return sharedPreferences!!.getBoolean("isFirstTimeRun", false)
    }
}
