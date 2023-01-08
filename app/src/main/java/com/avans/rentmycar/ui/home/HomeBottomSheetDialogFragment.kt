package com.avans.rentmycar.ui.home

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import com.avans.rentmycar.R
import com.avans.rentmycar.viewmodel.HomeViewModel
import com.avans.rentmycar.viewmodel.OfferViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.slider.Slider


class HomeBottomSheetDialogFragment : BottomSheetDialogFragment() {

    // TODO: Figure out why I can not get viewbinding to work in a bottomsheetdialogfragment

    companion object {
        const val TAG = "[HBSDF]"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.home_bottom_sheet, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // TODO: Rename this
        val model = ViewModelProvider(requireActivity()).get(HomeViewModel::class.java)

        // TODO: Order this file so all the fields are prefilled from the model here at the top
        val mCheckBox = view.findViewById<View>(R.id.checkbox_home_sheet_ice) as CheckBox
        val numberOfSeatsSlider = view.findViewById<View>(R.id.slider_home_sheet_seats) as Slider

        mCheckBox.isChecked = model.checkboxBlue.value ?: false
        numberOfSeatsSlider.value = model.numberOfSeatsFilter.value?.toFloat() ?: 4f



        view.findViewById<View>(R.id.button_home_sheet_filter)?.setOnClickListener {

            dismiss()


        // TODO: Send all form values to the model


            if(mCheckBox.isChecked) {
                // TODO: Should this model activate the filter? Of should the filter always check values of the ViewModel?
                Log.d("[Sheet] filter click", "activate vw?")
                val offerVW = OfferViewModel()
                offerVW.getOffersByColor("Purple")
            }

            Log.d("[Sheet] checkChecked", "checkboxIsChecked: ${mCheckBox.isChecked}")
            Log.d("[Sheet] numberOfSeats", "numberOfSeatsFilter: ${numberOfSeatsSlider.getValue()}")

            model.setCheckboxBlue(mCheckBox.isChecked)
            model.setNumberOfSeatsFilter(numberOfSeatsSlider.getValue().toInt())

        }




    }

}