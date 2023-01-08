package com.avans.rentmycar.ui.home

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import com.avans.rentmycar.R
import com.avans.rentmycar.viewmodel.OfferViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.slider.Slider


class HomeBottomSheetDialogFragment : BottomSheetDialogFragment() {

    // TODO: Figure out why I can not get viewbinding to work in a bottomsheetdialogfragment

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.home_bottom_sheet, container, false)
    }

//    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModel = ViewModelProvider(requireActivity())[OfferViewModel::class.java]

        // TODO: Order this file so all the fields are prefilled from the model here at the top
        val iceCheckbox = view.findViewById<View>(R.id.checkbox_home_sheet_ice) as CheckBox
        val bevCheckbox = view.findViewById<View>(R.id.checkbox_home_sheet_bev) as CheckBox
        val fvecCheckbox = view.findViewById<View>(R.id.checkbox_home_sheet_fcev) as CheckBox

        val numberOfSeatsSlider = view.findViewById<View>(R.id.slider_home_sheet_seats) as Slider
        val maxdistanceSlider = view.findViewById<View>(R.id.slider_home_sheet_maxdistance) as Slider

        iceCheckbox.isChecked = viewModel.checkboxFuelTypeIceFilter.value ?: false
        bevCheckbox.isChecked = viewModel.checkboxFuelTypeBevFilter.value ?: false
        fvecCheckbox.isChecked = viewModel.checkboxFuelTypeFcevFilter.value ?: false

        numberOfSeatsSlider.value = viewModel.numberOfSeatsFilter.value?.toFloat() ?: 4f
        maxdistanceSlider.value = viewModel.maxDistanceInKmFilter.value?.toFloat() ?: 50f


        view.findViewById<View>(R.id.button_home_sheet_filter)?.setOnClickListener {

            dismiss() // This dismisses the bottom sheet modal

            // Update all data in the ViewModel
            viewModel.setCheckboxFuelTypeIceFilter(iceCheckbox.isChecked)
            viewModel.setCheckboxFuelTypeBevFilter(bevCheckbox.isChecked)
            viewModel.setCheckboxFuelTypeFcevFilter(fvecCheckbox.isChecked)

            viewModel.setNumberOfSeatsFilter(numberOfSeatsSlider.value.toInt())
            viewModel.setMaxDistanceInKmFilter(maxdistanceSlider.value)

        }




    }

}