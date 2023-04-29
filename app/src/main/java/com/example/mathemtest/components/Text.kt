package com.example.mathemtest.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.mathemtest.ui.theme.MathemTheme

@Composable
fun HeaderText(
    modifier: Modifier = Modifier,
    text: String
) {
    Text(
        text = text,
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold,
        modifier = modifier
    )
}

@Composable
fun SubText(
    modifier: Modifier = Modifier,
    text: String
) {
    Text(
        text = text,
        fontSize = 12.sp,
        modifier = modifier
    )
}

@Preview
@Composable
fun HeaderTextPreview() {
    MathemTheme {
        HeaderText(text = "Header")
    }
}

@Preview
@Composable
fun SubTextPreview() {
    MathemTheme {
        SubText(text = "Subtext")
    }
}