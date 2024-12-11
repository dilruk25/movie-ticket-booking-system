package com.dilruk.movieticketbooking.util;

import com.dilruk.movieticketbooking.exception.FileWritingException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Logger;

import static com.dilruk.movieticketbooking.util.Logging.logFilePath;

public class FileHandlerUtil {

    private static final Logger logger = Logger.getLogger(FileHandlerUtil.class.getName());
    private static final String LOG_FILE_PREFIX = "logs_";
    private static final String LOG_FILE_EXTENSION = ".txt";

    /**
     * Generates a unique log file name using timestamp.
     */
    public static String generateLogFilePath() {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        return LOG_FILE_PREFIX + timestamp + LOG_FILE_EXTENSION;
    }

    public static void readFile() {

        BufferedReader bufferedReader = null;
        int fileCheckCounter = 0;

        if (logFilePath == null) {
            logger.info("Run the simulator first.\n");
            return;
        }

        File file = new File(logFilePath);

        if (!file.exists()) {
            logger.warning("File does not exist: " + logFilePath + "\n");
            return;
        }

        try {
            bufferedReader = new BufferedReader(new FileReader(file));
            String line;

            while (true) {
                if (fileCheckCounter > 10) { // After fair 10 rounds getting null lines, file reading is set to stopped predicting that the simulation has stopped.
                    bufferedReader.close();
                    return;
                }

                line = bufferedReader.readLine();
                if (line != null) {
                    System.out.println(line);
                    fileCheckCounter = 0;
                } else {
                    fileCheckCounter++;
                    Thread.sleep(1000);
                }
            }

        } catch (IOException e) {
            logger.warning("File can't be read." + e.getMessage() + "\n");
        } catch (InterruptedException e) {
            logger.warning("Interrupted reading file.\n");
        }
    }

    /**
     * Creates a new log file. If an old file exists, it is not overwritten.
     */
    public static void createLogFile(String filePath) {
        try {
            File logFile = new File(filePath);
            if (logFile.createNewFile()) {
                logger.info("Created new log file: " + filePath + "\n");
            } else {
                logger.warning("Log file already exists: " + filePath + "\n");
            }
        } catch (IOException e) {
            throw new FileWritingException("Error creating log file: " + e.getMessage());
        }
    }
}
