package com.dilruk.movieticketbooking.util;

public class ColorUtil {

    // Find how to colour the console outputs using ChatGPT for better UX.

    // Reset the color
    public static final String RESET = "\033[0m";

    // Text colors
    public static final String RED = "\033[31m";
    public static final String GREEN = "\033[32m";
    public static final String YELLOW = "\033[33m";
    public static final String BLUE = "\033[34m";
    public static final String MAGENTA = "\033[35m";
    public static final String CYAN = "\033[36m";
    public static final String WHITE = "\033[37m";

    // Background colors
    public static final String BLACK_BACKGROUND = "\033[40m";
    public static final String RED_BACKGROUND = "\033[41m";
    public static final String GREEN_BACKGROUND = "\033[42m";
    public static final String YELLOW_BACKGROUND = "\033[43m";
    public static final String BLUE_BACKGROUND = "\033[44m";
    public static final String MAGENTA_BACKGROUND = "\033[45m";
    public static final String CYAN_BACKGROUND = "\033[46m";
    public static final String WHITE_BACKGROUND = "\033[47m";

    // Bold text
    public static final String BOLD = "\033[1m";
    // Underlined text
    public static final String UNDERLINE = "\033[4m";

    // Helper method to colorize text
    public static String colorize(String text, String color) {
        return color + text + RESET;
    }
}

