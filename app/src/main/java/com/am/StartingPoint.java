package com.am;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.am.dialer.Dialer;
import com.am.dialer.R;

import java.util.regex.Pattern;

public class StartingPoint extends AppCompatActivity implements View.OnClickListener {

    Button setCredentials;
    EditText username, pass, ipAdd, port;
    ProgressBar pb;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_login);
        setCredentials = (Button) findViewById(R.id.setCredentials);
        username = (EditText) findViewById(R.id.username);
        pass = (EditText) findViewById(R.id.password);
        ipAdd = (EditText) findViewById(R.id.ipaddress);
        port = (EditText) findViewById(R.id.portNo);
        pb = (ProgressBar) findViewById(R.id.credentials_Pb);
        setCredentials.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.setCredentials:
                if (validateAndGo()) {
                    new CheckCredentials().execute(
                            username.getText().toString(),
                            pass.getText().toString(),
                            ipAdd.getText().toString(),
                            port.getText().toString());

                    PrefUtils.setString(this, "username", username.getText().toString());
                    PrefUtils.setString(this, "password", pass.getText().toString());
                    PrefUtils.setString(this, "ipAdd", ipAdd.getText().toString());
                    PrefUtils.setString(this, "port", port.getText().toString());


                    PrefUtils.setBoolean(this, "first_launch", false);
                    PrefUtils.setInt(this, "total_minutes", 0);

                    startActivityForResult(new Intent(this, Dialer.class), 11);
                }

                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 11:
                finish();
                break;
        }
    }

    private boolean validateAndGo() {
        boolean allGood = true;
        if (username.getText().length() > 0) {
            if (!isValidEmail(username.getText().toString())) {
                allGood = false;
                username.setError("Please check the username");
            }
        } else {
            allGood = false;
            username.setError("Please enter the username");
        }
        if (pass.getText().length() < 3) {
            allGood = false;
            pass.setError("Please check the password");
        }
        if (ipAdd.getText().length() < 4) {
            allGood = false;
            ipAdd.setError("Please enter the correct Ip address");
        }
        if (port.getText().length() < 1) {
            allGood = false;
            port.setError("Please enter a port no.");
        }


        Log.d("theValidationvalue", allGood + "");
        return allGood;
    }

    public static boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }

    class CheckCredentials extends AsyncTask<String, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pb.setVisibility(View.VISIBLE);
            setCredentials.setVisibility(View.INVISIBLE);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            pb.setVisibility(View.INVISIBLE);
            setCredentials.setVisibility(View.VISIBLE);

        }


        @Override
        protected Void doInBackground(String... strings) {
//do the expensive and network heavy task here

            return null;
        }
    }
}
