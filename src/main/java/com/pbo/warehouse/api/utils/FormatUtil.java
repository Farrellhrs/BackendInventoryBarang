package com.pbo.warehouse.api.utils;

public class FormatUtil {
    public static String capitalizeFirstLetter(String input) {
        // Split the string into words using space as the delimiter
        String[] words = input.split("\\s+");

        // StringBuilder to build the result
        StringBuilder formatted = new StringBuilder();

        for (String word : words) {
            if (word.length() > 0) {
                // Convert the whole word to lowercase first
                String lowercaseWord = word.toLowerCase();

                // Capitalize the first letter and append the rest
                formatted.append(Character.toUpperCase(lowercaseWord.charAt(0)))
                        .append(lowercaseWord.substring(1))
                        .append(" "); // Add a space after each word
            }
        }

        // Trim the trailing space and return the result
        return formatted.toString().trim();
    }
}
