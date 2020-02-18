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
import com.aidangordon.boggleevaluatorapi.exception.LocalizationNotSupportedException;

@Service
public class BoggleDictionaryService {
    /**
     * Returns a set of dictionary words for the given language
     * @param localization
     * @return Set of dictionary words for the given language
     * @throws FailureToBuildDictionaryException
     * @throws LocalizationNotSupportedException
     */
    public Set getDictionary(final String localization) throws FailureToBuildDictionaryException, LocalizationNotSupportedException {
        //we currently only support english
        if (!localization.equals("en")) {
            throw new LocalizationNotSupportedException("Currently only english is supported");
        }
        InputStream inputStream = getClass()
                .getClassLoader().getResourceAsStream("dictionary.en.txt");
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
