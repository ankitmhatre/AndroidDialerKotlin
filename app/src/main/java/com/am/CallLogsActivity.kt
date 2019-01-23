package com.am

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.am.dialer.R


class CallLogsActivity : AppCompatActivity() {

    lateinit var callnUssdViewModel: CallnUssdViewModel
    lateinit var call_list_rv: RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.call_logs)
        call_list_rv = findViewById<View>(R.id.call_list_rv) as RecyclerView

//we can even delete item
        //all the provisions are already present


        call_list_rv.layoutManager = LinearLayoutManager(this)
        val recentCallAdapter = RecentCallAdapter()
        call_list_rv.adapter = recentCallAdapter

        callnUssdViewModel = ViewModelProviders.of(this).get(CallnUssdViewModel::class.java)
        callnUssdViewModel.getListLiveData().observe(this,
            Observer { recentCalls ->
                //update the list
                recentCallAdapter.setRecentCalls(recentCalls)
                //     Toast.makeText(SimpleSocketActivty.this, "changed", Toast.LENGTH_SHORT).show();
            })

    }
}
