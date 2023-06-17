package com.example.unpas.k9_mobile_perkuliahan.networks

import com.example.unpas.k9_mobile_perkuliahan.model.Mahasiswa
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface MahasiswaApi {
    @GET("api/mahasiswa")
    suspend fun all(): ApiResponse<MahasiswaGetResponse>
    @GET("api/mahasiswa/{id}")
    suspend fun find(@Path("id") id: String):
            ApiResponse<MahasiswaSingleGetResponse>
    @POST("api/mahasiswa")
    @Headers("Content-Type: application/json")
    suspend fun insert(@Body item: Mahasiswa):
            ApiResponse<MahasiswaSingleGetResponse>
    @PUT("api/mahasiswa/{id}")
    @Headers("Content-Type: application/json")
    suspend fun update(@Path("id") pathId: String,
                       @Body item: Mahasiswa):
            ApiResponse<MahasiswaSingleGetResponse>
    @DELETE("api/mahasiswa/{id}")
    suspend fun delete(@Path("id") id: String):
            ApiResponse<MahasiswaSingleGetResponse>
}