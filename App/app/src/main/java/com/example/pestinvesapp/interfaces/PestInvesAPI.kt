package com.example.pestinvesapp.interfaces

import com.example.pestinvesapp.dataclass.Coord
import com.example.pestinvesapp.dataclass.Mission
import com.example.pestinvesapp.dataclass.Status
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
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

    @DELETE("/deletecoord/{coordId}")
    fun deleteCoord(
        @Path("coordId") idCoord: Int
    ): Call<Coord>

    @DELETE("/deletemission/{missionId}")
    fun deleteMission(
        @Path("missionId") idMission: Int
    ): Call<Mission>

    @PUT("/automaticmode/")
    fun changeToAutomatic(): Call<Status>

    @PUT("/manualmode/")
    fun changeToManual(): Call<Status>

    @PUT("/enableforward/")
    fun enableForward(): Call<Status>

    @PUT("/enablebackward/")
    fun enableBackward(): Call<Status>

    @PUT("/enableturnleft/")
    fun enableTurnleft(): Call<Status>

    @PUT("/enableturnright/")
    fun enableTurnright(): Call<Status>

    @PUT("/disableforward/")
    fun disableForward(): Call<Status>

    @PUT("/disablebackward/")
    fun disableBackward(): Call<Status>

    @PUT("/disableturnleft/")
    fun disableTurnleft(): Call<Status>

    @PUT("/disableturnright/")
    fun disableTurnright(): Call<Status>

    companion object {
        fun createClient(): PestInvesAPI {
            val pestInvest: PestInvesAPI = Retrofit.Builder()
                .baseUrl("http://192.168.125.187:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(PestInvesAPI::class.java)
            return pestInvest
        }
    }
}