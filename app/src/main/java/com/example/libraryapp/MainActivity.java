package com.example.libraryapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        File books = new File(getFilesDir(), "books.txt");
        if (!books.exists()) {
            try {
                books.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            createBooksFile();
        } else if (books.length() == 0) {
            createBooksFile();
        }

        try {
            int counter = 0;
            BufferedReader reader = new BufferedReader(new InputStreamReader(getResources().openRawResource(R.raw.users)));
            while (reader.readLine() != null) {
                counter++;
            }

            Toast.makeText(this,String.valueOf(counter),Toast.LENGTH_SHORT).show();

            for (int i = 1; i <= counter; i++) {
                File user = new File(getFilesDir(), "user" + i + ".txt");
                Toast.makeText(this,"user" + counter + ".txt",Toast.LENGTH_SHORT).show();

                if (!user.exists()) {
                    user.createNewFile();
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        EditText usernameField = findViewById(R.id.editTextText);
        EditText passwordField = findViewById(R.id.editTextText2);
        Button login = findViewById(R.id.button);

        login.setOnClickListener(v -> {
            String username = usernameField.getText().toString();
            String password = passwordField.getText().toString();
            if (checkCredentials(username, password)) {
                Intent intent = new Intent(MainActivity.this, StartMenu.class);
                startActivity(intent);
                Toast.makeText(getApplicationContext(), "Login successful!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), "Invalid username or password!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean checkCredentials(String username, String password) {
        PreferenceManager.setID(this,1);
        return true;

//        try {
//            BufferedReader reader = new BufferedReader(new InputStreamReader(getResources().openRawResource(R.raw.users)));
//            String line;
//            int id = 0;
//            while ((line = reader.readLine()) != null) {
//                String[] parts = line.split(",");
//                String storedUsername = parts[0];
//                String storedPassword = parts[1];
//
//                id++;
//                if (storedUsername.equals(username) && storedPassword.equals(password)) {
//                    PreferenceManager.setID(this, id);
//                    return true;
//                }
//            }
//            reader.close();
//
//        } catch (IOException e) {
//            Toast.makeText(getApplicationContext(),username +password,Toast.LENGTH_SHORT).show();
//
//            e.printStackTrace();
//        }
//        return false;
    }

    private void createBooksFile() {
        try {
            String data = "001,To Kill a Mockingbird,Harper Lee,0,0,0\n" +
                    "002,1984,George Orwell,0,0,0\n" +
                    "003,Pride and Prejudice,Jane Austen,0,0,0\n" +
                    "004,The Great Gatsby,F.Scott Fitzgerald,104,1,2023-04-18\n" +
                    "005,To the Lighthouse,Virginia Woolf,105,1,2023-04-19\n" +
                    "006,Moby-Dick,Herman Melville,106,1,2023-04-20\n" +
                    "007,The Catcher in the Rye,J.D.Salinger,107,1,2023-04-21\n" +
                    "008,Jane Eyre,Charlotte Brontë,108,1,2023-04-22\n" +
                    "009,Brave New World,Aldous Huxley,109,1,2023-04-23\n" +
                    "010,The Lord of the Rings,J.R.R.Tolkien,110,1,2023-04-24\n" +
                    "011,Harry Potter and the Sorcerer's Stone,J.K.Rowling,111,1,2023-04-25\n" +
                    "012,The Hobbit,J.R.R.Tolkien,112,1,2023-04-26\n" +
                    "013,Frankenstein,Mary Shelley,113,1,2023-04-27\n" +
                    "014,Crime and Punishment,Fyodor Dostoevsky,114,1,2023-04-28\n" +
                    "015,The Odyssey,Homer,115,1,2023-04-29\n" +
                    "016,The Picture of Dorian Gray,Oscar Wilde,116,1,2023-04-30\n" +
                    "017,The Adventures of Huckleberry Finn,Mark Twain,117,1,2023-05-01\n" +
                    "018,The Scarlet Letter,Nathaniel Hawthorne,118,1,2023-05-02\n" +
                    "019,Wuthering Heights,Emily Brontë,119,1,2023-05-03\n" +
                    "020,The Count of Monte Cristo,Alexandre Dumas,120,1,2023-05-04\n" +
                    "021,The Alchemist,Paulo Coelho,121,1,2023-05-05\n" +
                    "022,One Hundred Years of Solitude,Gabriel García Márquez,122,1,2023-05-06\n" +
                    "023,The Kite Runner,Khaled Hosseini,123,1,2023-05-07\n" +
                    "024,The Hobbit,J.R.R. Tolkien,124,1,2023-05-08\n" +
                    "025,The Brothers Karamazov,Fyodor Dostoevsky,125,1,2023-05-09\n" +
                    "026,Anna Karenina,Leo Tolstoy,126,1,2023-05-10\n" +
                    "027,The Book Thief,Markus Zusak,127,1,2023-05-11\n" +
                    "028,The Shining,Stephen King,128,1,2023-05-12\n" +
                    "029,The Hunger Games,Suzanne Collins,129,1,2023-05-13\n" +
                    "030,The Chronicles of Narnia,C.S. Lewis,130,1,2023-05-14";

            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(openFileOutput("books.txt", Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }
}