package com.example.expensetracker;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    MaterialCardView cardAddEntry, cardMonthly;
    RecyclerView recyclerView;
    ArrayList<TransactionModel> list;
    TransactionAdapter adapter;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Cards (buttons ki jagah)
        cardAddEntry = findViewById(R.id.cardAddEntry);
        cardMonthly  = findViewById(R.id.cardMonthly);

        recyclerView = findViewById(R.id.recyclerView);

        dbHelper = new DatabaseHelper(this);
        list = new ArrayList<>();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TransactionAdapter(this, list);
        recyclerView.setAdapter(adapter);

        loadRecentEntries();

        // Add Entry click
        cardAddEntry.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, AddEntryActivity.class)));

        // Monthly Summary click
        cardMonthly.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, MonthlySummaryActivity.class)));
    }

    private void loadRecentEntries() {
        list.clear();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor c = db.rawQuery(
                "SELECT * FROM transactions ORDER BY id DESC LIMIT 10",
                null
        );

        while (c.moveToNext()) {
            list.add(new TransactionModel(
                    c.getInt(0),      // id
                    c.getString(1),   // type
                    c.getInt(2),      // amount
                    c.getString(3),   // category
                    c.getString(4)    // date
            ));
        }
        c.close();
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadRecentEntries(); // refresh after add/delete
    }
}