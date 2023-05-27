package com.example.pestinvesapp.dataclass

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Coord(
    @Expose
    @SerializedName("id") val idCoord: Int,

    @Expose
    @SerializedName("lat") val lat: Double,

    @Expose
    @SerializedName("longi") val longi: Double,

    @Expose
    @SerializedName("idMission") val idMissionAtCoordTable: Int) {}