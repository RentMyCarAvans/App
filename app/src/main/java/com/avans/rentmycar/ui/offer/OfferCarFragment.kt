package com.avans.rentmycar.ui.offer

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Build
import android.os.Bundle
import android.se.omapi.Session
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.DatePicker
import android.widget.TextView
import android.widget.TimePicker
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.avans.rentmycar.R
import com.avans.rentmycar.databinding.FragmentOfferBinding
import com.avans.rentmycar.model.response.BaseResponse
import com.avans.rentmycar.model.response.Data
import com.avans.rentmycar.model.response.RdwResponseItem
import com.avans.rentmycar.utils.Constant
import com.avans.rentmycar.utils.SessionManager
import com.avans.rentmycar.viewmodel.CarViewModel
import com.avans.rentmycar.viewmodel.OfferViewModel
import com.avans.rentmycar.viewmodel.UserViewModel
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import java.time.LocalDateTime
import java.time.LocalDateTime.now
import java.util.*

/**
 * A simple [Fragment] subclass.
 * Use the [OfferCarFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class OfferCarFragment : Fragment(), DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    private val TAG = "[RMC][OfferCarFrg]"

    // var for binding the fragment_edit_car layout
    private var _binding: FragmentOfferBinding? = null
    private val binding get() = _binding!!

    // Declare viewmodel
    private val viewModel: UserViewModel by viewModels()

    //
    var day = 0
    var month = 0
    var year = 0
    var hour = 0
    var minute = 0

    var saveMinute = 0
    var saveDay = 0
    var saveMonth = 0
    var saveYear = 0
    var saveHour = 0

    var offerId: Long = 0L
    var carId: Long = 0L

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

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args: OfferCarFragmentArgs by navArgs()
        Log.d(TAG, "onViewCreated() => Checking navArgs Car offer")
        Log.d(TAG, "onViewCreated() => navArgs: " + args.toString())

        // Observe userresult when retrieving userdata
        viewModel.userResult.observe(viewLifecycleOwner) {
            Log.d(TAG, "onViewCreated() => observer userResult triggerd:" + viewModel.userResult.value.toString())
            //bindIt(it)
            Log.d(TAG, "onViewCreated() => it" + it.toString())
            when (it) {

                is BaseResponse.Loading -> {
                    Log.d(TAG, "onViewCreated() => response loading")
                }
                is BaseResponse.Error -> {
                    Log.d(TAG, "onViewCreated() => ERROR, API DOWN?")
                    Snackbar.make(view, "Error", Snackbar.LENGTH_LONG).show()
                }
                is BaseResponse.Success -> {
                    Log.d(TAG, "onViewCreated() => success")
                    binding!!.txtInputOfferCarLocation.setText(it.data?.data?.address + ", " + it.data?.data?.city)
                }
            }
        }

        bindUI(args)
        setDefaults(args)

        val mBtn_dateTimePicker = view.findViewById<Button>(R.id.button_car_offer_start_datetime)

        mBtn_dateTimePicker.setOnClickListener {
            Log.d(TAG, "onViewCreated() => Button STARTDATE clicked")
            getDateTimeCalender()
            DatePickerDialog(it.context,this,year, month, day).show()
        }

        binding.buttonCarSave.setOnClickListener {
            Log.d(TAG, "onViewCreated() => Button SAVE clicked. Invoke CarApiService")
            Log.d(TAG, "onViewCreated() => args = " + args)
            Log.d(TAG, "onViewCreated() => Carid = " + args.carid)

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
        binding.textviewOfferCarStartDatetime.text = args.startdate
        binding.textviewOfferCarEndDatetime.text = args.enddate
        binding.txtInputOfferCarLocation.setText(args.location)
        // binding.txtInputOfferCarLocation.setText(SessionManager.getDeviceLocationReadable())
        binding.textviewOfferCarLicensePlate.text = (args.licenseplate)
        offerId = args.id.toLong()
        carId = args.carid
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun setDefaults(args: OfferCarFragmentArgs){
        Log.d(TAG, "setDefaults()")

        if (args.startdate.isNullOrEmpty() && args.enddate.isNullOrEmpty()) {
            Log.d(TAG, "setDefaults() => setting default dates on startdate and enddate because they are null")
            Log.d(TAG, "setDefaults() => Default startdate: " + getCurrentDateTime(0))
            Log.d(TAG, "setDefaults() => Default enddate: " + getCurrentDateTime(8))
            binding.textviewOfferCarStartDatetime.text = getCurrentDateTime(0)
            binding.textviewOfferCarEndDatetime.text = getCurrentDateTime(8)
        }

        // If no location is provided, then use the address of the current user as a default location
        if (args.location.isNullOrEmpty()){
            val userViewModel: UserViewModel by viewModels()
            val userId = SessionManager.getUserId(requireContext())
            userViewModel.getUser(userId!!)
        }
    }

    private fun createOffer(id: Int) {
        Log.d(TAG, "createOffer()")
        val offerViewModel: OfferViewModel by viewModels()

        // Cast inputfields
        val offerStartDate: TextView = binding.textviewOfferCarStartDatetime
        val startDate: String = offerStartDate.text.toString()

        val offerEndDate: TextView = binding.textviewOfferCarEndDatetime
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
        val offerViewModel: OfferViewModel by viewModels()

        // Cast inputfields
        val offerStartDate: TextView = binding.textviewOfferCarStartDatetime
        val startDate: String = offerStartDate.text.toString()

        val offerEndDate: TextView = binding.textviewOfferCarEndDatetime
        val endDate: String = offerEndDate.text.toString()

        val offerLocation: TextInputEditText = binding.txtInputOfferCarLocation
        val location: String = offerLocation.text.toString()

        Log.d(TAG, "updateOffer() => startDate = " + startDate)
        Log.d(TAG, "updateOffer() => endDate = " + endDate)
        Log.d(TAG, "updateOffer() => location = " + location)
        Log.d(TAG, "updateOffer() => carId = " + carId)

        offerViewModel.updateOffer(offerId, startDate, endDate, location, carId)
        Log.d(TAG, "createOffer() => After calling viewModel.createOffer().")
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        Log.d(TAG, "onDateSet()" )
        saveDay = dayOfMonth
        saveMonth = month
        saveYear = year

        Log.d(TAG, "onDateSet() before getDateTimeCalender: $saveDay-$saveMonth-$saveYear" )
        getDateTimeCalender()
        Log.d(TAG, "onDateSet() after getDateTimeCalender: $saveDay-$saveMonth-$saveYear" )

        TimePickerDialog(context, this, hour, minute, true).show()
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        Log.d(TAG, "onTimeSet()" )
        saveHour = hourOfDay
        saveMinute = minute
        Log.d(TAG, "onTimeSet() : $saveHour:$saveMinute" )

        var date = "$saveYear-$saveMonth-$saveDay $saveHour:$saveMinute"
        binding.textviewOfferCarStartDatetime.text =date
    }

    private fun getDateTimeCalender(){
        val cal = Calendar.getInstance()
        year = cal.get(Calendar.YEAR)
        month = cal.get(Calendar.MONTH)
        day = cal.get(Calendar.DAY_OF_MONTH) + 1 // start next day
        hour = cal.get(Calendar.HOUR_OF_DAY)
        minute = cal.get(Calendar.MINUTE)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getCurrentDateTime(added_hours: Int) : String{
        val calendar = Calendar.getInstance()
        val current = LocalDateTime.of(
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH) + 1,
            calendar.get(Calendar.DAY_OF_MONTH) + 1,
            calendar.get(Calendar.HOUR_OF_DAY) + added_hours,
            calendar.get(Calendar.MINUTE),
            calendar.get(Calendar.SECOND)
        )

        return current.toString()
    }

}