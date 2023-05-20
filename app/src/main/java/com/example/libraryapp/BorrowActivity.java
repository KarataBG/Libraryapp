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

public class BorrowActivity extends AppCompatActivity {
    TableLayout tableLayout;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.borrow_book);

        tableLayout = findViewById(R.id.displayBooksTable);

        displayBooks();
        editText = findViewById(R.id.editText_borrow);
        Button button = findViewById(R.id.button_borrow);
        button.setOnClickListener(b -> {
            //ако потребителя е написал само цифри и тези цифри съвпадат с ид на свободна книга се заема
            if (!TextUtils.isDigitsOnly(editText.getText().toString())) {
                Toast.makeText(this, "Въведи число", Toast.LENGTH_SHORT).show();
            } else if (PreferenceManager.getFreeToTakeBooks().contains(Integer.valueOf(editText.getText().toString()))) {
                try {
                    borrowBook(Integer.parseInt(editText.getText().toString()));
                    editText.setText("");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else {
                Toast.makeText(this, "Грешен ID на книгата", Toast.LENGTH_SHORT).show();
            }
        });
        Button buttonBack = findViewById(R.id.button_borrow_back);
        buttonBack.setOnClickListener(b -> {
            PreferenceManager.clearFreeToTakeBooks();
            Intent intent = new Intent(BorrowActivity.this, StartMenu.class);
            startActivity(intent);
        });
    }

    private void borrowBook(int bookID) throws IOException {
        //Пак проверява дали книгите съществуват като допълнителна проверка ако е станало нещо с базата данни междувременно
        if (!new File(getFilesDir(), "books.txt").exists()) {
            Toast.makeText(this, "Книгите не са създадени връщаме назад", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(BorrowActivity.this, MainActivity.class);
            startActivity(intent);
        }
        // Стига до реда като преминава ред по ред и инкрементира брояча
        // Като стигне до ид книга 5 брояч 5 и редът е също 5 променя реда
        // Полето userID възлага userID, isTaken True, date date
        // Останалите редове само ги преписва като ги зареди в паметта
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(openFileInput("books.txt")));
            String line;
            int counter = 0;
            StringBuilder fileContent = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                counter++;
                if (counter == bookID) {
                    String[] parts = line.split(",");
                    parts[3] = String.valueOf(PreferenceManager.getID(this));
                    parts[4] = "1";
                    parts[5] = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault()).format(Calendar.getInstance().getTime());

                    fileContent.append(parts[0]).append(",").append(parts[1]).append(",").append(parts[2]).append(",").append(parts[3]).append(",").append(parts[4]).append(",").append(parts[5]).append("\n");
                } else {
                    fileContent.append(line).append("\n");
                }
            }

            // Трябва целия файл да се зареди в памет, защото няма начин за записване на информация без презаписване
            BufferedWriter writer = new BufferedWriter(new FileWriter(getFilesDir() + File.separator + "books.txt"));
            writer.write(fileContent.toString());
            writer.flush();
            writer.close();
            reader.close();

            Toast.makeText(this, "Успешно взета книга", Toast.LENGTH_SHORT).show();

            PreferenceManager.removeFreeToTakeBooks(bookID);
        } catch (IOException e) {
            e.printStackTrace();
        }

        updateHistory(bookID);
        displayBooks();
    }

    private boolean displayBooks() {
        try {
            tableLayout.removeAllViews();

            BufferedReader reader = new BufferedReader(new InputStreamReader(openFileInput("books.txt")));
            String line;
            int counter = 0;
            while ((line = reader.readLine()) != null) {
                counter++;
                //На всеки ред проверява isTaken дали е 0 или 1
                // Ако е свободна книгата я добавя в списъка на свободни за взимане
                // Добавя текста в реда, реда в таблицата, таблицата е статично добавена в scrollView
                if (line.split(",")[4].equals("0")) {

                    PreferenceManager.setFreeToTakeBooks(counter);
                    TableRow tableRow = new TableRow(this);

                    TextView tv = new TextView(this);
                    tv.setPadding(10, 10, 10, 10);
                    line = InsertNewLines.insertNewLines(line, PreferenceManager.getLineLength(this));
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

    private boolean updateHistory(int bookID) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    openFileInput("user" + PreferenceManager.getID(this) + ".txt")));
            String line;
            int counter = 1;
            StringBuilder fileContent = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                counter++;
                fileContent.append(line).append("\n");
            }

            //Като зареди целия файл в паметта записва новия ред, който съдържа брояч(работи като ид заради инкрементацията)
            //userID, bookID, Date
            BufferedWriter writer = new BufferedWriter(new FileWriter(getFilesDir() + File.separator + "user" + PreferenceManager.getID(this) + ".txt"));
            fileContent.append(counter).append(",").append(PreferenceManager.getID(this)).append(",")
                    .append(bookID).append(",").append(new SimpleDateFormat("dd-MMM-yyyy",
                            Locale.getDefault()).format(Calendar.getInstance().getTime())).append("\n");
            writer.write(fileContent.toString());
            writer.flush();
            writer.close();

            Toast.makeText(this, "Успешно обновяване на историята" + bookID, Toast.LENGTH_SHORT).show();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
