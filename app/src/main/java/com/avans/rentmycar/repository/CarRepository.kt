package com.avans.rentmycar.repository

import com.avans.rentmycar.R
import com.avans.rentmycar.model.CarModel

object CarRepository {
    val carModels = mutableListOf<CarModel>(
        // TODO Replace with actual API call
        CarModel(R.drawable.lamborghini, "Lamborghini Diablo", "Internal Combustion Engine", "1099km $207 p/hr"),
        CarModel(R.drawable.porsche, "Porsche Carrera 911", "Internal Combustion Engine", "11599km $159 p/hr"),
        CarModel(R.drawable.ferrari, "Ferrari Testarossa", "Internal Combustion Engine", "40799km $219 p/hr"),
        CarModel(R.drawable.tesla, "Tesla Model S", "Electric", "29874km $109 p/hr"),
        CarModel(R.drawable.mercedes, "Mercedes X", "Fuel Cell Elctric Vehicle", "12587km $119 p/hr"),
        CarModel(R.drawable.mclaren, "McLaren F1", "Internal Combustion Engine", "3911km $219 p/hr"),
        CarModel(R.drawable.audi, "Audi Q7", "Electric", "7899km $129 p/hr"),
        CarModel(R.drawable.bmw, "BMW X6", "Fuel Cell Elctric Vehicle", "4766km $129 p/hr"),
        CarModel(R.drawable.lamborghini, "Lamborghini Diablo 2", "Internal Combustion Engine", "1099km $207 p/hr"),
        CarModel(R.drawable.porsche, "Porsche Carrera 911 2", "Internal Combustion Engine", "11599km $159 p/hr"),
        CarModel(R.drawable.ferrari, "Ferrari Testarossa 2", "Internal Combustion Engine", "40799km $219 p/hr"),
        CarModel(R.drawable.tesla, "Tesla Model S 2", "Electric", "29874km $109 p/hr"),
        CarModel(R.drawable.mercedes, "Mercedes X 2", "Fuel Cell Elctric Vehicle", "12587km $119 p/hr"),
        CarModel(R.drawable.mclaren, "McLaren F1 2", "Internal Combustion Engine", "3911km $219 p/hr"),
        CarModel(R.drawable.audi, "Audi Q7 2", "Electric", "7899km $129 p/hr"),
        CarModel(R.drawable.bmw, "BMW X6 2", "Fuel Cell Elctric Vehicle", "4766km $129 p/hr"),
    )
}