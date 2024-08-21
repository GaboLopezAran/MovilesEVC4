package com.idat.proyectoevc4.modelo

import com.google.gson.annotations.SerializedName

data class CitaResponse(

    @SerializedName("listaCita") var listaCita:ArrayList<Cita>

)
