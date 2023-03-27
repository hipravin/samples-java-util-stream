package com.hipravin.stream.pwdgen;

import org.junit.jupiter.api.RepeatedTest;

import java.nio.CharBuffer;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GeneratorTest {
    static Generator streamImplGenerator = new StreamImplGenerator(0);
    static Set<Character> ACCEPTABLE_PASSWORD_CHARS = toCharacterSet(PasswordUtils.PASSWORD_ACCEPTABLE_CHARS);

    @RepeatedTest(5)
    void testSampleGenerate() {
        char[] password = streamImplGenerator.randomPassword();
        System.out.println(String.valueOf(password));
        assertEquals(10, password.length);
    }

    static Set<Character> toCharacterSet(char[] chars) {
        return CharBuffer.wrap(chars)
                .chars()
                .mapToObj(c -> (char) c)
                .collect(Collectors.toSet());
    }

}