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
import com.avans.rentmycar.utils.DateTimeConverter
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

        private val offerOwnerBanner = itemOfferBinding.textviewItemOfferOwnerBanner

        @SuppressLint("SetTextI18n")
        @RequiresApi(Build.VERSION_CODES.O)
        fun bindData(offerData: OfferData) {

            // TODO: Disable the distance textview if called from the MyOffersFragment
            // For now, hide the distance textview if distance is null or 0
            if (offerData.distance == 0f) {
                offerDistance.visibility = View.GONE
            }

            val startDate = DateTimeConverter.convertDatabaseDateTimeToReadableDateTime(offerData.startDateTime)
            val endDate = DateTimeConverter.convertDatabaseDateTimeToReadableDateTime(offerData.endDateTime)

            var carImage = ""
                // TODO: Find a royalty free image to use as a placeholder and add it to the drawable folder
            carImage = if(offerData.car.image == null || offerData.car.image == "") {
                "https://www.thecarwiz.com/images/listing_vehicle_placeholder.jpg"
            } else {
                offerData.car.image
            }

            imageLoader.loadImage(carImage, offerCarImage)
            offerCarName.text = offerData.car.model
            offerStartDate.text = itemView.context.getString(R.string.offer_pickupAfter, startDate)
            offerEndDate.text = itemView.context.getString(R.string.offer_returnBefore, endDate)
            offerLocation.text = offerData.pickupLocation

            val currentUserId = SessionManager.getUserId(context = itemOfferBinding.root.context)

            if(offerData.car.user.id == currentUserId) {
                offerOwnerBanner.visibility = View.VISIBLE
            } else {
                offerOwnerBanner.visibility = View.GONE
            }


            if (offerData.distance < 1000) { // If the distance < 1000, show the distance in meters
                offerDistance.text = offerData.distance.toInt().toString() + "m"
            } else if (offerData.distance > 1000) { // If the distance > 1000, show the distance in kilometers
                val distanceInKm = offerData.distance / 1000
                // set distanceInKm to 1 decimal
                offerDistance.text = String.format("%.1f", distanceInKm) + "km"
            } else {
                offerDistance.text = "..."
            }


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