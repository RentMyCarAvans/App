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
import com.avans.rentmycar.databinding.BookingListItemBinding
import com.avans.rentmycar.databinding.ItemBookingBinding
import com.avans.rentmycar.databinding.ItemOfferBinding
import com.avans.rentmycar.model.response.BookingData
import com.avans.rentmycar.model.response.OfferData
import com.avans.rentmycar.utils.ImageLoader
import com.avans.rentmycar.utils.SessionManager
import java.text.SimpleDateFormat
import java.util.*

class BookingAdapter(
    private val imageLoader: ImageLoader,
    private val clickListener: (BookingData) -> Unit) :
    RecyclerView.Adapter<BookingAdapter.BookingViewHolder>() {

    lateinit var itemBookingBinding: BookingListItemBinding

    inner class BookingViewHolder(container: View) : RecyclerView.ViewHolder(container) {
//        private val offerCarImage = itemBookingBinding.imageviewItemOfferCarImage
        private val bookingCarName = itemBookingBinding.textviewItemBookingCarName
//        private val offerStartDate = itemBookingBinding.textviewItemOfferStartdate
//        private val offerEndDate = itemBookingBinding.textviewItemOfferEnddate
//        private val offerLocation = itemBookingBinding.textviewItemOfferLocation
//        private val offerDistance = itemBookingBinding.textviewItemOfferDistance

        @SuppressLint("SetTextI18n")
        @RequiresApi(Build.VERSION_CODES.O)
        fun bindData(bookingData: BookingData) {

            // Convert the datetime strings to a more readable format
            val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
            val formatter = SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault())

            val startDate: String? = parser.parse(bookingData.offer.startDateTime)
                ?.let { formatter.format(it) }
            val endDate: String? = parser.parse(bookingData.offer.endDateTime)?.let { formatter.format(it) }

            // TODO: Replace cute placeholder images with dumb pictures of cars
            val random = (1..10).random() * 100
            var carImage = ""
            // TODO: Find a royalty free image to use as a placeholder and add it to the drawable folder
            carImage = if(bookingData.offer.car.image == null || bookingData.offer.car.image == "") {
                "https://www.thecarwiz.com/images/listing_vehicle_placeholder.jpg"
            } else {
                bookingData.offer.car.image
            }

//            imageLoader.loadImage(carImage, bookingCarImage)
            bookingCarName.text = bookingData.offer.car.model
//            bookingStartDate.text = itemView.context.getString(R.string.offer_pickupAfter, startDate)
//            bookingEndDate.text = itemView.context.getString(R.string.offer_returnBefore, endDate)
//            bookingLocation.text = bookingData.offer.pickupLocation
//
//            Log.d("[OAdapt] distance", "distance: " + offerData.distance)
//
//            if (bookingData.offer.distance < 1000) { // If the distance < 1000, show the distance in meters
//                offerDistance.text = bookingData.offer.distance.toInt().toString() + "m"
//            } else if (bookingData.offer.distance > 1000) { // If the distance > 1000, show the distance in kilometers
//                val distanceInKm = bookingData.offer.distance / 1000
//                // set distanceInKm to 1 decimal
//                offerDistance.text = String.format("%.1f", distanceInKm) + "km"
//            } else {
//                offerDistance.text = "..."
//            }
//
//            Log.d("[OfferAdapter locPerm]", "Location permission: ${SessionManager.getLocationPermissionGranted()}")
//
//            if(!SessionManager.getLocationPermissionGranted()){
//                itemBookingBinding.textviewItemOfferDistance.visibility = View.GONE
//            }

        }

    }


    private val bookingData = mutableListOf<BookingData>()

    fun setData(bookingData: Collection<BookingData>) {
        this.bookingData.clear()
        this.bookingData.addAll(bookingData)
        notifyDataSetChanged() // TODO: Improve this if we have time left
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookingAdapter.BookingViewHolder {
        itemBookingBinding =
            BookingListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BookingViewHolder(itemBookingBinding.root)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: BookingAdapter.BookingViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            clickListener(bookingData[position])
            Log.d("[BookingAdapter]", "onBindViewHolder() => Clicked on offer with id: " + bookingData[position].id)
            Log.d("[BookingAdapter]", "Clicked on offer with id: " + bookingData[position].id)
        }
        holder.bindData(bookingData[position])
    }

    override fun getItemCount(): Int = bookingData.size

}