package com.dam.test412.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dam.test412.ui.theme.Test412Theme

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    snackbarHostState: SnackbarHostState= SnackbarHostState()
) {
    Surface(
        modifier = modifier.
        fillMaxSize()
        .padding(8.dp)
    ) {

    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    Test412Theme {
        HomeScreen()
    }
}
