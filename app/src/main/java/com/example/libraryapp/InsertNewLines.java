package com.example.libraryapp;

public class InsertNewLines {
    public static String insertNewLines(String text, int lineLength) {
        //взима подадения текст, докато не е стигнал края изчислява следващата позиция
        //проверява да не е подминало края на низа
        // когато е на позицията връща назад докато не стигне интервал за да не разбива дума на два реда
        // добавя символите на реда и почва нов ред

        StringBuilder builder = new StringBuilder();
        int currentPosition = 0;
        while (currentPosition < text.length()) {
            int nextPosition = Math.min(currentPosition + lineLength, text.length());
            if (nextPosition < text.length())
                while (text.charAt(nextPosition) != ' ') {
                    nextPosition--;
                }

            builder.append(text.substring(currentPosition, nextPosition).trim());
            builder.append("\n");
            currentPosition = nextPosition;
        }
        return builder.toString();
    }
}
