package com.example.mathemtest.api

class MathemRepository(private val api: MathemApi) {
    suspend fun getDeliveryDates(): List<String> {
        return api.getDeliveryDates()
    }

    suspend fun getDeliveryTimes(datestamp: String): List<DeliveryTime> {
        return api.getDeliveryTimes(datestamp)
    }
}