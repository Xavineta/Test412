package com.dam.test421.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dam.test421.R
import com.dam.test421.data.Titulo
import com.dam.test421.ui.theme.Test421Theme
import kotlinx.coroutines.launch

@Composable
fun AlumnosMto(
    modifier: Modifier = Modifier,
    alumnosVM: AlumnosVM = viewModel(),
    snackbarHostState: SnackbarHostState = SnackbarHostState()
) {
    val uiState = alumnosVM.uiState
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(8.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.Top
        ) {
            OutlinedTextField(
                value = uiState.dni,
                onValueChange = { alumnosVM.setDni(it) },
                modifier = Modifier.fillMaxWidth(),
                label = {
                    Text(
                        text = stringResource(R.string.txt_mto_dni), fontWeight = FontWeight.Bold
                    )
                },
                isError = !uiState.datosObligatorios,
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Characters,
                    keyboardType = KeyboardType.Text
                ),
                singleLine = true
            )
            OutlinedTextField(
                value = uiState.nombre,
                onValueChange = { alumnosVM.setNombre(it) },
                modifier = Modifier.fillMaxWidth(),
                label = {
                    Text(
                        text = stringResource(R.string.txt_mto_nombre), fontWeight = FontWeight.Bold
                    )
                },
                isError = !uiState.datosObligatorios,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text
                ),
                singleLine = true
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .selectableGroup(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = stringResource(R.string.txt_titulo), fontWeight = FontWeight.Bold)
                RadioButton(
                    selected = uiState.titulo == Titulo.SMR,
                    onClick = { alumnosVM.setTitulo(Titulo.SMR) })
                Text(text = stringResource(R.string.txt_titulo_smr))
                RadioButton(
                    selected = uiState.titulo == Titulo.DAM,
                    onClick = { alumnosVM.setTitulo(Titulo.DAM) })
                Text(text = stringResource(R.string.txt_titulo_dam))
            }

        }
        AlumnoMtoAcciones(onClickDelete = {
            val ok = alumnosVM.baja()
            if (ok) alumnosVM.resetDatos()
            scope.launch {
                snackbarHostState.showSnackbar(
                    context.getString(if (ok) R.string.msg_baja_ok else R.string.msg_baja_ko)
                )
            }
        }, onClickAdd = {
            val ok = alumnosVM.alta()
            if (ok) alumnosVM.resetDatos()
            scope.launch {
                snackbarHostState.showSnackbar(
                    context.getString(if (ok) R.string.msg_alta_ok else R.string.msg_alta_ko)
                )
            }
        },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .align(Alignment.BottomEnd)
        )
    }
}

@Composable
fun AlumnoMtoAcciones(
    onClickDelete: () -> Unit, onClickAdd: () -> Unit, modifier: Modifier = Modifier
) {

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End,
    ) {
        FloatingActionButton(
            onClick = onClickDelete, modifier = Modifier.width(120.dp)
        ) {
            //Text(text = stringResource(R.string.but_cancelar))
            Icon(imageVector = Icons.Default.Delete, contentDescription = null)
        }
        Spacer(modifier = Modifier.width(8.dp))
        FloatingActionButton(
            onClick = onClickAdd, modifier = Modifier.width(120.dp)
        ) {
            //Text(text = stringResource(R.string.but_aceptar))
            Icon(imageVector = Icons.Default.Add, contentDescription = null)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AlumnosMtoPreview() {
    Test421Theme {
        AlumnosMto()
    }
}
