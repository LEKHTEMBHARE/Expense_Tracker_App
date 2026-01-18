package com.example.expensetracker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.ViewHolder> {

    Context context;
    List<TransactionModel> list;
    DatabaseHelper db;

    public TransactionAdapter(Context context, List<TransactionModel> list) {
        this.context = context;
        this.list = list;
        this.db = new DatabaseHelper(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context)
                .inflate(R.layout.item_transaction, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder h, int position) {
        TransactionModel t = list.get(position);

        h.tvCategory.setText(t.getCategory());
        h.tvDate.setText(t.getDate());

        if (t.getType().equalsIgnoreCase("income")) {
            h.tvAmount.setText("+ ₹" + t.getAmount());
            h.tvAmount.setTextColor(context.getColor(R.color.green_income));
        } else {
            h.tvAmount.setText("- ₹" + t.getAmount());
            h.tvAmount.setTextColor(context.getColor(R.color.red_expense));
        }

        h.btnDelete.setOnClickListener(v -> {
            db.deleteEntry(t.getId());
            list.remove(position);
            notifyItemRemoved(position);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    // VIEW HOLDER
    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvCategory, tvAmount, tvDate, btnDelete;

        ViewHolder(View v) {
            super(v);
            tvCategory = v.findViewById(R.id.tvCategory);
            tvAmount = v.findViewById(R.id.tvAmount);
            tvDate = v.findViewById(R.id.tvDate);
            btnDelete = v.findViewById(R.id.btnDelete);
        }
    }
}