package com.am;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.am.dialer.R;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class RecentCallAdapter extends RecyclerView.Adapter<RecentCallAdapter.RecentCallHolder> {


    List<RecentCall> recentCalls = new ArrayList<>();

    class RecentCallHolder extends RecyclerView.ViewHolder {

        ImageView ic;
        TextView name, duration, dialed, credits_textt;



        public RecentCallHolder(@NonNull View itemView) {
            super(itemView);
            duration = itemView.findViewById(R.id.duration_tv);
            name = itemView.findViewById(R.id.mainnumber_tv);

            credits_textt = itemView.findViewById(R.id.credits_textt);
            ic = itemView.findViewById(R.id.imageView_ic);
            dialed = itemView.findViewById(R.id.dialed_on_tv);

        }
    }

    @NonNull
    @Override
    public RecentCallHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recenet_call_item, parent, false);
        return new RecentCallHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecentCallHolder holder, int position) {
        RecentCall call = recentCalls.get(position);
        if (call != null)
            try {
                if (call.getIncoming()) {
                    if (call.getDialed_on() != null) {
                        holder.dialed.setText("Ringed on " + getDate(Long.parseLong(call.getDialed_on()), "hh:mm:ss aa"));

                    }
                    Picasso.get().load(R.drawable.ic_action_phone_incoming).into(holder.ic);
                } else {
                    if (call.getDialed_on() != null) {
                        holder.dialed.setText("Dialed on " + getDate(Long.parseLong(call.getDialed_on()), "dd mmm, yyyy hh:mm:ss aa"));

                    }
                    Picasso.get().load(R.drawable.ic_action_phone_outgoing).into(holder.ic);
                }
            } catch (Exception e) {
                if (call.getDialed_on() != null) {
                    holder.dialed.setText("Missed on " + getDate(Long.parseLong(call.getDialed_on()), "hh:mm:ss aa"));
                    holder.dialed.setTextColor(Color.RED);
                }
                e.printStackTrace();

                Picasso.get().load(R.drawable.ic_action_phone_missed).into(holder.ic);

            }

        holder.name.setText(call.getNumber() != null ? call.getNumber() : "unknown");
        if (call.getTime_started() != null) {

            if (call.getTime_ended() != null) {
                long diff = Long.parseLong(call.getTime_ended()) - Long.parseLong(call.getTime_started());


                holder.credits_textt.setText(getCreditsCalculation(diff));
                holder.duration.setText(getMillisToStuff(diff));
            }

        } 


    }

    private String getCreditsCalculation(long diff) {
        long check;
        if (false) {
            check = TimeUnit.MILLISECONDS.toMinutes(diff);
        } else {
            check = TimeUnit.MILLISECONDS.toSeconds(diff);
        }
        return check + "";
    }

    public String getMillisToStuff(long millis) {
        long hours = (millis / 1000) / 3600;
        long minutes = (millis / 1000) / 60;
        long seconds = (millis / 1000) % 60;

        String a = String.format("%dh:%dm:%ds", hours, minutes, seconds);

        // Math.floor((double) minutes);
        return a;
    }

    public void removeItem(int position) {
        recentCalls.remove(position);
        // notify the item removed by position
        // to perform recycler view delete animations
        // NOTE: don't call notifyDataSetChanged()
        notifyItemRemoved(position);
    }

    public void setRecentCalls(List<RecentCall> recentCalls) {
        this.recentCalls = recentCalls;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return recentCalls.size();
    }

    public static String getDate(long milliSeconds, String dateFormat) {
        // Create a DateFormatter object for displaying date in specified format.
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);

        // Create a calendar object that will convert the date and time value in milliseconds to date.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());
    }

}
