package com.dam.test421.data

enum class Titulo { SMR, DAM }

data class Alumno(
    var dni: String = "",               // PK
    var nombre: String = "",
    var titulo: Titulo = Titulo.DAM
)

object AlumnoLogica {

    private fun existe(alumno: Alumno, alumnos: List<Alumno>): Boolean {

        return alumnos.find { it.dni == alumno.dni } != null
    }

    fun datosObligatorios(alumno: Alumno): Boolean {
        return (alumno.dni == "" || alumno.nombre == "")
    }

    fun alta(alumno: Alumno, alumnos: MutableList<Alumno>): Boolean {
        if (datosObligatorios(alumno)) return false
        if (existe(alumno, alumnos)) return false
        return alumnos.add(alumno)
    }

    fun baja(alumno: Alumno, alumnos: MutableList<Alumno>): Boolean {
        if (!existe(alumno, alumnos)) return false
        return alumnos.remove(alumnos.find { it.dni == alumno.dni })
    }
}
