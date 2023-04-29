package com.example.mathemtest.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mathemtest.ui.theme.MathemTheme

@Composable
fun SwitchWithText(
    modifier: Modifier = Modifier,
    text: String,
    switchState: Boolean,
    onSwitchToggled: (Boolean) -> Unit
) {
    Row(
        modifier = modifier
            .border(
                width = 1.dp,
                color = Color.Gray,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(PaddingValues(8.dp)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Switch(
            checked = switchState,
            onCheckedChange = { newState -> onSwitchToggled(newState) }
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            modifier = Modifier.padding(start = Grid.TINY.value),
            text = text
        )
    }
}

@Preview
@Composable
fun SwitchWithTextPreview() {
    MathemTheme {
        SwitchWithText(
            switchState = false,
            onSwitchToggled = {},
            text = "Test"
        )
    }
}
