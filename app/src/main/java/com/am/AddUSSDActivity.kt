package com.am

import android.app.Application
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.am.dialer.R
import kotlinx.android.synthetic.main.add_ussd_layout.*
import java.util.regex.Pattern

class AddUSSDActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_ussd_layout)
        add_ussd.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.add_ussd -> {
                if (validateUSSD(uussd_textbox.text.toString())) {
                    var usItem = UssdItem(
                        ussd_id = null,
                        ussd_no = uussd_textbox.text.toString(),
                        done = false,
                        minutes = null,
                        timeAtCreation = System.currentTimeMillis().toString()
                    )
                    InsertUssdItem(application).execute(usItem)
                } else {
                    Toast.makeText(this, "Enter a valid USSD", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun validateUSSD(str: String): Boolean {
        val sPattern = Pattern.compile("^\\*[0-9\\*#]*[0-9]+[0-9\\*#]*#$")

        var value = sPattern.matcher(str).matches()
        Log.d("pattren", value.toString() + str)
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