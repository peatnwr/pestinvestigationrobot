package com.example.pestinvesapp.dataclass

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Mission (
    @Expose
    @SerializedName("id") val idMission: Int,

    @Expose
    @SerializedName("missionName") val missionName: String,

    @Expose
    @SerializedName("datetime") val datetime: String) {}