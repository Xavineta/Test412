package com.dam.test421.ui

import com.dam.test421.data.Titulo


data class AlumnosState(
    val dni: String = "",
    val nombre: String = "",
    val titulo: Titulo = Titulo.SMR,
    val datosObligatorios: Boolean = false,
    val alumnoSelected: Int = -1
)

