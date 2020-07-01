package com.example.wordcounter.helper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FileHelperTests {

    private FileHelper fileHelper;

    private MockMultipartFile mockFile = new MockMultipartFile("file", "test.txt", "text/plain", "123. 456 789;".getBytes());

    @BeforeEach
    public void setup() throws IOException {
        fileHelper = new FileHelper();
        fileHelper.readFile(mockFile);
    }

    @Test
    public void test_getWordCount() {
        assertEquals(fileHelper.getWordCount(), 3);
    }

    @Test
    public void test_getAverageWordLength() {
        assertEquals(fileHelper.getAverageWordLength(), 3);
    }

    @Test
    public void test_getWordLengths() {
        Map<Integer, Integer> wordLengths = new HashMap<>();
        wordLengths.put(3, 3);

        assertEquals(fileHelper.getWordLengths(), wordLengths);
    }

    @Test
    public void test_getMostFrequent() {
        Map<Integer, List<Integer>> mostFrequent = new HashMap<>();
        List<Integer> num = new ArrayList<>();
        num.add(3);
        mostFrequent.put(3, num);

        assertEquals(fileHelper.getMostFrequent(), mostFrequent);
    }

    @Test
    public void test_getMostFrequent_multipleValues() throws IOException {
        FileHelper fileHelper = new FileHelper();
        MockMultipartFile mockFile = new MockMultipartFile("file", "test.txt", "text/plain", "123; 456 789, this. will; work;".getBytes());
        fileHelper.readFile(mockFile);

        Map<Integer, List<Integer>> mostFrequent = new HashMap<>();
        List<Integer> num = new ArrayList<>();
        num.add(3);
        num.add(4);
        mostFrequent.put(3, num);

        assertEquals(fileHelper.getMostFrequent(), mostFrequent);
    }
}
