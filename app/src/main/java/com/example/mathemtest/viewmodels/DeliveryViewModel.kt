package com.example.mathemtest.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mathemtest.api.DeliveryTime
import com.example.mathemtest.api.MathemRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DeliveryViewModel @Inject constructor(private val repository: MathemRepository) : ViewModel() {

    private val _viewState = MutableStateFlow<ViewState>(ViewState.DeliveryOptions)
    val viewState: StateFlow<ViewState> = _viewState

    private val _deliveryData = MutableStateFlow(DeliveryData())
    val deliveryData: StateFlow<DeliveryData> = _deliveryData

    init {
        fetchDeliveryDates()
    }

    private fun fetchDeliveryDates() {
        viewModelScope.launch {
            val deliveryDates = repository.getDeliveryDates()
            _deliveryData.value = _deliveryData.value.copy(deliveryDates = deliveryDates)
        }
    }

    fun fetchDeliveryTimes(datestamp: String) {
        _deliveryData.value = _deliveryData.value
            .updateSelectedDate(datestamp)
            .updateSelectedTime(null)

        viewModelScope.launch {
            val deliveryTimes = repository.getDeliveryTimes(datestamp).sortedBy { it.startTime }
            _deliveryData.value = _deliveryData.value.updateDeliveryTimes(deliveryTimes)
        }
    }

    fun setInHomeDelivery(inHomeDelivery: Boolean) {
        _deliveryData.value = _deliveryData.value.updateInHomeDelivery(inHomeDelivery)
    }

    fun setDeliveryTime(deliveryTime: DeliveryTime) {
        _deliveryData.value = _deliveryData.value.updateSelectedTime(deliveryTime)
    }

    fun onContinueButtonClicked() {
        _viewState.value = ViewState.DeliveryConfirmation(
            date = _deliveryData.value.selectedDate,
            time = _deliveryData.value.selectedTime
        )
    }

    fun onBackPressed() {
        _viewState.value = ViewState.DeliveryOptions
    }

    sealed class ViewState {
        object DeliveryOptions : ViewState()
        data class DeliveryConfirmation(val date: String, val time: DeliveryTime?) : ViewState()
    }
}

data class DeliveryData(
    val deliveryDates: List<String> = emptyList(),
    val deliveryTimes: List<DeliveryTime> = emptyList(),
    val inHomeDelivery: Boolean = false,
    val selectedTime: DeliveryTime? = null,
    val selectedDate: String = ""
) {
    fun updateDeliveryTimes(deliveryTimes: List<DeliveryTime>): DeliveryData {
        return this.copy(deliveryTimes = deliveryTimes)
    }

    fun updateInHomeDelivery(inHomeDelivery: Boolean): DeliveryData {
        return this.copy(inHomeDelivery = inHomeDelivery)
    }

    fun updateSelectedTime(selectedTime: DeliveryTime?): DeliveryData {
        return this.copy(selectedTime = selectedTime)
    }

    fun updateSelectedDate(selectedDate: String): DeliveryData {
        return this.copy(selectedDate = selectedDate)
    }
}