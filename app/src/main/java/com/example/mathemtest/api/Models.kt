package com.example.mathemtest.api

import com.google.gson.annotations.SerializedName

data class DeliveryTime(
    @SerializedName("deliveryTimeId") val deliveryTimeId: String,
    @SerializedName("deliveryDate") val deliveryDate: String,
    @SerializedName("startTime") val startTime: String,
    @SerializedName("stopTime") val stopTime: String,
    @SerializedName("inHomeAvailable") val inHomeAvailable: Boolean
)