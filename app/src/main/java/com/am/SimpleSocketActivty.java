package com.am;

import android.os.Bundle;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.am.dialer.R;

import java.io.IOException;
import java.net.*;
import java.util.Enumeration;
import java.util.List;

public class SimpleSocketActivty extends AppCompatActivity {
    ServerSocket ss = null;
    Socket s = null;
    boolean end = false;
    String gloabalres = "";
    RecyclerView call_list_rv;
    private RecentCallViewModel recentCallViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.simple_for_socket_layout);
        gloabalres = "HI Ankit here";
        call_list_rv = (RecyclerView) findViewById(R.id.call_list_rv);
        call_list_rv.setLayoutManager(new LinearLayoutManager(this));
        call_list_rv.setHasFixedSize(true);
        final RecentCallAdapter recentCallAdapter = new RecentCallAdapter();
        call_list_rv.setAdapter(recentCallAdapter);

        recentCallViewModel = ViewModelProviders.of(this).get(RecentCallViewModel.class);
        recentCallViewModel.getListLiveData().observe(this, new Observer<List<RecentCall>>() {
            @Override
            public void onChanged(List<RecentCall> recentCalls) {
                //update the list
                recentCallAdapter.setRecentCalls(recentCalls);
           //     Toast.makeText(SimpleSocketActivty.this, "changed", Toast.LENGTH_SHORT).show();
            }
        });
     /*   new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    Log.d("ipadddr", getLocalAddress().getHostAddress().toString());

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();*/


    }

    @Override
    protected void onStart() {
        super.onStart();

/*
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
        t.start();*/

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
