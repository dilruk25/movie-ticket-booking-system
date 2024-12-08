package com.dilruk.movieticketbooking.util;

import com.dilruk.movieticketbooking.exception.FileWritingException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.logging.Logger;


public class LogUtil {

    private static PrintStream printStream;
    private static final Logger logger = Logger.getLogger(LogUtil.class.getName());
    // Use static block for one time initialization
    static {
        try {
            String logFilePath = "logs.txt";
            File logFile = new File(logFilePath);

            if (logFile.exists() && !logFile.delete()) {
                logger.warning("\nExisting file cannot be deleted: " + logFilePath);
                logger.warning("Overwriting existing file: " + logFilePath + "\n");
            } else {
                logger.info("\nCreated new file: " + logFilePath + "\n");
            }

            printStream = new PrintStream(new FileOutputStream(logFile, false));

        } catch (IOException e) {
            throw new FileWritingException(e.getMessage());
        }
    }

    public static void printlnAndLog(String message) {
        System.out.println(message);
        if (printStream != null) {
            printStream.println(message); // Print message to file
        }
    }

    public static void log(String message) {
        if (printStream != null) {
            printStream.println(message); // Print message to file
        }
    }

    public static void close() {
        if (printStream != null) {
            printStream.close();
        }
    }
}
