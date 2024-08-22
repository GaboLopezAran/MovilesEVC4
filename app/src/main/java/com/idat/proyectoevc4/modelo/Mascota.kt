package com.idat.proyectoevc4.modelo

data class Mascota(

    var id_mascota: Int,

    var id_dueno: Int,
    var nombre: String,
    var raza: String,
    var sexo :String,
    var fecha_nacimiento : String,
    var peso : String ,
    var especie :String


)
