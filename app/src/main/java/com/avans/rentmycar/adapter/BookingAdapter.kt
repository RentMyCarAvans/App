package com.avans.rentmycar.adapter

import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.avans.rentmycar.databinding.ItemBookingBinding
import com.avans.rentmycar.databinding.ItemOfferBinding
import com.avans.rentmycar.model.BookingData
import com.avans.rentmycar.utils.ImageLoader
import java.text.SimpleDateFormat

class BookingAdapter(
    private val imageLoader: ImageLoader,
    private val clickListener: (BookingData) -> Unit) :
    RecyclerView.Adapter<BookingAdapter.BookingViewHolder>() {

    inner class BookingViewHolder(container: View) : RecyclerView.ViewHolder(container) {
        private val bookingCarImage = itemBookingBinding.imageviewItemBookingCarImage
        private val bookingCarName = itemBookingBinding.textviewItemBookingCarName
        private val bookingDates = itemBookingBinding.textviewItemBookingDates
        private val bookingLocation = itemBookingBinding.textviewItemBookingLocation

        @RequiresApi(Build.VERSION_CODES.O)
        fun bindData(bookingData: BookingData) {

            // Convert the datetime strings to a more readable format
            val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
            val formatter = SimpleDateFormat("dd MM yyyy HH:mm")

            val startDate: String = formatter.format(parser.parse(bookingData.offer.startDateTime))
            val endDate: String = formatter.format(parser.parse(bookingData.offer.endDateTime))

            // TODO: Replace cute placeholder images with dumb pictures of cars
            val random = (1..10).random() * 100

            imageLoader.loadImage(
                "http://placekitten.com/" + random + "/" + random,
                bookingCarImage
            )
            bookingCarName.text = bookingData.offer.car.model
            bookingDates.text = "$startDate - $endDate"
            bookingLocation.text = bookingData.offer.pickupLocation
        }
    }

    lateinit var itemBookingBinding: ItemBookingBinding

    private val bookingData = mutableListOf<BookingData>()

    fun setData(bookingData: Collection<BookingData>) {
        this.bookingData.clear()
        this.bookingData.addAll(bookingData)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BookingAdapter.BookingViewHolder {
        itemBookingBinding =
            ItemBookingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BookingViewHolder(itemBookingBinding.root)
    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: BookingAdapter.BookingViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            clickListener(bookingData[position])
            Log.d("BookingAdapter", "Clicked on Booking with id: " + bookingData[position].id)
        }
        holder.bindData(bookingData[position])
    }

    override fun getItemCount(): Int = bookingData.size
}