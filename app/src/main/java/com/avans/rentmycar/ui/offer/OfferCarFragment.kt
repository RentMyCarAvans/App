package com.avans.rentmycar.ui.offer

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.DatePicker
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.avans.rentmycar.R
import com.avans.rentmycar.databinding.FragmentOfferBinding
import com.avans.rentmycar.utils.SessionManager
import com.avans.rentmycar.viewmodel.CarViewModel
import com.avans.rentmycar.viewmodel.OfferViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.timepicker.MaterialTimePicker
import java.text.SimpleDateFormat
import java.util.*

/**
 * A simple [Fragment] subclass.
 * Use the [OfferCarFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class OfferCarFragment : Fragment() {
    private val TAG = "[RMC][OfferCarFrg]"

    // var for binding the fragment_edit_car layout
    private var _binding: FragmentOfferBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG,"onCreate()")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOfferBinding.inflate(inflater, container, false)
        Log.d(TAG, "onCreateView()")
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args: OfferCarFragmentArgs by navArgs()
        Log.d("[RMC]", "onViewCreated() => Checking navArgs Car offer")
        Log.d("[RMC]", "onViewCreated() => navArgs: " + args.toString())

        bindUI(args)

        val mPickTimeBtn = view.findViewById<Button>(R.id.pickTimeBtn)
        val mDateTimeBtn = view.findViewById<Button>(R.id.pickDateBtn)

        // nVar to hold the startdatetime, which is concatenation of textview_date and textview_time
        val textView     = view.findViewById<TextView>(R.id.textview_offer_car_startdate)

        var textview_date: TextView? = null
        var textview_time: TextView? = null
        var cal = Calendar.getInstance()

        mPickTimeBtn.setOnClickListener {
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)
                textview_time?.text = SimpleDateFormat("HH:mm").format(cal.time)
                textview_date?.text = textview_date.toString() + "-" + textview_time.toString()
            }
            TimePickerDialog(this.context, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()
        }

        val mPickDateTimeBtn = view.findViewById<Button>(R.id.pickDateBtn)
        textview_date     = view.findViewById<TextView>(R.id.textview_offer_car_startdate)

        // create an OnDateSetListener
        val dateSetListener = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            cal.set(Calendar.YEAR, year)
            cal.set(Calendar.MONTH, monthOfYear)
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            val myFormat = "dd.MM.yyyy" // mention the format you need
            val sdf = SimpleDateFormat(myFormat, Locale.US)
            textview_date.text = sdf.format(cal.time)
          //  textview_date.text = textview_date.toString() + "-" + textview_time.toString()
        }


        mDateTimeBtn.setOnClickListener {
            this.context?.let { it1 ->
                DatePickerDialog(
                    it1, dateSetListener,
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH)).show()
            }
        }


        binding.buttonCarSave.setOnClickListener {
            Log.d(TAG, "onViewCreated() => Button SAVE clicked. Invoke CarApiService")
            Log.d(TAG, "onViewCreated() => args = " + args)

            if (args.startdate.isNullOrEmpty() && args.enddate.isNullOrEmpty()) {
                createOffer(args.id.toInt())
                Snackbar.make(
                    view,
                    "Offer on car with licenseplate " + args.licenseplate + " created",
                    Snackbar.LENGTH_LONG
                )
                    .show()
                Log.d(
                    TAG,
                    "onViewCreated() => Offer made for car " + args.licenseplate + ". Return to home"
                )
                findNavController().navigate(R.id.action_offerCarFragment_to_mycars)
            } else {
                // TODO Figure out if there's a way to determine where you came from. From edit offer? Then invoek updateOffer, else createOffer
                Log.d(TAG, "onViewCreated() => Offer updated for car " + args.licenseplate + ". Return to my offers")
                updateOffer()
                findNavController().navigate(R.id.action_offerCarFragment2_to_myOffersFragment)
            }
        }
    }

    /**
     * Binds views with the passed in item data.
     */
    fun bindUI(args: OfferCarFragmentArgs) {
        Log.d(TAG, "bindUI()")
        binding.txtInputOfferCarStartDate.setText(args.startdate)
        binding.txtInputOfferCarEndDate.setText(args.enddate)
        binding.txtInputOfferCarLocation.setText(args.location)
        binding.textviewOfferCarLicensePlate.text = (args.licenseplate)
    }

    private fun createOffer(id: Int) {
        Log.d(TAG, "createOffer()")
        val offerViewModel: OfferViewModel by viewModels()

        // Cast inputfields
        val offerStartDate: TextInputEditText = binding.txtInputOfferCarStartDate
        val startDate: String = offerStartDate.text.toString()

        val offerEndDate: TextInputEditText = binding.txtInputOfferCarEndDate
        val endDate: String = offerEndDate.text.toString()

        val offerLocation: TextInputEditText = binding.txtInputOfferCarLocation
        val location: String = offerLocation.text.toString()

        val carId : Long = id.toLong()

        Log.d(TAG, "createOffer() => startDate = " + startDate)
        Log.d(TAG, "createOffer() => endDate = " + endDate)
        Log.d(TAG, "createOffer() => location = " + location)
        Log.d(TAG, "createOffer() => carId = " + carId)

        offerViewModel.createOffer(startDate, endDate, location, carId)
        Log.d(TAG, "createOffer() => After calling viewModel.createOffer().")
    }

    private fun updateOffer(){
        Log.d(TAG, "updateOffer()")
    }
}