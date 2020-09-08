package com.shadiDemo.shadi.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.shadiDemo.shadi.R
import com.shadiDemo.shadi.adapters.MatchingAdapter
import com.shadiDemo.shadi.databinding.ActivityMainBinding
import com.shadiDemo.shadi.local.DBHelper
import com.shadiDemo.shadi.model.Result
import com.shadiDemo.shadi.viewModel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.view.*

class MainActivity : AppCompatActivity() {

    private lateinit var appStoreHomeViewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding
    private var dataList: ArrayList<Result> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        appStoreHomeViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        binding.mainModelHome = appStoreHomeViewModel

        dataList = DBHelper.getInstance(this).allMatches
        setRecyclerView(dataList)   //send empty list initially

        subscribeDataCallBack()
    }


    private fun subscribeDataCallBack() {

        appStoreHomeViewModel.getProjectList(10)?.observe(this, Observer {

            if (it != null) {
                val dbHelper = DBHelper.getInstance(this)
                dbHelper.insertMatches(it)
                matchingAdapter.setAppList(it)
            }

        })

    }

    private lateinit var matchingAdapter: MatchingAdapter

    private fun setRecyclerView(dataList: ArrayList<Result>) {
        matchingAdapter = MatchingAdapter(this)
        val categoryLinearLayoutManager = LinearLayoutManager(this)
        categoryLinearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.root.rvMatches.layoutManager = categoryLinearLayoutManager
        matchingAdapter.setAppList(dataList)
        binding.root.rvMatches.adapter = matchingAdapter


    }
}