package com.hipravin.stream.pwdgen;

import java.util.Random;

public class ImperativeGenerator implements Generator {
    private final Random random;

    public ImperativeGenerator() {
        random = new Random();
    }

    public ImperativeGenerator(long seed) {
        random = new Random(seed);
    }

    @Override
    public char[] randomPassword() {
        return randomChars(PasswordUtils.PASSWORD_ACCEPTABLE_CHARS,
                PasswordUtils.DEFAULT_RANDOM_PASWORD_LENGTH);
    }

    private char[] randomChars(char[] candidates, int charCount) {
        char[] result = new char[charCount];
        for (int i = 0; i < charCount; i++) {
            result[i] = candidates[random.nextInt(candidates.length)];
        }
        return result;
    }
}

