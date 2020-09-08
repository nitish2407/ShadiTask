package com.shadiDemo.shadi.Interfaces

import android.content.Context
import android.view.View
import com.shadiDemo.shadi.R
import com.shadiDemo.shadi.local.DBHelper
import com.shadiDemo.shadi.model.Result

class MatchingClickListener(private var context: Context) {
    fun onMatchClick(view: View, result: Result) {
        if (view.id == R.id.btnAccept)
            result.status = "Member Accepted"
        else
            result.status = "Member Declined"
        DBHelper.getInstance(context).updateMatch(result.status, result.login.username)
        //matchingAdapter.notifyDataSetChanged()
    }
}