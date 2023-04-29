package com.example.mathemtest.components

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

enum class Grid(val value: Dp) {
    TINY(6.dp),
    SMALL(12.dp),
    MEDIUM(16.dp),
    LARGE(24.dp),
    EXTRA_LARGE(32.dp);
}