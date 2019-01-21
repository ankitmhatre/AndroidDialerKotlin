package com.am;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.am.dialer.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class RecentCallAdapter extends RecyclerView.Adapter<RecentCallAdapter.RecentCallHolder> {


    List<RecentCall> recentCalls = new ArrayList<>();

    class RecentCallHolder extends RecyclerView.ViewHolder {

        ImageView ic;
        TextView name, start, end;


        public RecentCallHolder(@NonNull View itemView) {
            super(itemView);
            start = itemView.findViewById(R.id.starttime_tv);
            name = itemView.findViewById(R.id.mainnumber_tv);
            end = itemView.findViewById(R.id.endtime_tv);
            ic = itemView.findViewById(R.id.imageView_ic);
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
        if (call.getIncoming()) {
            Picasso.get().load(R.drawable.ic_action_phone_incoming).into(holder.ic);
        } else {
            Picasso.get().load(R.drawable.ic_action_phone_outgoing).into(holder.ic);

        }
        holder.name.setText(call.getNumber());

        holder.start.setText(call.getTime_started());
        holder.end.setText(call.getTime_ended());
    }

    public void setRecentCalls(List<RecentCall> recentCalls) {
        this.recentCalls = recentCalls;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return recentCalls.size();
    }
}
