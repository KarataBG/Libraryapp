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
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class ReturnActivity extends AppCompatActivity {
    EditText editText;
    TableLayout tableLayout;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.return_book);

        tableLayout = findViewById(R.id.displayBooksTable);

        displayBooks();
        editText = findViewById(R.id.editText_return);
        Button button = findViewById(R.id.button_return);
        button.setOnClickListener(b -> {
            if (!TextUtils.isDigitsOnly(editText.getText().toString())) {
                Toast.makeText(this, "Въведи число", Toast.LENGTH_SHORT).show();
            } else if (PreferenceManager.getUserTakenBooks().contains(Integer.valueOf(editText.getText().toString()))) {
                try {
                    returnBook(Integer.parseInt(editText.getText().toString()));
                    editText.setText("");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else {
                Toast.makeText(this, "Грешен ID на книгата", Toast.LENGTH_SHORT).show();
            }
        });
        Button buttonBack = findViewById(R.id.button_return_back);
        buttonBack.setOnClickListener(b -> {
            Intent intent = new Intent(ReturnActivity.this, StartMenu.class);
            startActivity(intent);
        });
    }

    private void returnBook(int bookID) throws IOException {
        if (!new File(getFilesDir(), "books.txt").exists()) {
            Toast.makeText(this, "Книгите не са създадени връщаме назад", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(ReturnActivity.this, MainActivity.class);
            startActivity(intent);
        }
        try {
            // id, name, author, userid, istaken, datataken
//            BufferedReader reader = new BufferedReader(new InputStreamReader(getResources().openRawResource(R.raw.books)));
            BufferedReader reader = new BufferedReader(new InputStreamReader(openFileInput("books.txt")));
            String line;
            int counter = 0;
            StringBuilder fileContent = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                counter++;
                if (counter == bookID) {
                    String[] parts = line.split(",");
                    parts[3] = "0";
                    parts[4] = "0";
                    parts[5] = "0";

                    fileContent.append(parts[0]).append(",").append(parts[1]).append(",").append(parts[2]).append(",").append(parts[3]).append(",").append(parts[4]).append(",").append(parts[5]).append("\n");
                } else {
                    fileContent.append(line).append("\n");
                }
            }

            BufferedWriter writer = new BufferedWriter(new FileWriter(getFilesDir() + File.separator + "books.txt"));
            writer.write(fileContent.toString());
            writer.flush();
            writer.close();
            reader.close();

            Toast.makeText(this,"Успешно върната книга",Toast.LENGTH_SHORT).show();

            PreferenceManager.removeUserTakenBooks(bookID);

        } catch (IOException e) {
            e.printStackTrace();
        }

        displayBooks();
    }

    private boolean displayBooks() {
        try {
            // id, name, author, userid, istaken, datataken
//            BufferedReader reader = new BufferedReader(new InputStreamReader(getResources().openRawResource(R.raw.books)));
            tableLayout.removeAllViews();

            BufferedReader reader = new BufferedReader(new InputStreamReader(openFileInput("books.txt")));
            String line;
            int counter = 0;
            while ((line = reader.readLine()) != null) {
                counter++;
                String[] parts = line.split(",");
                if (line.split(",")[3].equals(PreferenceManager.getID(this))) {
                    PreferenceManager.setUserTakenBooks(counter);

                    TableRow tableRow = new TableRow(this);

                    TextView tv = new TextView(this);
                    tv.setPadding(10, 10, 10, 10);
                    line = InsertNewLines.insertNewLines(line, 64);
                    tv.setText(line);

                    tableRow.addView(tv);

                    tableLayout.addView(tableRow);
                }
            }
            reader.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }
}
