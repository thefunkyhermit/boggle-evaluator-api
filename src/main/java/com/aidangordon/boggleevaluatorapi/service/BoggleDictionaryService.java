package com.aidangordon.boggleevaluatorapi.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.aidangordon.boggleevaluatorapi.exception.FailureToBuildDictionaryException;

@Service
public class BoggleDictionaryService {
    public Set getDictionary() throws FailureToBuildDictionaryException {
        InputStream inputStream = getClass()
                .getClassLoader().getResourceAsStream("dictionary.txt");
        Set<String> dictionary = new HashSet<>();

        InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
        BufferedReader reader = new BufferedReader(streamReader);
        try {
            for (String line; (line = reader.readLine()) != null; ) {
                dictionary.add(line.toLowerCase());
            }
        } catch (IOException e) {
            throw new FailureToBuildDictionaryException(e);
        }
        return dictionary;
    }
}
