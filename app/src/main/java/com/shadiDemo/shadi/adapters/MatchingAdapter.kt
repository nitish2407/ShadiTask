package com.shadiDemo.shadi.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.shadiDemo.shadi.R
import com.shadiDemo.shadi.databinding.MatchCardItemBinding
import com.shadiDemo.shadi.local.DBHelper
import com.shadiDemo.shadi.model.Result
import java.util.*


class MatchingAdapter(private var context: Context) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val matchingList = ArrayList<Result>()

    fun setAppList(categoryModel: ArrayList<Result>) {
        matchingList.addAll(categoryModel)
        //notifyItemRangeInserted(0, categoryModel.size)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        Log.d("LIST_SIZE", "" + matchingList.size)
        return matchingList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val appInfo = matchingList[position]
        (holder as MatchingAdapter.RecyclerHolderCatIcon).bind(appInfo)
        val btnAccept = holder.applicationBinding.root.findViewById<TextView>(R.id.btnAccept)
        val btnDecline = holder.applicationBinding.root.findViewById<TextView>(R.id.btnDecline)
        btnAccept.setOnClickListener(
            View.OnClickListener { onClick(position, btnAccept, appInfo) }
        )
        btnDecline.setOnClickListener(
            View.OnClickListener { onClick(position, btnDecline, appInfo) }
        )
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val applicationBinding = MatchCardItemBinding.inflate(layoutInflater, parent, false)
        return RecyclerHolderCatIcon(applicationBinding)
    }

    private fun onClick(
        position: Int,
        view: View,
        result: Result
    ) {
        if (view.id == R.id.btnAccept)
            result.status = "Member Accepted"
        else
            result.status = "Member Declined"
        DBHelper.getInstance(context).updateMatch(result.status, result.login.username)
        notifyItemChanged(position)
    }


    inner class RecyclerHolderCatIcon(public var applicationBinding: MatchCardItemBinding) :
        RecyclerView.ViewHolder(applicationBinding.root) {

        fun bind(appInfo: Result) {
            applicationBinding.matchItem = appInfo

        }


    }
}