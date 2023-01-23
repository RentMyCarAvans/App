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
import com.avans.rentmycar.model.response.BookingData
import com.avans.rentmycar.utils.DateTimeConverter
import com.avans.rentmycar.utils.ImageLoader
import java.text.SimpleDateFormat
import java.util.*

class BookingAdapter(
    private val imageLoader: ImageLoader,
    private val clickListener: (BookingData) -> Unit) :
    RecyclerView.Adapter<BookingAdapter.BookingViewHolder>() {

    lateinit var itemBookingBinding: BookingListItemBinding

    inner class BookingViewHolder(container: View) : RecyclerView.ViewHolder(container) {
        private val bookingCarImage = itemBookingBinding.imageviewItemBookingCarImage
        private val bookingCarName = itemBookingBinding.textviewItemBookingCarName
        private val bookingStartDate = itemBookingBinding.textviewItemBookingStartdate
        private val bookingEndDate = itemBookingBinding.textviewItemBookingEnddate
        private val bookingLocation = itemBookingBinding.textviewItemBookingLocation
        private val bookingStatusBanner = itemBookingBinding.textviewItemBookingStatusBanner

        @SuppressLint("SetTextI18n")
        @RequiresApi(Build.VERSION_CODES.O)
        fun bindData(bookingData: BookingData) {

            val startDate = DateTimeConverter.convertDatabaseDateTimeToReadableDateTime(bookingData.offer.startDateTime)
            val endDate = DateTimeConverter.convertDatabaseDateTimeToReadableDateTime(bookingData.offer.endDateTime)

            var carImage = ""
            // TODO: Find a royalty free image to use as a placeholder and add it to the drawable folder
            carImage = if(bookingData.offer.car.image == null || bookingData.offer.car.image == "") {
                "https://www.thecarwiz.com/images/listing_vehicle_placeholder.jpg"
            } else {
                bookingData.offer.car.image
            }

            imageLoader.loadImage(carImage, bookingCarImage)
            bookingCarName.text = bookingData.offer.car.model
            bookingStartDate.text = itemView.context.getString(R.string.offer_pickupAfter, startDate)
            bookingEndDate.text = itemView.context.getString(R.string.offer_returnBefore, endDate)
            bookingLocation.text = bookingData.offer.pickupLocation


            bookingStatusBanner.text = bookingData.status

            when (bookingData.status) {
                "PENDING" -> {
                    bookingStatusBanner.setBackgroundResource(R.color.colorPending)
                }
                "APPROVED" -> {
                    bookingStatusBanner.setBackgroundResource(R.color.colorApproved)

                }
            }


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