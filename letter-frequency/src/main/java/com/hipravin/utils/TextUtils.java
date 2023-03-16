package com.hipravin.utils;

import java.util.Objects;

public final class TextUtils {
    private TextUtils() {}

    public static final String SPLIT_WORDS_REGEX = "[\\p{Punct}\\s^0-9]+";

    public static String[] splitWords(String text) {
        Objects.requireNonNull(text);
        return text.split(SPLIT_WORDS_REGEX);
    }
}
