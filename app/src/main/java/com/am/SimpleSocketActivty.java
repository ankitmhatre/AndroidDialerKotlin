package com.am;

import android.os.Bundle;
import android.util.Log;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.am.dialer.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class SimpleSocketActivty extends AppCompatActivity {
    ServerSocket ss = null;
    boolean end = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.simple_for_socket_layout);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    ss = new ServerSocket(Integer.parseInt(PrefUtils.getString(SimpleSocketActivty.this, "port", "80")));
                    Socket s;
                    Log.d("serversock", "created");

                    while (!end) {
                        //Server is waiting for client here, if needed
                        s = ss.accept();
                        BufferedReader input = new BufferedReader(new InputStreamReader(s.getInputStream()));

                        Log.d("socketAnswer", input.readLine());
                        // PrintWriter output = new PrintWriter(s.getOutputStream());
                        // s.close();
                    }
                    ss.close();


                } catch (IOException e) {
                    e.printStackTrace();
                    Log.d("serversock", "cannot connect");
                }
            }
        });
        t.start();

    }
}
