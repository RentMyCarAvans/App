package com.avans.rentmycar.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.avans.rentmycar.databinding.ItemOfferBinding
import com.avans.rentmycar.model.OfferData
import com.avans.rentmycar.model.OfferUiModel
import com.avans.rentmycar.utils.ImageLoader

class OfferAdapter(private val imageLoader: ImageLoader) :
    RecyclerView.Adapter<OfferAdapter.OfferViewHolder>() {

    inner class OfferViewHolder(container: View) : RecyclerView.ViewHolder(container) {
        private val offerCarImage = itemOfferBinding.imageviewItemOfferCarImage
        private val offerCarName = itemOfferBinding.textviewItemOfferCarName
        private val offerDates = itemOfferBinding.textviewItemOfferDates
        private val offerLocation = itemOfferBinding.textviewItemOfferLocation

        fun bindData(offerData: OfferData) {
            imageLoader.loadImage("http://placekitten.com/300/300", offerCarImage)
            offerCarName.text = offerData.car.model
            offerDates.text = offerData.startDateTime + " - " + offerData.endDateTime
            offerLocation.text = offerData.pickupLocation
        }
    }

    lateinit var itemOfferBinding: ItemOfferBinding

    private val offerData = mutableListOf<OfferData>()

    fun setData(offerData: Collection<OfferData>) {
        Log.d("[Home] OfferAdapter", "setData: $offerData")
        this.offerData.clear()
        this.offerData.addAll(offerData)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OfferViewHolder {
        itemOfferBinding =
            ItemOfferBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OfferViewHolder(itemOfferBinding.root)
    }

    override fun onBindViewHolder(holder: OfferViewHolder, position: Int) {
        holder.bindData(offerData[position])
    }

    override fun getItemCount(): Int = offerData.size

}