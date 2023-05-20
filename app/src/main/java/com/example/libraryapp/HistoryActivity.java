package com.example.libraryapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class HistoryActivity extends AppCompatActivity {
    TableLayout tableLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history_books);

        tableLayout = findViewById(R.id.displayBooksTable);

        displayBooks();
        Button buttonBack = findViewById(R.id.button_history_back);
        buttonBack.setOnClickListener(b -> {
            Intent intent = new Intent(HistoryActivity.this, StartMenu.class);
            startActivity(intent);
        });
    }
    private boolean displayBooks() {
        //Отваря файла, зарежда го в паметта и създава таблицата, коята се показва с историята на заемането
        try {
            tableLayout.removeAllViews();

            BufferedReader reader = new BufferedReader(new InputStreamReader(openFileInput("user" + PreferenceManager.getID(this) + ".txt")));
            String line;
            while ((line = reader.readLine()) != null) {
                TableRow tableRow = new TableRow(this);

                TextView tv = new TextView(this);
                tv.setPadding(10, 10, 10, 10);
                line = InsertNewLines.insertNewLines(line, PreferenceManager.getLineLength(this));
                tv.setText(line);

                tableRow.addView(tv);

                tableLayout.addView(tableRow);

            }
            reader.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }
}
