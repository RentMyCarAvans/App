package com.avans.rentmycar.adapter

import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.avans.rentmycar.databinding.ItemOfferBinding
import com.avans.rentmycar.model.OfferData
import com.avans.rentmycar.utils.ImageLoader
import java.text.SimpleDateFormat

class OfferAdapter(
    private val imageLoader: ImageLoader,
    private val clickListener: (OfferData) -> Unit) :
    RecyclerView.Adapter<OfferAdapter.OfferViewHolder>() {

    inner class OfferViewHolder(container: View) : RecyclerView.ViewHolder(container) {
        private val offerCarImage = itemOfferBinding.imageviewItemOfferCarImage
        private val offerCarName = itemOfferBinding.textviewItemOfferCarName
        private val offerDates = itemOfferBinding.textviewItemOfferDates
        private val offerLocation = itemOfferBinding.textviewItemOfferLocation

        @RequiresApi(Build.VERSION_CODES.O)
        fun bindData(offerData: OfferData) {

            // Convert the datetime strings to a more readable format
            val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
            val formatter = SimpleDateFormat("dd MM yyyy HH:mm")

            val startDate: String = formatter.format(parser.parse(offerData.startDateTime))
            val endDate: String = formatter.format(parser.parse(offerData.endDateTime))

            // TODO: Replace cute placeholder images with dumb pictures of cars
            val random = (1..10).random() * 100

            imageLoader.loadImage("http://placekitten.com/" + random + "/" + random, offerCarImage)
            offerCarName.text = offerData.car.model
            offerDates.text = "$startDate - $endDate"
            offerLocation.text = offerData.pickupLocation
        }
    }

    lateinit var itemOfferBinding: ItemOfferBinding

    private val offerData = mutableListOf<OfferData>()

    fun setData(offerData: Collection<OfferData>) {
        this.offerData.clear()
        this.offerData.addAll(offerData)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OfferViewHolder {
        itemOfferBinding =
            ItemOfferBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OfferViewHolder(itemOfferBinding.root)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: OfferViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            clickListener(offerData[position])
            Log.d("OfferAdapter", "Clicked on offer with id: " + offerData[position].id)
        }
        holder.bindData(offerData[position])
    }

    override fun getItemCount(): Int = offerData.size
}