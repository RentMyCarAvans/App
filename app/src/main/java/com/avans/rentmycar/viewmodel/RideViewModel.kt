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

    fun startRide(rideId: Long, startLatitude: Double, startLongitude: Double, startTimeStamp: String) {
        val timeNow = Instant.now()
        val ride = Ride(rideId = rideId, startLatitude = startLatitude, startLongitude = startLongitude, endLatitude = null, endLongitude = null, startTimeStamp = startTimeStamp, endTimeStamp = null )

        viewModelScope.launch(Dispatchers.IO) {
            // call the DAO method to update a ride to the database
            rideDao.insert(ride)
        }
    }


    fun endRide(
        rideId: Long, startLatitude: Double?, startLongitude: Double?, startTimeStamp: String?, endLatitude: Double?, endLongitude: Double?, endTimeStamp: String?
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            // call the DAO method to update a ride to the database
            val ride = Ride(rideId = rideId, startLatitude = startLatitude, startLongitude = startLongitude, endLatitude = endLatitude, endLongitude = endLongitude, startTimeStamp = startTimeStamp, endTimeStamp = endTimeStamp )
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