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
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.LocalDateTime.now
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
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

    var setDateTimeForEnd = false

    var saveMinuteEnd = 0
    var saveDayEnd = 0
    var saveMonthEnd = 0
    var saveYearEnd = 0
    var saveHourEnd = 0

    var offerId: Long = 0L
    var carId: Long = 0L

    val defaultHoursToAddForEndDateTime = 8

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOfferBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args: OfferCarFragmentArgs by navArgs()

        // Observe userresult when retrieving userdata
        viewModel.userResult.observe(viewLifecycleOwner) {
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
        val mBtn_dateTimePickerEnd = view.findViewById<Button>(R.id.button_car_offer_end_datetime)

        mBtn_dateTimePicker.setOnClickListener {
            setDateTimeForEnd = false
            getDateTimeCalender()
            DatePickerDialog(it.context,this,year, month, day).show()
        }

        mBtn_dateTimePickerEnd.setOnClickListener {
            setDateTimeForEnd = true
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
            } else {
                Log.d(TAG, "onViewCreated() => Offer updated for car " + args.licenseplate + ". Return to my offers")
                updateOffer()
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
        if(args.location.isNullOrEmpty()) {
            binding.txtInputOfferCarLocation.setText(SessionManager.getDeviceLocationReadable())
        }
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
            Log.d(TAG, "setDefaults() => Default enddate: " + getCurrentDateTime(defaultHoursToAddForEndDateTime))
            binding.textviewOfferCarStartDatetime.text = getCurrentDateTime(0)
            binding.textviewOfferCarEndDatetime.text = getCurrentDateTime(defaultHoursToAddForEndDateTime)
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


        if (checkIfEndDateIsAfterStartDate(startDate, endDate)) {
            offerViewModel.createOffer(startDate, endDate, location, carId)
            findNavController().navigate(R.id.action_offerCarFragment_to_mycars)
        } else {
            Snackbar.make(
                binding.root,
                "Start date can not be after end date",
                Snackbar.LENGTH_LONG
            ).show()
        }

    }

    private fun checkIfEndDateIsAfterStartDate(startDate: String, endDate: String): Boolean {
        Log.d(TAG, "checkIfEndDateIsAfterStartDate()")
        val startDateTime = LocalDateTime.parse(startDate, DateTimeFormatter.ISO_DATE_TIME).toEpochSecond(
            ZoneOffset.UTC)
        val endDateTime = LocalDateTime.parse(endDate, DateTimeFormatter.ISO_DATE_TIME).toEpochSecond(ZoneOffset.UTC)
        Log.d(TAG, "checkIfEndDateIsAfterStartDate() => startDateTime = " + startDateTime)
        Log.d(TAG, "checkIfEndDateIsAfterStartDate() => endDateTime = " + endDateTime)
        return endDateTime> startDateTime
        Log.d(TAG, "checkIfEndDateIsAfterStartDate() => startDateTime = " + startDate)
        Log.d(TAG, "checkIfEndDateIsAfterStartDate() => endDateTime = " + endDate)
        return endDateTime>startDateTime
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

        if (checkIfEndDateIsAfterStartDate(startDate, endDate)) {
            offerViewModel.updateOffer(offerId, startDate, endDate, location, carId)
            findNavController().navigate(R.id.action_offerCarFragment2_to_myOffersFragment)
        } else {
            Snackbar.make(
                binding.root,
                "Start date can not be after end date",
                Snackbar.LENGTH_LONG
            ).show()
        }


    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        Log.d(TAG, "onDateSet()" )
        if(!setDateTimeForEnd) {
            saveDay = dayOfMonth
            saveMonth = month + 1
            saveYear = year
            Log.d(TAG, "onDateSet() : $saveDay-$saveMonth-$saveYear" )

        } else {
            saveDayEnd = dayOfMonth
            saveMonthEnd = month + 1
            saveYearEnd = year
            Log.d(TAG, "onDateSet() : $saveDayEnd-$saveMonthEnd-$saveYearEnd" )

        }

        getDateTimeCalender()
//        Log.d(TAG, "onDateSet() after getDateTimeCalender: $saveDay-$saveMonth-$saveYear" )

        TimePickerDialog(context, this, hour, minute, true).show()
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        Log.d(TAG, "onTimeSet()" )
        if(!setDateTimeForEnd) {
            saveHour = hourOfDay
            saveMinute = minute
            Log.d(TAG, "onTimeSet() !setDateTimeForEnd: $saveHour:$saveMinute")

            var date = "$saveYear-${saveMonth.toString().padStart(2, '0')}-${
                saveDay.toString().padStart(2, '0')
            }T${saveHour.toString().padStart(2, '0')}:${saveMinute.toString().padStart(2, '0')}:00"
            binding.textviewOfferCarStartDatetime.text = date
        } else {
            saveHourEnd = hourOfDay
            saveMinuteEnd = minute
            Log.d(TAG, "onTimeSet() YES END setDateTimeForEnd: $saveHourEnd:$saveMinuteEnd")

            var dateEnd = "$saveYearEnd-${saveMonthEnd.toString().padStart(2, '0')}-${
                saveDayEnd.toString().padStart(2, '0')
            }T${saveHourEnd.toString().padStart(2, '0')}:${saveMinuteEnd.toString().padStart(2, '0')}:00"
            binding.textviewOfferCarEndDatetime.text = dateEnd
        }
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
        calendar.timeZone = TimeZone.getDefault()
        val current = LocalDateTime.of(
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH) + 1,
            calendar.get(Calendar.DAY_OF_MONTH) + 1,
            calendar.get(Calendar.HOUR_OF_DAY),
            calendar.get(Calendar.MINUTE),
            calendar.get(Calendar.SECOND)
        )

        // Add hours to current date if needed
        if(added_hours > 0){
            val adjustedDate = current.plusHours(added_hours.toLong())
            return adjustedDate.toString()
        }

        return current.toString()
    }

}