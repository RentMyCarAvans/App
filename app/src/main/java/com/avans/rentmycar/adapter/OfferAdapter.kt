package com.avans.rentmycar.adapter

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.avans.rentmycar.R
import com.avans.rentmycar.databinding.ItemOfferBinding
import com.avans.rentmycar.model.response.OfferData
import com.avans.rentmycar.utils.ImageLoader
import com.avans.rentmycar.utils.SessionManager
import java.text.SimpleDateFormat
import java.util.*

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

        @SuppressLint("SetTextI18n")
        @RequiresApi(Build.VERSION_CODES.O)
        fun bindData(offerData: OfferData) {

            // Convert the datetime strings to a more readable format
            val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
            val formatter = SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault())

            val startDate: String? = parser.parse(offerData.startDateTime)
                ?.let { formatter.format(it) }
            val endDate: String? = parser.parse(offerData.endDateTime)?.let { formatter.format(it) }

            // TODO: Replace cute placeholder images with dumb pictures of cars
            val random = (1..10).random() * 100
            var carImage = ""
            carImage = if(offerData.car.image == null) {
                "http://placekitten.com/$random/$random"
            } else {
                offerData.car.image
            }

            imageLoader.loadImage(carImage, offerCarImage)
            offerCarName.text = offerData.car.model
            offerStartDate.text = itemView.context.getString(R.string.offer_pickupAfter, startDate)
            offerEndDate.text = itemView.context.getString(R.string.offer_returnBefore, endDate)
            offerLocation.text = offerData.pickupLocation

            Log.d("[OAdapt] distance", "distance: " + offerData.distance)

            if (offerData.distance < 1000) { // If the distance < 1000, show the distance in meters
                offerDistance.text = offerData.distance.toInt().toString() + "m"
            } else if (offerData.distance > 1000) { // If the distance > 1000, show the distance in kilometers
                val distanceInKm = offerData.distance / 1000
                // set distanceInKm to 1 decimal
                offerDistance.text = String.format("%.1f", distanceInKm) + "km"
            } else {
                offerDistance.text = "..."
            }

            Log.d("[OfferAdapter locPerm]", "Location permission: ${SessionManager.getLocationPermissionGranted()}")

            if(!SessionManager.getLocationPermissionGranted()){
                itemOfferBinding.textviewItemOfferDistance.visibility = View.GONE
            }

        }

    }


    private val offerData = mutableListOf<OfferData>()

    fun setData(offerData: Collection<OfferData>) {
        this.offerData.clear()
        this.offerData.addAll(offerData)
        notifyDataSetChanged() // TODO: Improve this if we have time left
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
            Log.d("[OfferAdapter]", "onBindViewHolder() => Clicked on offer with id: " + offerData[position].id)
            Log.d("[OfferAdapter]", "Clicked on offer with id: " + offerData[position].id)
        }
        holder.bindData(offerData[position])
    }

    override fun getItemCount(): Int = offerData.size

}