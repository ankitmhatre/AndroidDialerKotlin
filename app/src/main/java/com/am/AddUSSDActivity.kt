package com.am

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.am.dialer.R
import kotlinx.android.synthetic.main.add_ussd_layout.*

class AddUSSDActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_ussd_layout)
        add_ussd.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.add_ussd -> {

            }
        }
    }

    fun validateUSSD(str: String) {
       //TODO(Implement the validation of a USSD number)
        //function validateUSSD(str){
        //  var regex = /^\*[0-9\*#]*[0-9]+[0-9\*#]*#$/;
        //  var valid= regex.test(str);
        //  console.log(str, valid)
        //  return valid;
        //}
    }
}