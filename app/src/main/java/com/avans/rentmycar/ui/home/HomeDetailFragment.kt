package com.avans.rentmycar.ui.home

import android.media.Image
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.avans.rentmycar.R
import com.avans.rentmycar.databinding.FragmentHomeBinding
import com.avans.rentmycar.utils.Constant
import com.avans.rentmycar.utils.GlideImageLoader
import com.avans.rentmycar.utils.SessionManager
import com.bumptech.glide.Glide

class HomeDetailFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_home_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args: HomeDetailFragmentArgs by navArgs()
        val offerId = args.id
        val offerCarModel = args.carmodel
        val offerPickupLocation = args.pickuplocation
        val offerStartDateTime = args.startDateTime
        val offerEndDateTime = args.endDateTime
        val carImageUrl = args.carImageUrl

        Log.d("[HDF]", "OfferId: " + offerId.toString())

        // TODO: Replace with binding
        val imageViewCar = view.findViewById<ImageView>(R.id.imageview_home_detail_car_image)
        Glide.with(this).load(carImageUrl).centerCrop().placeholder(R.drawable.audi).into(imageViewCar);
        view.findViewById<TextView>(R.id.textview_home_detail_offerid).text = "OfferId: " + offerId.toString()
        view.findViewById<TextView>(R.id.textview_home_detail_car_name).text = offerCarModel
        view.findViewById<TextView>(R.id.textview_home_detail_offer_pickuplocation).text = offerPickupLocation
        view.findViewById<TextView>(R.id.textview_home_detail_offer_dates).text = offerStartDateTime + " - " + offerEndDateTime


        val userId = SessionManager.getUserId(requireContext())

        // Setup Book Button
        view.findViewById<TextView>(R.id.button_home_detail_book).setOnClickListener {

            Log.d("[HDF]", "Book button clicked")
            Log.d("[HDF] User", "userId: " + userId.toString())


            // TODO: Remove this when user is logged in
            Log.w("[HDF]", "LET OP! USING MOCK USER ID!")
            val userId = 1
            Log.d("[HDF]", "Book Offer " + offerId + " for user with id " + userId.toString())

            // TODO: Make a POST request to the API to book the offer

        }



        // Set the title of the actionbar
        val bar = (activity as AppCompatActivity).supportActionBar
        bar?.title = offerCarModel

    }





}