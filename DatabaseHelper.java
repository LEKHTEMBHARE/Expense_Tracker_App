package com.example.expensetracker;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "expense_db";
    private static final int DB_VERSION = 1;

    public static final String TABLE_NAME = "transactions";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "type TEXT," +           // income / expense
                "amount INTEGER," +
                "category TEXT," +
                "date TEXT," +
                "month TEXT)";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    // =========================
    // ✅ GET ALL TRANSACTIONS
    // =========================
    public List<TransactionModel> getAllTransactions() {
        List<TransactionModel> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor c = db.rawQuery(
                "SELECT id, type, amount, category, date FROM " + TABLE_NAME +
                        " ORDER BY id DESC", null
        );

        if (c.moveToFirst()) {
            do {
                list.add(new TransactionModel(
                        c.getInt(0),
                        c.getString(1),
                        c.getInt(2),
                        c.getString(3),
                        c.getString(4)
                ));
            } while (c.moveToNext());
        }

        c.close();
        db.close();
        return list;
    }

    // =========================
    // ✅ DELETE TRANSACTION
    // =========================
    public void deleteEntry(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, "id=?", new String[]{String.valueOf(id)});
        db.close();
    }
}