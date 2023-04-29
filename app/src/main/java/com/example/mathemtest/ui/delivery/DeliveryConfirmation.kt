package com.example.mathemtest.ui.delivery

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mathemtest.api.DeliveryTime
import com.example.mathemtest.components.Grid
import com.example.mathemtest.components.HeaderText
import com.example.mathemtest.components.SubText
import com.example.mathemtest.ui.theme.MathemTheme

@Composable
fun DeliveryConfirmation(
    dateSelected: String,
    timeSelected: DeliveryTime?,
    onComplete: () -> Unit,
    onGoBack: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .padding(Grid.SMALL.value)
                .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            HeaderText(
                text = "Confirm delivery"
            )
            Spacer(modifier = Modifier.height(Grid.SMALL.value))
            Text(
                text = "Your delivery is scheduled to arrive $dateSelected between ${timeSelected?.startTime} - ${timeSelected?.stopTime}",
                textAlign = TextAlign.Center
            )

            if (timeSelected != null && timeSelected.inHomeAvailable) {
                Spacer(modifier = Modifier.height(Grid.SMALL.value))
                SubText(text = "In-home delivery")
            }

            Row(
                modifier = Modifier
                    .padding(Grid.SMALL.value),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = { onGoBack() },
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                ) {
                    Text("Go back")
                }

                Spacer(modifier = Modifier.width(8.dp))

                Button(
                    onClick = { onComplete() },
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                ) {
                    Text("Complete")
                }
            }
        }
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun DeliveryConfirmationPreview() {
    val mockDeliveryTime = DeliveryTime(
        deliveryTimeId = "3",
        deliveryDate = "2023-04-28",
        startTime = "12:00",
        stopTime = "14:00",
        inHomeAvailable = true
    )

    MathemTheme {
        DeliveryConfirmation(
            dateSelected = "2022-04-28",
            timeSelected = mockDeliveryTime,
            onComplete = {},
            onGoBack = {}
        )
    }
}