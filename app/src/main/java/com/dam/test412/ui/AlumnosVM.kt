package com.dam.test412.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.dam.test412.data.Alumno
import com.dam.test412.data.AlumnoLogica
import com.dam.test412.data.DataSource
import com.dam.test412.data.Titulo

class AlumnosVM : ViewModel() {

    var uiState by mutableStateOf(AlumnosState())
        private set

    fun setDni(dni: String) {
        uiState = uiState.copy(
            dni = dni,
            datosObligatorios = AlumnoLogica.datosObligatorios(
                Alumno(dni, uiState.nombre)
            )
        )
    }

    fun setNombre(nombre: String) {
        uiState = uiState.copy(
            nombre = nombre,
            datosObligatorios = AlumnoLogica.datosObligatorios(
                Alumno(uiState.dni, nombre)
            )
        )
    }

    fun setTitulo(titulo: Titulo) {
        uiState = uiState.copy(
            titulo = titulo
        )
    }

    fun setAlumnoSelected(pos: Int) {
        uiState = uiState.copy(
            alumnoSelected = pos
        )
    }

    fun resetDatos() {
        uiState = uiState.copy(
            dni = "",
            nombre = "",
            titulo = Titulo.SMR,
            datosObligatorios = false,
            alumnoSelected = -1
        )
    }

    fun alta(): Boolean {
        val alumno = Alumno(uiState.dni, uiState.nombre, uiState.titulo)
        return AlumnoLogica.alta(alumno, DataSource.alumnos)
    }

    fun baja(): Boolean {
        val alumno = Alumno(uiState.dni)
        return AlumnoLogica.baja(alumno, DataSource.alumnos)
    }

}
