package com.avans.rentmycar.repository

import com.avans.rentmycar.model.OfferUiModel

class OfferRepository {

    fun getOffers(): List<OfferUiModel> {
        // Dit is mock data om de ui te testen.
        // TODO RS: Deze data moet vervangen worden door data uit de database.
        return listOf(
            OfferUiModel(
                "https://upload.wikimedia.org/wikipedia/commons/9/97/Lamborghini_Aventador_LP700-4_Orange.jpg",
                "Lamborghini Aventador",
                "1 dec 18:00 - 2 dec 12:00",
                "Tilburg"
            ),
            OfferUiModel(
                "https://prod.pictures.autoscout24.net/listing-images/9d76c122-3f11-4dd1-a37c-2ee61eeecf21_8c8e3cc2-ca5b-43b5-b53f-637d8dea6bbf.jpg/420x315_white-background.jpg",
                "Renault Kangoo",
                "4 dec 12:00 - 4 dec 15:00",
                "Rotterdam"
            ),
            OfferUiModel(
                "https://images.pexels.com/photos/210019/pexels-photo-210019.jpeg?auto=compress&cs=tinysrgb&h=650&w=940",
                "Porche",
                "2 dec 12:00 - 8 dec 12:00",
                "Vlissingen"
            ),

            )
    }


}