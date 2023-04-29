package com.example.mathemtest.ui.delivery

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mathemtest.R
import com.example.mathemtest.api.DeliveryTime
import com.example.mathemtest.components.Grid
import com.example.mathemtest.components.HeaderText
import com.example.mathemtest.components.SubText
import com.example.mathemtest.components.SwitchWithText
import com.example.mathemtest.ui.theme.MathemTheme
import com.example.mathemtest.utils.getFriendlyDateFormat
import com.example.mathemtest.viewmodels.DeliveryViewModel

@Composable
fun DeliveryScreen(
    viewModel: DeliveryViewModel = viewModel()
) {
    val viewState by viewModel.viewState.collectAsState()

    when (val view = viewState) {
        is DeliveryViewModel.ViewState.DeliveryOptions -> {
            val deliveryData by viewModel.deliveryData.collectAsState()

            DeliveryList(
                dateList = deliveryData.deliveryDates,
                timeList = deliveryData.deliveryTimes,
                inHomeDelivery = deliveryData.inHomeDelivery,
                selectedItemId = deliveryData.selectedTime?.deliveryTimeId ?: "",
                onDateSelected = {
                    viewModel.fetchDeliveryTimes(it)
                },
                onTimeSelected = {
                    viewModel.setDeliveryTime(it)
                },
                onInHomeDeliveryChanged = {
                    viewModel.setInHomeDelivery(it)
                },
                onContinueButtonClicked = {
                    viewModel.onContinueButtonClicked()
                }
            )
        }
        is DeliveryViewModel.ViewState.DeliveryConfirmation -> {
            DeliveryConfirmation(
                dateSelected = view.date,
                timeSelected = view.time,
                onComplete = { /* end of specification */ },
                onGoBack = { viewModel.onBackPressed() })
        }
    }
}

@Composable
fun DeliveryList(
    dateList: List<String>,
    timeList: List<DeliveryTime>,
    inHomeDelivery: Boolean,
    selectedItemId: String,
    onDateSelected: (String) -> Unit,
    onTimeSelected: (DeliveryTime) -> Unit,
    onInHomeDeliveryChanged: (Boolean) -> Unit,
    onContinueButtonClicked: () -> Unit
) {
    val listState = rememberLazyListState()

    Column(
        modifier = Modifier.padding(Grid.SMALL.value)
    ) {
        HeaderText(
            text = "Select a date for delivery"
        )
        Spacer(modifier = Modifier.height(Grid.SMALL.value))
        LazyRow(state = listState) {
            items(items = dateList) { item ->
                Box(modifier = Modifier.padding(Grid.TINY.value)
                ) {
                    DeliveryItem(item = item) {
                        onDateSelected(it)
                    }
                }
            }
        }

        if (timeList.isNotEmpty()) {
            Spacer(modifier = Modifier.height(Grid.MEDIUM.value))
            SwitchWithText(
                modifier = Modifier
                    .fillMaxWidth(),
                text = "In home delivery",
                switchState = inHomeDelivery) {
                    onInHomeDeliveryChanged(it)
            }
            Spacer(modifier = Modifier.height(Grid.LARGE.value))
            HeaderText(
                text = "Select time for dropoff"
            )
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                LazyColumn(
                    contentPadding = PaddingValues(bottom = 100.dp)
                ) {
                    val sortedTimeList = if (inHomeDelivery) {
                        timeList.filter { it.inHomeAvailable }
                    } else {
                        timeList
                    }

                    items(sortedTimeList) { item ->
                        DeliveryTimeItem(
                            item = item,
                            selectedItemId = selectedItemId) {
                            onTimeSelected(it)
                        }
                        Divider(color = Color.LightGray, thickness = 1.dp)
                    }
                }
                Button(
                    onClick = onContinueButtonClicked,
                    enabled = selectedItemId.isNotEmpty(),//continueButtonEnabled,
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .fillMaxWidth()
                        .height(56.dp)
                ) {
                    Text("Continue")
                }
            }
        }
    }
}

@Composable
fun DeliveryItem(
    item: String,
    onItemSelected: (String) -> Unit
) {
    Card(
        modifier = Modifier
            .width(100.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(Grid.MEDIUM.value)
                .fillMaxWidth()
                .clickable { onItemSelected(item) },
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            val friendlyDate = getFriendlyDateFormat(item)
            SubText(text = friendlyDate.dayOfWeek)
            HeaderText(text = friendlyDate.dayOfMonth)
            SubText(text = "${friendlyDate.month}.")
        }
    }
}

@Composable
fun DeliveryTimeItem(
    item: DeliveryTime,
    selectedItemId: String,
    onItemSelected: (DeliveryTime) -> Unit
) {
    val isSelected = item.deliveryTimeId == selectedItemId

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(Grid.SMALL.value)
    ) {
        RadioButton(
            selected = isSelected,
            onClick = { onItemSelected(item) }
        )
        Text(
            text = "${item.startTime} - ${item.stopTime}",
        )
        if (item.inHomeAvailable) {
            Spacer(modifier = Modifier.weight(1f))
            Image(
                painter = painterResource(R.drawable.baseline_home_24),
                contentDescription = "In home delivery available",
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun DeliveryListPreview() {
    val mockDateList = listOf(
        "2023-04-28",
        "2023-04-29",
        "2023-04-30",
        "2023-05-01",
        "2023-05-02"
    )

    val mockTimeList = listOf(
        DeliveryTime(
            deliveryTimeId = "1",
            deliveryDate = "2023-04-28",
            startTime = "08:00",
            stopTime = "10:00",
            inHomeAvailable = true
        ),
        DeliveryTime(
            deliveryTimeId = "2",
            deliveryDate = "2023-04-28",
            startTime = "10:00",
            stopTime = "12:00",
            inHomeAvailable = false
        ),
        DeliveryTime(
            deliveryTimeId = "3",
            deliveryDate = "2023-04-28",
            startTime = "12:00",
            stopTime = "14:00",
            inHomeAvailable = true
        )
    )

    MathemTheme {
        DeliveryList(
            dateList = mockDateList,
            timeList = mockTimeList,
            inHomeDelivery = false,
            selectedItemId = "",
            onDateSelected = {},
            onTimeSelected = {},
            onInHomeDeliveryChanged = {},
            onContinueButtonClicked = {}
        )
    }
}
