package com.hipravin.stream.pwdgen;

import java.nio.CharBuffer;
import java.util.Random;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class StreamImplGenerator implements Generator {
    private final Random random;

    public StreamImplGenerator() {
        random = new Random();
    }

    public StreamImplGenerator(long seed) {
        random = new Random(seed);
    }

    @Override
    public char[] randomPassword() {
//        return randomChars(PasswordUtils.PASSWORD_ACCEPTABLE_CHARS, PasswordUtils.DEFAULT_RANDOM_PASWORD_LENGTH);
//        return randomCharsWithCharBuffer(PasswordUtils.PASSWORD_ACCEPTABLE_CHARS, PasswordUtils.DEFAULT_RANDOM_PASWORD_LENGTH);
        return randomCharsWithCharBufferPrimitive(
                PasswordUtils.PASSWORD_ACCEPTABLE_CHARS, PasswordUtils.DEFAULT_RANDOM_PASWORD_LENGTH);
    }

    private char[] randomChars(char[] candidates, int charCount) {
        return random.ints(charCount, 0, candidates.length)
                .mapToObj(index -> candidates[index])
                .map(String::valueOf)
                .collect(Collectors.joining())
                .toCharArray();
    }

    private char[] randomCharsWithCharBuffer(char[] candidates, int charCount) {
        return random.ints(charCount, 0, candidates.length)
                .mapToObj(index -> candidates[index])
                .collect(Collector.of(
                        () -> CharBuffer.allocate(charCount),
                        CharBuffer::put,
                        CharBuffer::put,
                        CharBuffer::array));
    }

    private char[] randomCharsWithCharBufferPrimitive(char[] candidates, int charCount) {
        return random.ints(charCount, 0, candidates.length)
                .map(index -> candidates[index])
                .collect(() -> CharBuffer.allocate(charCount),
                        (buffer, c) -> buffer.put((char) c),
                        CharBuffer::put)
                .array();
    }

    //with Stream, it is awkward to operate with char
    //equivalent code working with String is 2 lines shorter
    private String randomString(String[] candidates, int partCount) {
        return random.ints(partCount, 0, candidates.length)
                .mapToObj(index -> candidates[index])
                .collect(Collectors.joining());
    }
}
