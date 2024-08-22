package com.idat.proyectoevc4.modelo

import com.google.gson.annotations.SerializedName

data class DuenoResponse(

    @SerializedName("listaDueno") var listaDueno:ArrayList<Dueno>

)
