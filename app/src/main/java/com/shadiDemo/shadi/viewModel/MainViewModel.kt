package com.shadiDemo.shadi.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.shadiDemo.shadi.Interfaces.ApiInterface
import com.shadiDemo.shadi.client.APIClient
import com.shadiDemo.shadi.model.MatchResponse
import com.shadiDemo.shadi.model.Result
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(application: Application) : AndroidViewModel(application) {
    var dataList: ArrayList<Result> = ArrayList()
    private val mutablePostList: MutableLiveData<ArrayList<Result>> = MutableLiveData()

        fun getProjectList(resultsSize: Int): LiveData<ArrayList<Result>>? {

            //TODO:  DO this in repository

        var apiServices = APIClient.client.create(ApiInterface::class.java)
        val call = apiServices.getProjectList(resultsSize)

        call.enqueue(object : Callback<MatchResponse> {
            override fun onResponse(call: Call<MatchResponse>, response: Response<MatchResponse>) {

                val jsonResponse = response.body()
                dataList = jsonResponse?.results as ArrayList<Result>

                mutablePostList.postValue(dataList)
            }

            override fun onFailure(call: Call<MatchResponse>?, t: Throwable?) {
                Log.d("ERROR : ", " ")

            }
        })

        return mutablePostList


    }

}