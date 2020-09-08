package com.shadiDemo.shadi.Interfaces

import com.shadiDemo.shadi.model.MatchResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiInterface {
    @GET("api/?")
    fun getProjectList(@Query("results")resultsSize: Int): Call<MatchResponse>


}