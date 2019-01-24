package com.am;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.am.dialer.R;

import java.util.ArrayList;
import java.util.List;

public class TransactionHistoryAdapter extends RecyclerView.Adapter<TransactionHistoryAdapter.TransactionViewHolder> {

    List<UssdItem> ussdItems = new ArrayList<>();

    class TransactionViewHolder extends RecyclerView.ViewHolder {
        TextView ussd_status, ussd_timestamp, ussd_units;


        public TransactionViewHolder(@NonNull View itemView) {
            super(itemView);
            ussd_status = itemView.findViewById(R.id.transaction_status);
            ussd_timestamp = itemView.findViewById(R.id.ussd_timestamp);
            ussd_units = itemView.findViewById(R.id.ussd_units);
        }
    }

    @NonNull
    @Override
    public TransactionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ussd_item_layout, parent, false);
        return new TransactionViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionViewHolder holder, int position) {
        UssdItem ussdItem = ussdItems.get(position);
        holder.ussd_units.setText(ussdItem.getCredits());
        holder.ussd_timestamp.setText(RecentCallAdapter.getDate(ussdItem.getTimeofTransaction(), "dd/mm/yyyy hh:mm:ss aa"));
        holder.ussd_status.setText(ussdItem.getStatusofthistransaction());
        if (ussdItem.getCredits() != null) {
            switch (ussdItem.getCredits()) {
                case "CREDIT":
                    holder.ussd_status.setTextColor(Color.GREEN);
                    break;
                case "DEBIT":
                    holder.ussd_status.setTextColor(Color.RED);
                    break;
                default:
                    holder.ussd_status.setTextColor(Color.BLACK);

            }
        }
    }

    public void setRecentUssds(List<UssdItem> recentUssds) {
        this.ussdItems = recentUssds;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return ussdItems.size();
    }
}
