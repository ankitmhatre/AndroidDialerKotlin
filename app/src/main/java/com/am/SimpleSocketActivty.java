package com.am;

import android.os.Bundle;
import android.util.Log;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.am.dialer.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;
import java.util.Enumeration;

public class SimpleSocketActivty extends AppCompatActivity {
    ServerSocket ss = null;
    Socket s = null;
    boolean end = false;
    String gloabalres = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.simple_for_socket_layout);
        gloabalres = "HI Ankit here";
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    Log.d("ipadddr", getLocalAddress().getHostAddress().toString());

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    @Override
    protected void onStart() {
        super.onStart();


        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    ss = new ServerSocket(Integer.parseInt(PrefUtils.getString(SimpleSocketActivty.this, "port", "80")));

                    Log.d("serversock", "created");


                    while (!end) {
                        //Server is waiting for client here, if needed
                        s = ss.accept();

                        BufferedReader input = new BufferedReader(new InputStreamReader(s.getInputStream()));
                        PrintWriter output = new PrintWriter(s.getOutputStream());

                        receiveMessage(input.readLine());
                        output.write(sendMessage());
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

    public void receiveMessage(String res) {

    }

    public String sendMessage() {
        return gloabalres;
    }

    private InetAddress getLocalAddress() throws IOException {

        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        //return inetAddress.getHostAddress().toString();
                        return inetAddress;
                    }
                }
            }
        } catch (SocketException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
