package com.am

import android.app.Application
import android.os.AsyncTask
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.afollestad.materialdialogs.customview.getCustomView
import com.am.dialer.R
import kotlinx.android.synthetic.main.ussd_details_layout.*
import java.util.regex.Pattern

class UssdActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var ussdViewModel: CallnUssdViewModel
    lateinit var ussd_rv: RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ussd_details_layout)
        add_ussd_fab.setOnClickListener(this)
        ussd_rv = findViewById<View>(R.id.ussd_list) as RecyclerView
        textView2.text =  " ${PrefUtils.getInt(this, "total_minutes", 0)}"
        ussd_rv.layoutManager = LinearLayoutManager(this)
        val transactionHistoryAdapter = TransactionHistoryAdapter()
        ussd_rv.adapter = transactionHistoryAdapter

        ussdViewModel = ViewModelProviders.of(this).get(CallnUssdViewModel::class.java)
        ussdViewModel.getUssdList().observe(this,
            Observer { ussdList ->
                //update the list
                transactionHistoryAdapter.setRecentUssds(ussdList)
                //     Toast.makeText(SimpleSocketActivty.this, "changed", Toast.LENGTH_SHORT).show();
            })


    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.add_ussd_fab -> {
                MaterialDialog(this).show {
                    title(R.string.add_ussd)
                    customView(R.layout.add_ussd_layout)
                    noAutoDismiss()
                    positiveButton(R.string.add) { dialog ->
                        val customView = dialog.getCustomView()

                        var ussd_code = customView?.findViewById<EditText>(R.id.ussd_code_box)?.text.toString()
                        var ussd_minutes = customView?.findViewById<EditText>(R.id.ussd_minutes_box)?.text.toString()

                        if (validateUSSD(ussd_code) && TextUtils.isDigitsOnly(ussd_minutes)) {
                            var usItem = UssdItem(
                                ussd_id = null,
                                ussd_no = ussd_code,
                                done = false,
                                credits = ussd_minutes,
                                timeofTransaction = null,
                                statusofthistransaction = "UNKNOWN"
                            )
                            InsertUssdItem(application).execute(usItem)
                        } else {
                            Toast.makeText(this@UssdActivity, "Enter a valid USSD", Toast.LENGTH_LONG).show()
                        }

                    }
                    negativeButton(R.string.cancel) { dismiss() }
                }
            }
        }
    }

    private fun validateUSSD(str: String): Boolean {
        val sPattern = Pattern.compile("^\\*[0-9\\*#]*[0-9]+[0-9\\*#]*#$")

        var value = sPattern.matcher(str).matches()

        return value
    }

    class InsertUssdItem : AsyncTask<UssdItem, Void, String> {
        var application: Application
        var ussdDao: UssdDao

        constructor(application: Application) {
            this.application = application
            ussdDao = CallnUssdDatabase.getInstance(application).ussdDao()

        }


        override fun doInBackground(vararg params: UssdItem): String {


            ussdDao.insert(params[0])

            Log.d("ussdItem", params[0].toString())

            return ""
        }
    }
}