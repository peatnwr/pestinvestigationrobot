package com.example.pestinvesapp.interfaces

import com.example.pestinvesapp.dataclass.Mission
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface PestInvesAPI {
    @GET("/allmission/")
    fun receiveAllDate(): Call<List<Mission>>

    @POST("/addmission/")
    @FormUrlEncoded
    fun addMission(
        @Field("missionName") missionName: String,
        @Field("date") date: String
    ): Call<Mission>
}