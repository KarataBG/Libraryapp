package com.example.libraryapp;

import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.XmlResourceParser;
import android.graphics.Color;
import android.graphics.Interpolator;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

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

//    private boolean borrowBook(int bookID) {
//
////        try {
////            // Load the XML file
////            InputStream inputStream = getResources().openRawResource(R.xml.books);
////            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
////            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
////            Document doc = docBuilder.parse(inputStream);
////
////            // Modify the XML content
////            Element rootElement = doc.getDocumentElement();
////
////            // Find the element you want to edit
////            Node bookNode = rootElement.getElementsByTagName("Book").item(0);
////
////            // Update the element's content
////            Element isTakenElement = (Element) bookNode.getElementsByTagName("IsTaken").item(0);
////            isTakenElement.setTextContent("Yes");
////
////            // Save the changes to the XML file
////            TransformerFactory transformerFactory = TransformerFactory.newInstance();
////            Transformer transformer = transformerFactory.newTransformer();
////            DOMSource source = new DOMSource(doc);
////            StreamResult result = new StreamResult(xmlFile);
////            transformer.transform(source, result);
////
////            System.out.println("XML file successfully updated.");
////        } catch (Exception e) {
////            e.printStackTrace();
////        }
//
//        /**/
//
//        try {
//            File tempFile = File.createTempFile("temp", null);
//
//            // Read the original file and write to the temporary file
//            BufferedReader reader = new BufferedReader(new InputStreamReader(getResources().openRawResource(R.raw.users)));
////            BufferedWriter writer = new BufferedWriter(new );
////            OutputStream outputStream = getResources().openRawResource(R.raw.books);
////            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream));
//
////            BufferedReader reader = new BufferedReader(new InputStreamReader(getResources().openRawResource(R.raw.users)));
//            String line;
//            int counter = 0;
//            while ((line = reader.readLine()) != null) {
//                if (counter == bookID){
//                    String[] parts =
//                }
//            }
//            reader.close();
//
//        } catch (IOException e) {
//
//            e.printStackTrace();
//        }
//        return false;
//    }

    private void borrowBook(int bookID) throws IOException {
        if (!new File(getFilesDir(), "books.txt").exists()) {
            Toast.makeText(this, "Книгите не са създадени връщаме назад", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(BorrowActivity.this, MainActivity.class);
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
                    parts[3] = PreferenceManager.getID(this);
                    parts[4] = "1";
                    parts[5] = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault()).format(Calendar.getInstance().getTime());

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

            Toast.makeText(this, "Успешно взета книга", Toast.LENGTH_SHORT).show();

            PreferenceManager.removeFreeToTakeBooks(bookID);
        } catch (IOException e) {
            e.printStackTrace();
        }

        updateHistory(bookID);
        displayBooks();
    }


    //    private void copyFileFromAssets(AssetManager assetManager, String sourceFileName, File destinationFile) throws IOException {
//        BufferedReader reader = null;
//        BufferedWriter writer = null;
//
//        try {
//            InputStream inputStream = assetManager.open(sourceFileName);
//            reader = new BufferedReader(new InputStreamReader(inputStream));
//
//            writer = new BufferedWriter(new FileWriter(destinationFile));
//
//            String line;
//            while ((line = reader.readLine()) != null) {
//                writer.write(line);
//                writer.newLine();
//            }
//        } finally {
//            if (reader != null) {
//                try {
//                    reader.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            if (writer != null) {
//                try {
//                    writer.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }
    private boolean displayBooks() {
//        XmlResourceParser xmlParser = getResources().getXml(R.xml.books);
//
//        try {
//            int eventType = xmlParser.getEventType();
//            int counter =0;
//
//            while (eventType != XmlPullParser.END_DOCUMENT) {
//                if (eventType == XmlPullParser.START_TAG) {
//                    String tagName = xmlParser.getName();
//                    if ("Book".equals(tagName)) {
//                        counter++;
//                        xmlParser.next(); // Move to the next START_TAG event
//
//                        // Create a new TableRow
//                        TableRow tableRow = new TableRow(this);
//                        tableRow.setLayoutParams(new TableLayout.LayoutParams(
//                                TableLayout.LayoutParams.MATCH_PARENT,
//                                TableLayout.LayoutParams.WRAP_CONTENT
//                        ));
//
//                        String id = xmlParser.nextText();
//                        xmlParser.next();
//                        String name = xmlParser.nextText();
//                        xmlParser.next();
//                        String author = xmlParser.nextText();
//                        xmlParser.next();
//                        String userID = xmlParser.nextText();
//                        xmlParser.next();
//                        String isTaken = xmlParser.nextText();
//                        xmlParser.next();
//                        String dateTaken = xmlParser.nextText();
//                        xmlParser.next();
//
//                        if (isTaken.equals("0")) {
//
//                            PreferenceManager.setFreeToTakeBooks(this,counter);
//
//                            TextView idTextView = new TextView(this);
//                            idTextView.setText(id);
////                        idTextView.setText(xmlParser.getAttributeValue(null, "ID"));
//
//                            TextView nameTextView = new TextView(this);
//                            nameTextView.setText(name);
//
//                            TextView authorTextView = new TextView(this);
//                            authorTextView.setText(author);
//
//                            TextView userIDTextView = new TextView(this);
//                            authorTextView.setText(userID);
//
//                            TextView isTakenTextView = new TextView(this);
//                            authorTextView.setText(isTaken);
//
//                            TextView dateTakenTextView = new TextView(this);
//                            authorTextView.setText(dateTaken);
//
//
//                            tableRow.addView(idTextView);
//                            tableRow.addView(nameTextView);
//                            tableRow.addView(authorTextView);
//                            tableRow.addView(userIDTextView);
//                            tableRow.addView(isTakenTextView);
//                            tableRow.addView(dateTakenTextView);
//
//                            tableLayout.addView(tableRow);
//                        }
//                    }
//                }
//
//                eventType = xmlParser.next();
//            }
//        } catch (XmlPullParserException | IOException e) {
//            e.printStackTrace();
//        }


//        XmlPullParserFactory parserFactory;
//        try {
//            parserFactory = XmlPullParserFactory.newInstance();
//            XmlPullParser parser = parserFactory.newPullParser();
////            InputStream is = getResources().getXml(R.xml.books);
//            InputStream is = getResources().getAssets().open("books.xml");
//
//
//            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
//            parser.setInput(is, null);
//
//
//            ArrayList<Book> books = new ArrayList<>();
//            int eventType = parser.getEventType();
//            Book currentBook = null;
//
//            while (eventType != XmlPullParser.END_DOCUMENT) {
//                String eltName = null;
//
//                switch (eventType) {
//                    case XmlPullParser.START_DOCUMENT:
//                        books = new ArrayList<>();
//                        break;
//                    case XmlPullParser.START_TAG:
//                        eltName = parser.getName();
//
//                        if ("Book".equals(eltName)) {
//                            currentBook = new Book();
//                            books.add(currentBook);
//                        } else if (currentBook != null) {
//                            if ("ID".equals(eltName)) {
//                                currentBook.id = parser.nextText();
//                            } else if ("Name".equals(eltName)) {
//                                currentBook.name = parser.nextText();
//                            } else if ("Author".equals(eltName)) {
//                                currentBook.author = parser.nextText();
//                            } else if ("UserID".equals(eltName)) {
//                                currentBook.userid = parser.nextText();
//                            } else if ("IsTaken".equals(eltName)) {
//                                currentBook.istaken = parser.nextText();
//                            } else if ("DateTaken".equals(eltName)) {
//                                currentBook.datetaken = parser.nextText();
//                            }
//                        }
//                        break;
//                }
//                eventType = parser.next();
//
//            }
//
//            StringBuilder builder = new StringBuilder();
//
//            for (Book b : books) {
//                builder.append(b.id).append(b.name).append(b.author).append(b.userid).append(b.istaken).append(b.datetaken);
//            }
//
//            EditText editText = findViewById(R.id.editText_borrow);
//            editText.setText(builder.toString());
//
//        } catch (XmlPullParserException e) {
//            throw new RuntimeException(e);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
        try {
            // id, name, author, userid, istaken, datataken
//            BufferedReader reader = new BufferedReader(new InputStreamReader(getResources().openRawResource(R.raw.books)));
            tableLayout.removeAllViews();

            BufferedReader reader = new BufferedReader(new InputStreamReader(openFileInput("books.txt")));
            String line;
            int counter = 0;
            while ((line = reader.readLine()) != null) {
                counter++;
                if (line.split(",")[4].equals("0")) {

                    PreferenceManager.setFreeToTakeBooks(counter);
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

    private void updateHistory(int bookID) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(openFileInput("user" + PreferenceManager.getID(this) + ".txt")));
            String line;
            StringBuilder fileContent = new StringBuilder();
            while ((line = reader.readLine()) != null) {

                fileContent.append(line).append("\n");

            }

            BufferedWriter writer = new BufferedWriter(new FileWriter(getFilesDir() + File.separator + "user" + PreferenceManager.getID(this) + ".txt"));
            fileContent.append(PreferenceManager.getID(this) + "," + bookID + "," + new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault()).format(Calendar.getInstance().getTime()) + "\n");
            writer.write(fileContent.toString());
            writer.flush();
            writer.close();

            Toast.makeText(this, "Успешно обновяване на историята", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
