package com.example.pestinvesapp.dataclass

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Status(
    @Expose
    @SerializedName("idStatus") val idStatus: Int,

    @Expose
    @SerializedName("mode") val mode: Int,

    @Expose
    @SerializedName("forWard") val forWard: Int,

    @Expose
    @SerializedName("backWard") val backWard: Int,

    @Expose
    @SerializedName("turnLeft") val turnLeft: Int,

    @Expose
    @SerializedName("turnRight") val turnRight: Int) {}