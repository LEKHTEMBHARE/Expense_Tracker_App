package com.example.expensetracker;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MonthlySummaryActivity extends AppCompatActivity {

    TextView tvIncomeAmount, tvExpenseAmount, tvNetAmount;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monthly_summary);

        // 🔹 UI IDs (card ke andar wale amount TextViews)
        tvIncomeAmount = findViewById(R.id.tvIncomeAmount);
        tvExpenseAmount = findViewById(R.id.tvExpenseAmount);
        tvNetAmount = findViewById(R.id.tvNetAmount);

        calculateMonthlySummary();
    }

    private void calculateMonthlySummary() {

        int totalIncome = 0;
        int totalExpense = 0;

        // 🔹 Current month (yyyy-MM)
        String currentMonth = new SimpleDateFormat(
                "yyyy-MM",
                Locale.getDefault()
        ).format(new Date());

        DatabaseHelper dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT type, amount FROM transactions WHERE month = ?",
                new String[]{ currentMonth }
        );

        while (cursor.moveToNext()) {

            String type = cursor.getString(0);
            int amount = cursor.getInt(1);

            if (type.equalsIgnoreCase("income")) {
                totalIncome += amount;
            } else if (type.equalsIgnoreCase("expense")) {
                totalExpense += amount;
            }
        }

        cursor.close();

        int net = totalIncome - totalExpense;

        // 🔹 UI Update
        tvIncomeAmount.setText("₹" + totalIncome + ".00");
        tvExpenseAmount.setText("₹" + totalExpense + ".00");
        tvNetAmount.setText("₹" + net + ".00");
    }
}