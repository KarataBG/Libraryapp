package com.example.libraryapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class HistoryActivity extends AppCompatActivity {
    EditText editText;
    TableLayout tableLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history_books);

        tableLayout = findViewById(R.id.displayBooksTable);

        displayBooks();
//        editText = findViewById(R.id.editText_return);
        Button button = findViewById(R.id.button_history);
        button.setOnClickListener(b -> {
//            if (!TextUtils.isDigitsOnly(editText.getText().toString())) {
//                Toast.makeText(this, "Въведи число", Toast.LENGTH_SHORT).show();
//            } else if (PreferenceManager.getUserTakenBooks().contains(Integer.valueOf(editText.getText().toString()))) {
////                try {
////                    returnBook(Integer.parseInt(editText.getText().toString()));
////                    editText.setText("");
////                } catch (IOException e) {
////                    throw new RuntimeException(e);
////                }
//            } else {
//                Toast.makeText(this, "Грешен ID на книгата", Toast.LENGTH_SHORT).show();
//            }
        });
        Button buttonBack = findViewById(R.id.button_history_back);
        buttonBack.setOnClickListener(b -> {
            Intent intent = new Intent(HistoryActivity.this, StartMenu.class);
            startActivity(intent);
        });


    }

    private boolean displayBooks() {
        try {
            // id, name, author, userid, istaken, datataken
//            BufferedReader reader = new BufferedReader(new InputStreamReader(getResources().openRawResource(R.raw.books)));
            tableLayout.removeAllViews();

            BufferedReader reader = new BufferedReader(new InputStreamReader(openFileInput("user" + PreferenceManager.getID(this) + ".txt")));
            String line;
            while ((line = reader.readLine()) != null) {
                TableRow tableRow = new TableRow(this);

                TextView tv = new TextView(this);
                tv.setPadding(10, 10, 10, 10);
                line = InsertNewLines.insertNewLines(line, 64);
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
