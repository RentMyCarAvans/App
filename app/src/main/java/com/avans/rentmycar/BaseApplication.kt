package com.avans.rentmycar

import android.app.Application
import com.avans.rentmycar.room.AppDatabase

class BaseApplication : Application() {

    val database: AppDatabase by lazy { AppDatabase.getDatabase(this) }
}