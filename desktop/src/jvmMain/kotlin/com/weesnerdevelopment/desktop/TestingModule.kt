package com.weesnerDevelopment.desktop

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun TestingModule(
    text: String,
    lowerPart: @Composable () -> Unit,
    buttonClick: (
        buttonText: (String) -> Unit
    ) -> Unit
) {
    val (buttonText, setButtonText) = remember { mutableStateOf(text) }
    val (buttonClicked, setButtonClicked) = remember { mutableStateOf(false) }

    if (buttonClicked) {
        setButtonClicked(false)
        buttonClick(setButtonText)
    }

    Button(
        modifier = Modifier.padding(4.dp),
        onClick = {
            setButtonClicked(true)
        }) {
        Text(buttonText)
    }

    lowerPart()
}