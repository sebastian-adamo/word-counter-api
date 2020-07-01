package com.example.wordcounter.helper;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

public class FileHelper {

    private static final Set<Character> chars = new HashSet<>(Arrays.asList('.', ',', '?', '!', '\'', '"', ':', ';', '-', '(', ')', '[', ']'));

    private ArrayList<String> words = new ArrayList<>();

    public FileHelper() { }

    public void readFile(MultipartFile file) throws IOException {
        Reader reader = new InputStreamReader(file.getInputStream());
        BufferedReader bufferedReader = new BufferedReader(reader);
        String s;

        while((s = bufferedReader.readLine()) != null) {
            for (String word : StringUtils.split(s)) {
                // Removing punctuation
                for (Character c : chars) {
                    if (word.contains(c.toString())) {
                        word = word.replace(c.toString(), "");
                    }
                }
                if (word.length() > 0) {
                    words.add(word);
                }
            }
        }

        reader.close();
    }

    public int getWordCount() {
        return words.size();
    }

    public double getAverageWordLength() {
        int totalCharacters = 0;

        for (String word: words) {
            totalCharacters += word.length();
        }

        // Rounding to 3 decimal places
        BigDecimal bigDecimal = new BigDecimal(Double.toString((double) totalCharacters/words.size()));
        bigDecimal = bigDecimal.setScale(3, RoundingMode.HALF_UP);

        return bigDecimal.doubleValue();
    }

    public Map<Integer, Integer> getWordLengths() {
        Map<Integer, Integer> returnMap = new HashMap<>();

        for (String word : words) {
            int currentLength = word.length();

            if (returnMap.containsKey(currentLength)) {
                returnMap.put(currentLength, returnMap.get(currentLength)+1);
            }
            else {
                returnMap.put(currentLength, 1);
            }
        }
        return returnMap;
    }

    public Map<Integer, List<Integer>> getMostFrequent() {
        int mostFrequentAmount = 0;
        List<Integer> mostFrequentValues = new ArrayList<>();

        for (int key : getWordLengths().keySet()) {
            if (getWordLengths().get(key) > mostFrequentAmount) {
                mostFrequentAmount = getWordLengths().get(key);
                mostFrequentValues.clear();
                mostFrequentValues.add(key);
            }
            else if (getWordLengths().get(key) == mostFrequentAmount) {
                mostFrequentValues.add(key);
            }
        }

        Map<Integer, List<Integer>> returnMap = new HashMap<>();
        returnMap.put(mostFrequentAmount, mostFrequentValues);

        return returnMap;
    }
}
