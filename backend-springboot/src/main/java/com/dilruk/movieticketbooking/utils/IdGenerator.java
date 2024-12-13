package com.dilruk.movieticketbooking.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

/**
 * Utility class to generate unique IDs with a customizable prefix.
 */
public class IdGenerator {

    private static final DateTimeFormatter TIMESTAMP_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

    /**
     * Generates a unique ID using a prefix, current timestamp, and a random UUID fragment.
     *
     * @param prefix The prefix for the ID, typically denoting the type or context of the entity.
     * @return A unique ID string.
     * @throws IllegalArgumentException If the prefix is null or empty.
     */
    public static String generateId(String prefix) {
        if (prefix == null || prefix.trim().isEmpty()) {
            throw new IllegalArgumentException("Prefix cannot be null or empty");
        }

        String timeStamp = LocalDateTime.now().format(TIMESTAMP_FORMATTER);
        String uuidFragment = UUID.randomUUID().toString().substring(0, 8);

        return prefix + "-" + timeStamp + "-" + uuidFragment;
    }
}