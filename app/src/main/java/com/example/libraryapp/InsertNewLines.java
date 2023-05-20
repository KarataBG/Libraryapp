package com.example.libraryapp;

public class InsertNewLines {
    public static String insertNewLines(String text, int lineLength) {
        StringBuilder builder = new StringBuilder();
        int currentPosition = 0;
        while (currentPosition < text.length()) {
            int nextPosition = Math.min(currentPosition + lineLength, text.length());
            while (nextPosition < text.length() && text.charAt(nextPosition) != ' ') {
                nextPosition--;
            }
            if (nextPosition == currentPosition) {
                nextPosition = currentPosition + lineLength;
            }
            builder.append(text.substring(currentPosition, nextPosition).trim());
            builder.append("\n");
            currentPosition = nextPosition;
        }
        return builder.toString();
    }
}
