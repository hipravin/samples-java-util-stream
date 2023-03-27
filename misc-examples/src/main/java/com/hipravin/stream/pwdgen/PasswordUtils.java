package com.hipravin.stream.pwdgen;

public final class PasswordUtils {
    private PasswordUtils() {}

    private static final String ALPHABET_LC = "abcdefghijklmnopqrstuvwxyz";
    private static final String ALPHABET_UC = ALPHABET_LC.toUpperCase();
    private static final String DIGITS = "0123456789";
    private static final String SPECIAL = "@#$^?!";

    public static char[] PASSWORD_ACCEPTABLE_CHARS = ALPHABET_LC
            .concat(ALPHABET_UC)
            .concat(DIGITS)
            .concat(SPECIAL)
            .toCharArray();

    public static int DEFAULT_RANDOM_PASWORD_LENGTH = 10;
}
