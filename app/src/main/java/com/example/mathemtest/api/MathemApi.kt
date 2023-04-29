package com.example.mathemtest.api

import retrofit2.http.GET
import retrofit2.http.Path

interface MathemApi {
    @GET("mh-test-assignment/delivery/dates")
    suspend fun getDeliveryDates(): List<String>

    @GET("mh-test-assignment/delivery/times/{datestamp}")
    suspend fun getDeliveryTimes(@Path("datestamp") datestamp: String): List<DeliveryTime>
}