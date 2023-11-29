package com.dam.test421.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dam.test421.R
import com.dam.test421.data.Alumno
import com.dam.test421.data.DataSource.alumnos
import com.dam.test421.ui.theme.Test421Theme

@Composable
fun AlumnosBus(
    onNextButtonClick: () -> Unit,
    modifier: Modifier = Modifier,
    alumnosVM: AlumnosVM = viewModel(),
    snackbarHostState: SnackbarHostState = SnackbarHostState(),
) {

    val uiState = alumnosVM.uiState

    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(4.dp)
    ) {
        LazyColumn(
            modifier = modifier.fillMaxSize()
        ) {
            itemsIndexed(alumnos) { index, it ->
                AlumnoCard(
                    alumno = it,
                    itemSelected = uiState.alumnoSelected == index,
                    onItemSelectedChange = {
                        alumnosVM.setAlumnoSelected(if (uiState.alumnoSelected != index) index else -1)
                    })
            }
        }
        FloatingActionButton(
            modifier = Modifier
                .padding(8.dp)
                .align(Alignment.BottomEnd),
            onClick = { onNextButtonClick() }) {
            Icon(Icons.Filled.Send, contentDescription = null)
        }
    }
}

@Composable
fun AlumnoCard(
    alumno: Alumno,
    itemSelected: Boolean,
    onItemSelectedChange: () -> Unit,
    modifier: Modifier = Modifier
) {

    Card(
        modifier = modifier
            .padding(4.dp)
            .clickable { onItemSelectedChange() },
        border = if (itemSelected) BorderStroke(1.dp, Color.Black) else null
    ) {
        Row(
            modifier = Modifier.fillMaxSize()
        ) {
            Box {
                Image(
                    painter = painterResource(R.drawable.student),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(8.dp)
                        .size(100.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
            ) {
                Text(text = alumno.dni, fontWeight = FontWeight.Bold)
                Text(text = alumno.nombre, fontSize = 20.sp, fontStyle = FontStyle.Italic)
                Spacer(modifier = Modifier.height(20.dp))
                Text(text = alumno.titulo.toString(), fontSize = 16.sp)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AlumnosListaPreview() {
    Test421Theme {
        AlumnosBus(onNextButtonClick = {})
    }
}