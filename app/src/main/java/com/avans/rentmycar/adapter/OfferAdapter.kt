package com.avans.rentmycar.adapter

import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.recyclerview.widget.RecyclerView
import com.avans.rentmycar.R
import com.avans.rentmycar.databinding.ItemOfferBinding
import com.avans.rentmycar.model.OfferData
import com.avans.rentmycar.utils.ImageLoader
import com.google.android.gms.location.LocationServices
import java.text.SimpleDateFormat

// TODO: Move these constructor arguments to fields on the class?
class OfferAdapter(
    private val imageLoader: ImageLoader,
    private val clickListener: (OfferData) -> Unit) :
    RecyclerView.Adapter<OfferAdapter.OfferViewHolder>() {

    lateinit var itemOfferBinding: ItemOfferBinding


    inner class OfferViewHolder(container: View) : RecyclerView.ViewHolder(container) {
        private val offerCarImage = itemOfferBinding.imageviewItemOfferCarImage
        private val offerCarName = itemOfferBinding.textviewItemOfferCarName
        private val offerStartDate = itemOfferBinding.textviewItemOfferStartdate
        private val offerEndDate = itemOfferBinding.textviewItemOfferEnddate
        private val offerLocation = itemOfferBinding.textviewItemOfferLocation
        private val offerDistance = itemOfferBinding.textviewItemOfferDistance

        @RequiresApi(Build.VERSION_CODES.O)
        fun bindData(offerData: OfferData) {

            // Convert the datetime strings to a more readable format
            val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
            val formatter = SimpleDateFormat("dd-MM-yyyy HH:mm")

            val startDate: String = formatter.format(parser.parse(offerData.startDateTime))
            val endDate: String = formatter.format(parser.parse(offerData.endDateTime))

            // TODO: Replace cute placeholder images with dumb pictures of cars
            val random = (1..10).random() * 100
            var carImage = ""
            carImage = if(offerData.car.image == null) {
                "http://placekitten.com/$random/$random"
            } else {
                offerData.car.image
            }

//            var distance = 0.0

            imageLoader.loadImage(carImage, offerCarImage)
            offerCarName.text = offerData.car.model
            offerStartDate.text = itemView.context.getString(R.string.offer_pickupAfter, startDate)
            offerEndDate.text = itemView.context.getString(R.string.offer_returnBefore, endDate)
            offerLocation.text = offerData.pickupLocation

            // If te distance < 1000, show the distance in meters
            // If the distance > 1000, show the distance in kilometers
            // If the distance is null, show "Calculating distance..."
            if (offerData.distance < 1000) {
                offerDistance.text = offerData.distance.toInt().toString() + "m"
            } else if (offerData.distance > 1000) {
                val distanceInKm = offerData.distance / 1000
                // set distanceInKm to 1 decimal
                offerDistance.text = String.format("%.1f", distanceInKm) + "km"
            } else {
                offerDistance.text = "..."
            }

//            offerDistance.text = offerData.distance.toString() + " m"

//            Log.d("[Home][Adapter]", "bindData() => offer with carname " + offerCarName.text)
        }


    }

//    lateinit var itemOfferBinding: ItemOfferBinding

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
            Log.d("[Home][Adapter]", "onBindViewHolder() => Clicked on offer with id: " + offerData[position].id)
            Log.d("OfferAdapter", "Clicked on offer with id: " + offerData[position].id)
        }
        holder.bindData(offerData[position])
    }

    override fun getItemCount(): Int = offerData.size

//    fun setDistance(value: Map<Long, Float>?) {
//        if (value != null) {
//            for (offer in offerData) {
//                offer.distance = value[offer.id]!!
//            }
//        }
//        notifyDataSetChanged()
//
//    }


}