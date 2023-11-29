package com.dam.test421.ui

import android.app.Activity
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.ArrowBack
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.dam.test421.R
import com.dam.test421.ui.theme.Test421Theme


@OptIn(ExperimentalMaterial3Api::class)

enum class AppScreens(@StringRes val title: Int) {
    Home(title = R.string.txt_appbar),
    Bus(title = R.string.label_bus),
    Mto(title = R.string.label_mto),
}

@Composable
fun MainApp(
    modifier: Modifier = Modifier
) {
    val navController: NavHostController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = AppScreens.valueOf(
        backStackEntry?.destination?.route ?: AppScreens.Home.name
    )
    var optionSelected by rememberSaveable { mutableIntStateOf(0) }
    val snackbarHostState = remember { SnackbarHostState() }
    val alumnosVM: AlumnosVM = viewModel()

    Scaffold(
        modifier = modifier,
        topBar = {
            MainTopAppBar(
                currentScreen = currentScreen,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() }
            )
        },
        bottomBar = {
            MainBottomAppBar(
                selectedItem = optionSelected,
                onSelectedItemChange = { optionSelected = it }
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }) {
        NavHost(
            navController = navController,
            startDestination = AppScreens.Home.name,
            modifier = Modifier.padding(it)
        ) {
            composable(route = AppScreens.Home.name) {

                HomeScreen(
                    onNextButtonClick = {
                        navController.navigate(AppScreens.Home.name)
                    },
                    alumnosVM = alumnosVM,
                    snackbarHostState = snackbarHostState
                )

            }
            composable(route = AppScreens.Bus.name) {
                AlumnosBus(
                    onNextButtonClick = {
                        navController.navigate(AppScreens.Bus.name)
                    },
                    alumnosVM = alumnosVM, snackbarHostState = snackbarHostState
                )
            }
            composable(route = AppScreens.Mto.name) {
                AlumnosMto(alumnosVM = alumnosVM, snackbarHostState = snackbarHostState)
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainTopAppBar(
    currentScreen: AppScreens,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    var showMenu by rememberSaveable {
        mutableStateOf(false)
    }
    var showDlgConfirmacion by rememberSaveable {
        mutableStateOf(false)
    }
    val activity = (LocalContext.current as? Activity)
    CenterAlignedTopAppBar(
        title = { Text(text = stringResource(currentScreen.title)) },
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = null)
                }
            }
        },
        actions = {
            IconButton(onClick = {
                showMenu = !showMenu
            }) {
                Icon(imageVector = Icons.Filled.MoreVert, contentDescription = null)
            }
            DropdownMenu(expanded = showMenu, onDismissRequest = {
                showMenu = false
            }) {
                DropdownMenuItem(
                    text = { Text(text = stringResource(R.string.menu_salir)) },
                    onClick = {
                        showDlgConfirmacion = true
                    })
                if (showDlgConfirmacion) {
                    showMenu = false
                    DlgConfirmacion(
                        mensaje = R.string.txt_salir,
                        onCancelarClick = { showDlgConfirmacion = false },
                        onAceptarClick = {
                            showDlgConfirmacion = false
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
    Test421Theme {
        MainApp()
    }
}
