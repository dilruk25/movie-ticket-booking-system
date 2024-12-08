package com.dilruk.movieticketbooking.util;

import com.dilruk.movieticketbooking.exception.FileWritingException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;


public class LogUtil {

    private static PrintStream printStream;

    // Use static block for one time initialization
    static {
        try {
            String logFilePath = "logs.txt";
            File logFile = new File(logFilePath);

            if (logFile.exists() && !logFile.delete()) {
                System.out.println("\nExisting file cannot be deleted: " + logFilePath);
                System.out.println("Overwriting existing file: " + logFilePath + "\n");
            } else {
                System.out.println("\nCreated new file: " + logFilePath + "\n");
            }

            printStream = new PrintStream(new FileOutputStream(logFile, false));

        } catch (IOException e) {
            throw new FileWritingException(e.getMessage());
        }
    }

    public static void printLogLn(String message) {
        System.out.println(message);
        if (printStream != null) {
            printStream.println(message); // Print message to file
        }
    }

    public static void printLog(String message) {
        System.out.print(message);
        if (printStream != null) {
            printStream.print(message); // Print message to file
        }
    }

    public static void close() {
        if (printStream != null) {
            printStream.close();
        }
    }
}
