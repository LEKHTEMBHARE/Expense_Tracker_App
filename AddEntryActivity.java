package com.example.expensetracker;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AddEntryActivity extends AppCompatActivity {

    Spinner spinnerType;
    EditText etAmount, etCategory;
    Button btnAddEntry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_entry);

        spinnerType = findViewById(R.id.spinnerType);
        etAmount = findViewById(R.id.etAmount);
        etCategory = findViewById(R.id.etCategory);
        btnAddEntry = findViewById(R.id.btnAddEntry);

        // ✅ CORRECT Spinner Adapter (BLACK TEXT)
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.type_array,
                R.layout.spinner_item_black
        );
        adapter.setDropDownViewResource(R.layout.spinner_item_black);
        spinnerType.setAdapter(adapter);

        btnAddEntry.setOnClickListener(v -> saveEntry());
    }

    private void saveEntry() {

        String type = spinnerType.getSelectedItem().toString();
        String amountStr = etAmount.getText().toString().trim();
        String category = etCategory.getText().toString().trim();

        if (type.equals("Select") || amountStr.isEmpty() || category.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        int amount = Integer.parseInt(amountStr);

        String date = new SimpleDateFormat(
                "yyyy-MM-dd",
                Locale.getDefault()
        ).format(new Date());

        String month = new SimpleDateFormat(
                "yyyy-MM",
                Locale.getDefault()
        ).format(new Date());

        DatabaseHelper dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put("type", type.toLowerCase());
        cv.put("amount", amount);
        cv.put("category", category);
        cv.put("date", date);
        cv.put("month", month);

        db.insert("transactions", null, cv);

        Toast.makeText(this, "Entry Added", Toast.LENGTH_SHORT).show();
        finish();
    }
}