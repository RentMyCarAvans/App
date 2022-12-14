package com.avans.rentmycar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.avans.rentmycar.adapter.OfferAdapter
import com.avans.rentmycar.data.TestDatasources
import com.avans.rentmycar.databinding.ActivityMainBinding
import com.avans.rentmycar.ui.GlideImageLoader

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val offerAdapter by lazy {
        OfferAdapter(GlideImageLoader(this))
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val recyclerView = binding.mainRecyclerView
        recyclerView.adapter = offerAdapter
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        offerAdapter.setData(
            TestDatasources().loadOffers()
        )

    }
}