package com.dilruk.movieticketbooking.common.utils;

import com.dilruk.movieticketbooking.common.enums.IdPrefix;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class IdGenerator {

    public static String generateId(String prefix) {
        String timeStamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        String uuid = UUID.randomUUID().toString().substring(0, 8);

        return prefix + "-" + timeStamp + uuid;
    }
}
