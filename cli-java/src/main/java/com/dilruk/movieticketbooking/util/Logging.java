package com.dilruk.movieticketbooking.util;

import com.dilruk.movieticketbooking.exception.FileWritingException;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.logging.Logger;


public class Logging {
    private static final Logger logger = Logger.getLogger(Logging.class.getName());
    public static String logFilePath;
    private static PrintStream printStream;

    /**
     * Initializes the logging system by creating a new log file with a timestamped name.
     * <p>
     * This should be called at the start of each simulation.
     */
    public static void initialize() {
        try {
            logFilePath = FileHandlerUtil.generateLogFilePath();
            FileHandlerUtil.createLogFile(logFilePath);

            printStream = new PrintStream(new FileOutputStream(logFilePath, false));
            logger.info("Logging initialized. Log file: " + logFilePath + "\n");
        } catch (IOException e) {
            throw new FileWritingException("Failed to initialize logging: " + e.getMessage());
        }
    }

    /**
     * Allows to add console output with logging to a log file
     * @param message
     */
    public static void printlnAndLog(String message) {
        System.out.println(message);
        if (printStream != null) {
            printStream.println(message); // Print message to file
        }
    }

    /**
     * Allows to add logging to a log file without console output
     * @param message
     */
    public static void log(String message) {
        if (printStream != null) {
            printStream.println(message); // Print message to file
        }
    }

    /**
     * Closes the underlying print stream used for logging.
     * <p>
     * This method should be called when the logging is no longer needed.
     */
    public static void close() {
        if (printStream != null) {
            printStream.close();
        }
    }
}
