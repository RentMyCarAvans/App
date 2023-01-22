package com.avans.rentmycar.ui.offer

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.TimePicker
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.avans.rentmycar.R
import com.avans.rentmycar.databinding.FragmentOfferBinding
import com.avans.rentmycar.utils.DateTimeConverter
import com.avans.rentmycar.utils.DateTimeConverter.formatCalendarToDBString
import com.avans.rentmycar.utils.SessionManager
import com.avans.rentmycar.viewmodel.BookingViewModel
import com.avans.rentmycar.viewmodel.OfferViewModel
import com.google.android.material.snackbar.Snackbar
import java.util.*

class OfferCarFragment : Fragment(), DatePickerDialog.OnDateSetListener,
    TimePickerDialog.OnTimeSetListener {
    private val TAG = "[RMC][OfferCarFrg]"

    // var for binding the fragment_edit_car layout
    private var _binding: FragmentOfferBinding? = null
    private val binding get() = _binding!!

    // Offer variables
    private var offerId: Long = 0L
    private var carId: Long = 0L
    private var bookingId: Long = 0L

    private val defaultHoursToAddForEndDateTime = 8
    private var currentPickerIsForStart = true

    // Create calendars for date and time
    var startDateTime: Calendar = Calendar.getInstance()
    var endDateTime: Calendar = Calendar.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOfferBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args: OfferCarFragmentArgs by navArgs()

        Log.d(TAG, "========== onViewCreated: args: $args")

        bindUI(args)
        setDefaults(args)

        binding.buttonCarOfferStartDatetime.isEnabled = true
        binding.buttonCarOfferEndDatetime.isEnabled = true
        binding.txtInputOfferCarLocation.isEnabled = true
        binding.textviewOffercarCustomer.visibility = View.GONE
        binding.buttonCarSave.visibility = View.VISIBLE

        val bookingViewModel = ViewModelProvider(requireActivity())[BookingViewModel::class.java]

        bookingViewModel.clearSingleBooking()

        bookingViewModel.bookingSingle.observe(viewLifecycleOwner) {
            Log.d(TAG, "onViewCreated: bookingSingle: $it")



            // Log the status of the booking
            if (it != null) {
                Log.d(TAG, "onViewCreated: booking status: ${it.status}")

                when (it.status) {
                    "PENDING" -> {

                        // Show the Customer name
                        binding.textviewOffercarCustomer.text = it.customer.firstName + " " + it.customer.lastName + " wants to rent your car!"
                        binding.textviewOffercarCustomer.visibility = View.VISIBLE

                        // Show ACCEPT and DECLINE buttons
//                        binding.acceptButton.visibility = View.VISIBLE
//                        binding.declineButton.visibility = View.VISIBLE

                        // Enable the date and time pickers and the location field
                        binding.buttonCarOfferStartDatetime.isEnabled = false
                        binding.buttonCarOfferEndDatetime.isEnabled = false
                        binding.txtInputOfferCarLocation.isEnabled = false

                        // Show the save button
                        binding.buttonCarSave.visibility = View.GONE


                    }
                    "APPROVED" -> {

                        // Get the name of the user who made the booking
                        Log.d(TAG, "onViewCreated: booking made by: ${it.customer}")
                        Log.d(
                            TAG,
                            "==========: booking made by: ${it.customer.firstName + " " + it.customer.lastName}"
                        )

                        // Show the Customer name
                        binding.textviewOffercarCustomer.text = "Booked by: " + it.customer.firstName + " " + it.customer.lastName
                        binding.textviewOffercarCustomer.visibility = View.VISIBLE


                        // Hide ACCEPT and DECLINE buttons
//                        binding.acceptButton.visibility = View.GONE
//                        binding.declineButton.visibility = View.GONE

                        // Disable the date and time pickers and the location field
                        binding.buttonCarOfferStartDatetime.isEnabled = false
                        binding.buttonCarOfferEndDatetime.isEnabled = false
                        binding.txtInputOfferCarLocation.isEnabled = false

                        // Hide the save button
                        binding.buttonCarSave.visibility = View.GONE


                    }

                }


            }

        }

        bookingViewModel.getBookingForOfferById(args.id.toLong())

        // === Button listeners ===
        binding.buttonCarOfferStartDatetime.setOnClickListener {
            currentPickerIsForStart = true
            val datePickerDialog = DatePickerDialog(
                requireContext(),
                this,
                startDateTime.get(Calendar.YEAR),
                startDateTime.get(Calendar.MONTH),
                startDateTime.get(Calendar.DAY_OF_MONTH)
            )
            datePickerDialog.show()
        }

        binding.buttonCarOfferEndDatetime.setOnClickListener {
            currentPickerIsForStart = false
            val datePickerDialog = DatePickerDialog(
                requireContext(),
                this,
                endDateTime.get(Calendar.YEAR),
                endDateTime.get(Calendar.MONTH),
                endDateTime.get(Calendar.DAY_OF_MONTH)
            )
            datePickerDialog.show()
        }

        binding.buttonCarSave.setOnClickListener {
            Log.d(TAG, "onViewCreated() => Button SAVE clicked. Invoke CarApiService")
            Log.d(TAG, "onViewCreated() => args = " + args)
            Log.d(TAG, "onViewCreated() => Carid = " + args.carid)
            saveOffer(args)
        }

    } // end onViewCreated()


    /**
     * Binds views with the passed in item data.
     */
    private fun bindUI(args: OfferCarFragmentArgs) {
        Log.d(TAG, "bindUI() => args = " + args)
        binding.textviewOfferCarStartDatetime.text = args.startdate
        binding.textviewOfferCarEndDatetime.text = args.enddate
        binding.txtInputOfferCarLocation.setText(args.location)
        if (args.location.isNullOrEmpty()) {
            binding.txtInputOfferCarLocation.setText(SessionManager.getDeviceLocationReadable())
        }
        binding.textviewOfferCarLicensePlate.text = (args.licenseplate)
        offerId = args.id.toLong()
        carId = args.carid
    } // end bindUI()


    private fun setDefaults(args: OfferCarFragmentArgs) {
        if (args.startdate.isNullOrEmpty() && args.enddate.isNullOrEmpty()) {
            // Set startDateTime to current date and time + 1 hour
            startDateTime = Calendar.getInstance()
            startDateTime.add(Calendar.HOUR_OF_DAY, 1)

            // Set endDateTime to current date and time + defaultHoursToAddForEndDateTime hours
            endDateTime = Calendar.getInstance()
            endDateTime.add(Calendar.HOUR_OF_DAY, defaultHoursToAddForEndDateTime)

        } else {
            startDateTime = DateTimeConverter.formatDBStringToCalendar(args.startdate)
            endDateTime = DateTimeConverter.formatDBStringToCalendar(args.enddate)
        }

        binding.textviewOfferCarStartDatetime.text =
            DateTimeConverter.formatCalendarToString(startDateTime)
        binding.textviewOfferCarEndDatetime.text =
            DateTimeConverter.formatCalendarToString(endDateTime)

    } // end setDefaults()

    fun isEndDateAfterStartDate(startDate: Calendar, endDate: Calendar): Boolean {
        binding.buttonCarSave.isEnabled = endDate.after(startDate)
        return endDate.after(startDate)
    } // end isEndDateAfterStartDate()

    private fun saveOffer(args: OfferCarFragmentArgs) {
        val offerViewModel: OfferViewModel by viewModels()
        val carId: Long = args.carid
        val location: String = binding.txtInputOfferCarLocation.text.toString()

        if (args.startdate.isNullOrEmpty() && args.enddate.isNullOrEmpty()) {
            offerViewModel.createOffer(
                formatCalendarToDBString(startDateTime),
                formatCalendarToDBString(endDateTime),
                location,
                carId
            )
            view?.let {
                Snackbar.make(
                    it,
                    "Offer on car with licenseplate " + args.licenseplate + " created",
                    Snackbar.LENGTH_LONG
                ).show()
            }
            findNavController().navigate(R.id.action_offerCarFragment_to_mycars)
        } else {
            offerViewModel.updateOffer(
                offerId,
                formatCalendarToDBString(startDateTime),
                formatCalendarToDBString(endDateTime),
                location,
                carId
            )
            view?.let {
                Snackbar.make(
                    it,
                    "Offer on car with licenseplate " + args.licenseplate + " updated",
                    Snackbar.LENGTH_LONG
                ).show()
            }
            findNavController().navigate(R.id.action_offerCarFragment2_to_myOffersFragment)
        }
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        if (currentPickerIsForStart) {
            startDateTime.set(year, month, dayOfMonth)
            TimePickerDialog(
                context,
                this,
                startDateTime.get(Calendar.HOUR_OF_DAY),
                startDateTime.get(Calendar.MINUTE),
                true
            ).show()
        } else {
            endDateTime.set(year, month, dayOfMonth)
            TimePickerDialog(
                context,
                this,
                endDateTime.get(Calendar.HOUR_OF_DAY),
                endDateTime.get(Calendar.MINUTE),
                true
            ).show()
        }
    } // end onDateSet()

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        if (currentPickerIsForStart) {
            startDateTime.set(Calendar.HOUR_OF_DAY, hourOfDay)
            startDateTime.set(Calendar.MINUTE, minute)
            binding.textviewOfferCarStartDatetime.text =
                DateTimeConverter.formatCalendarToString(startDateTime)
        } else {
            endDateTime.set(Calendar.HOUR_OF_DAY, hourOfDay)
            endDateTime.set(Calendar.MINUTE, minute)
            binding.textviewOfferCarEndDatetime.text =
                DateTimeConverter.formatCalendarToString(endDateTime)
        }
        if (!isEndDateAfterStartDate(startDateTime, endDateTime)) {
            Snackbar.make(
                binding.root, "Start date can not be after end date", Snackbar.LENGTH_LONG
            ).show()
        }
    } // end onTimeSet()




}