package com.dilruk.movieticketbooking.util;

import java.util.List;
import java.util.Random;

public class TicketUtil {
    private List<String> elements;

    public static String getRandomString(List<String> elements) {
        Random random = new Random();
        int randomIndex = random.nextInt(0, elements.size());

        return elements.get(randomIndex);
    }

    public static void validateIntegers() {

    }
}
