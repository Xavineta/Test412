package com.dam.test412.ui

import android.app.Activity
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.dam.test412.R
import com.dam.test412.data.DataSource
import com.dam.test412.ui.theme.Test412Theme

@Composable
fun MainApp(
    modifier: Modifier = Modifier
) {

    var optionSelected by rememberSaveable { mutableIntStateOf(0) }
    val snackbarHostState=remember{SnackbarHostState()}

    Scaffold(
        modifier = modifier,
        topBar = {
            MainTopAppBar()
        },
        bottomBar = {
            MainBottomAppBar(
                selectedItem = optionSelected,
                onSelectedItemChange = { optionSelected = it }
            )
        },
        snackbarHost = {SnackbarHost(hostState = snackbarHostState)}
    ) {
        Surface(
            modifier = Modifier.padding(it)
        ) {
            when (optionSelected) {
                1 -> AlumnosBus(snackbarHostState=snackbarHostState)
                2 -> AlumnosMto(snackbarHostState=snackbarHostState)
                else -> HomeScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainTopAppBar(modifier: Modifier = Modifier) {


    var showMenu by rememberSaveable {
        mutableStateOf(false)
    }
    var showDlgConfirmacion by rememberSaveable {
        mutableStateOf(false)
    }
    val activity= (LocalContext.current as? Activity)
    CenterAlignedTopAppBar(
        title = { Text(text = stringResource(R.string.txt_appbar)) },
        modifier = modifier,
        actions= {
            IconButton(onClick = {
                showMenu=!showMenu
            }) {
                Icon(imageVector = Icons.Filled.MoreVert, contentDescription =null )
            }
            DropdownMenu(expanded = showMenu, onDismissRequest = {showMenu=false
            }) {
                DropdownMenuItem(
                    text = { Text(text = stringResource(R.string.menu_salir)) },
                    onClick = {
                        showDlgConfirmacion=true
                    })
                if (showDlgConfirmacion) {
                    showMenu=false
                    DlgConfirmacion(
                        mensaje = R.string.txt_salir,
                        onCancelarClick = { showDlgConfirmacion=false },
                        onAceptarClick = { showDlgConfirmacion=false
                         activity?.finish()
                        })

                }
            }
        }
    )
}

@Composable
fun MainBottomAppBar(
    selectedItem: Int,
    onSelectedItemChange: (Int) -> Unit,
    modifier: Modifier = Modifier
) {

    NavigationBar(
        modifier = modifier
    ) {
        NavigationBarItem(
            selected = selectedItem == 0,
            onClick = { onSelectedItemChange(0) },
            icon = { Icon(imageVector = Icons.Filled.Home, contentDescription = null) },
            label = { Text(text = stringResource(R.string.label_home)) })
        NavigationBarItem(
            selected = selectedItem == 1,
            onClick = { onSelectedItemChange(1) },
            icon = { Icon(imageVector = Icons.Filled.List, contentDescription = null) },
            label = { Text(text = stringResource(R.string.label_bus)) })
        NavigationBarItem(
            selected = selectedItem == 2,
            onClick = { onSelectedItemChange(2) },
            icon = { Icon(imageVector = Icons.Filled.AccountBox, contentDescription = null) },
            label = { Text(text = stringResource(R.string.label_mto)) })
    }
}


@Composable
private fun DlgConfirmacion(
    @StringRes mensaje: Int,
    onCancelarClick: () -> Unit,
    onAceptarClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    AlertDialog(
        onDismissRequest = { },
        title = { Text(stringResource(R.string.txt_appbar)) },
        text = { Text(stringResource(mensaje, 0)) },
        modifier = modifier,
        dismissButton = {
            TextButton(
                onClick = onCancelarClick
            ) {
                Text(text = stringResource(R.string.but_cancelar))
            }
        },
        confirmButton = {
            TextButton(
                onClick = onAceptarClick
            ) {
                Text(text = stringResource(R.string.but_aceptar))
            }
        })
}
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MainAppPreview() {
    Test412Theme {
        MainApp()
    }
}
