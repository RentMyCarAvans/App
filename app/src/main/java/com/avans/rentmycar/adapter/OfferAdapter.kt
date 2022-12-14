package com.avans.rentmycar.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.avans.rentmycar.databinding.ItemOfferBinding
import com.avans.rentmycar.model.OfferUiModel
import com.avans.rentmycar.ui.ImageLoader

class OfferAdapter(private val imageLoader: ImageLoader) :
    RecyclerView.Adapter<OfferAdapter.OfferViewHolder>() {

    inner class OfferViewHolder(container: View) : RecyclerView.ViewHolder(container) {
        private val offerCarImage = itemOfferBinding.itemOfferCarImage
        private val offerCarName = itemOfferBinding.itemOfferCarName
        private val offerDates = itemOfferBinding.itemOfferDates
        private val offerLocation = itemOfferBinding.itemOfferLocation

        fun bindData(offerData: OfferUiModel) {
            imageLoader.loadImage(offerData.image, offerCarImage)
            offerCarName.text = offerData.car
            offerDates.text = offerData.dates
            offerLocation.text = offerData.location
        }
    }

    lateinit var itemOfferBinding: ItemOfferBinding

    // For logging
    companion object {
        private val TAG = OfferAdapter::class.qualifiedName
    }

    private val offerData = mutableListOf<OfferUiModel>()
    fun setData(offerData: List<OfferUiModel>) {
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