package com.dam.test421.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dam.test421.ui.theme.Test421Theme


@Composable
fun HomeScreen(
    onNextButtonClick: () -> Unit,
    modifier: Modifier = Modifier,
    snackbarHostState: SnackbarHostState = SnackbarHostState(),
    alumnosVM: AlumnosVM = viewModel()
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(8.dp),
    ) {
        FloatingActionButton(
            modifier = Modifier
                .padding(8.dp)
                .align(Alignment.BottomEnd),
            onClick = { onNextButtonClick() }) {
            Icon(Icons.Filled.Send, contentDescription = null)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    Test421Theme {
        HomeScreen(onNextButtonClick = {})
    }
}


