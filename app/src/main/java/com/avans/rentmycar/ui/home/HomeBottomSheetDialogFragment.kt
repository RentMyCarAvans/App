package com.avans.rentmycar.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.lifecycle.ViewModelProvider
import com.avans.rentmycar.R
import com.avans.rentmycar.utils.SessionManager
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

        val iceCheckbox = view.findViewById<View>(R.id.checkbox_home_sheet_ice) as CheckBox
        val bevCheckbox = view.findViewById<View>(R.id.checkbox_home_sheet_bev) as CheckBox
        val fvecCheckbox = view.findViewById<View>(R.id.checkbox_home_sheet_fcev) as CheckBox

        val showOwnCarsCheckbox = view.findViewById<View>(R.id.checkbox_home_sheet_owncar) as CheckBox

        val numberOfSeatsSlider = view.findViewById<View>(R.id.slider_home_sheet_seats) as Slider
        val maxdistanceSlider = view.findViewById<View>(R.id.slider_home_sheet_maxdistance) as Slider

        if(!SessionManager.getLocationPermissionGranted()){
            maxdistanceSlider.isEnabled = false
            maxdistanceSlider.visibility = View.GONE
            view.findViewById<View>(R.id.divider_home_sheet_distance).visibility = View.GONE
            view.findViewById<View>(R.id.textView_home_sheet_maxdistance).visibility = View.GONE
        }

        iceCheckbox.isChecked = (SessionManager.getBoolean(context = requireContext(), "fuelTypeIceFilter") ?: true)
        bevCheckbox.isChecked = (SessionManager.getBoolean(context = requireContext(), "fuelTypeBevFilter") ?: true)
        fvecCheckbox.isChecked =  (SessionManager.getBoolean(context = requireContext(), "fuelTypeFcevFilter") ?: true)
        showOwnCarsCheckbox.isChecked =  (SessionManager.getBoolean(context = requireContext(), "showOwnCarsFilter") ?: true)

        numberOfSeatsSlider.value =  (SessionManager.getFloat(context = requireContext(), "numberOfSeatsFilter") ?: 4f)
        maxdistanceSlider.value =  (SessionManager.getFloat(context = requireContext(), "maxDistanceInKmFilter") ?: 75f)


    maxdistanceSlider.addOnChangeListener { slider, value, fromUser ->

        // If the value of maxdistanceSLider == 100, change the label to 100+
        if (maxdistanceSlider.value == 100f) {
            maxdistanceSlider.setLabelFormatter { value ->
                if (value == 100f) {
                    "100+"
                } else {
                    value.toInt().toString()
                }
            }
        }
    }

        view.findViewById<View>(R.id.button_home_sheet_filter)?.setOnClickListener {

            dismiss() // This dismisses the bottom sheet modal

            // Save the values of the checkboxes and sliders to the shared preferences
            SessionManager.saveBoolean(context = requireContext(), "fuelTypeIceFilter", iceCheckbox.isChecked)
            SessionManager.saveBoolean(context = requireContext(), "fuelTypeBevFilter", bevCheckbox.isChecked)
            SessionManager.saveBoolean(context = requireContext(), "fuelTypeFcevFilter", fvecCheckbox.isChecked)
            SessionManager.saveBoolean(context = requireContext(), "showOwnCarsFilter", showOwnCarsCheckbox.isChecked)

            SessionManager.saveFloat(context = requireContext(), "numberOfSeatsFilter", numberOfSeatsSlider.value)
            SessionManager.saveFloat(context = requireContext(), "maxDistanceInKmFilter", maxdistanceSlider.value)


            // Update all data in the ViewModel
            viewModel.setCheckboxFuelTypeIceFilter(iceCheckbox.isChecked)
            viewModel.setCheckboxFuelTypeBevFilter(bevCheckbox.isChecked)
            viewModel.setCheckboxFuelTypeFcevFilter(fvecCheckbox.isChecked)
            viewModel.setCheckboxShowOwnCarsFilter(showOwnCarsCheckbox.isChecked)

            viewModel.setMaxDistanceInKmFilter(maxdistanceSlider.value)

            viewModel.setNumberOfSeatsFilter(numberOfSeatsSlider.value.toInt())


            viewModel.getOffers()

        }

    // TODO: Create a button to clear the filters

    }

}