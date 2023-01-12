package com.avans.rentmycar.viewmodel

import androidx.lifecycle.*
import com.avans.rentmycar.room.Ride
import com.avans.rentmycar.room.RideDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.Instant

class RideViewModel(private val rideDao: RideDao) : ViewModel() {
    fun getRide(id: Long) : LiveData<Ride> {
        return rideDao.getRide(id).asLiveData()
    }
    fun updateRide(
       ride: Ride
    ) {
//        val ride = Ride(
//            rideId = ride.id,
//            startTimeStamp = startTimeStamp,
//            endTimeStamp = endTimeStamp,
//            startLatitude = startLatitude,
//            startLongitude = startLongitude,
//            endLatitude = endLatitude,
//            endLongitude = endLongitude
//        )
        viewModelScope.launch(Dispatchers.IO) {
            // call the DAO method to update a ride to the database
            rideDao.update(ride)
        }
    }
}
class RideViewModelFactory(private val rideDao: RideDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RideViewModel::class.java)) {
            return RideViewModel(rideDao) as T
        }
        throw java.lang.IllegalArgumentException("Unknown ViewModel")
    }
}