package com.idat.proyectoevc4.modelo

data class Dueno(
    var id_dueno: Int? = null,
    var nombre : String,
    var apellidos : String,
    var dni : Int,
    var direccion : String,
    var correo : String,
    var telefono : Int,
    var contrasena : String

)
