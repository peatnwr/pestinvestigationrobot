package com.example.pestinvesapp.interfaces

import com.example.pestinvesapp.dataclass.Coord
import com.example.pestinvesapp.dataclass.Mission
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface PestInvesAPI {
    @GET("/allmission/")
    fun receiveAllDate(): Call<List<Mission>>

    @POST("/addmission/")
    @FormUrlEncoded
    fun addMission(
        @Field("missionName") missionName: String,
        @Field("date") date: String
    ): Call<Mission>

    @GET("/missioninfo/{idMission}")
    fun getMissionInfo(
        @Path("idMission") idMission: String
    ): Call<List<Mission>>

    @POST("/addcoord/")
    @FormUrlEncoded
    fun addCoordToMission(
        @Field("idMission") idMissionAtCoordTable: String,
        @Field("lat") lat: Double,
        @Field("longi") longi: Double
    ): Call<Coord>

    @GET("/allcoord/{idMission}")
    fun receiveAllCoord(
        @Path("idMission") idMission: String
    ): Call<List<Coord>>

    @POST("updatecoord")
    @FormUrlEncoded
    fun updateCoord(
        @Field("coordId") idCoord: Int,
        @Field("lat") lat: Double,
        @Field("longi") longi: Double
    ): Call<Coord>
}