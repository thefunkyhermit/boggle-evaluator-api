package com.aidangordon.boggleevaluatorapi.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.aidangordon.boggleevaluatorapi.exception.FailureToBuildDictionaryException;

import static org.junit.Assert.*;

public class EvalutatorServiceTest {
    private static Set dictionary;
    static char[][] boggleBoard = {
            { 'N', 'A', 'C', 'I' },
            { 'O', 'L', 'G', 'Y' },
            { 'W', 'E', 'S', 'U' },
            { 'B', 'T', 'O', 'E' },
    };
    private static Set<String> actual;

    @BeforeClass
    public static void setup() throws FailureToBuildDictionaryException {
        BoggleDictionaryService dictionaryService = new BoggleDictionaryService();
        dictionary = dictionaryService.getDictionary();
        EvaluatorService evaluatorService = new EvaluatorService(dictionary);
        actual = evaluatorService.evaluateBoard(boggleBoard);
    }

    @Test
    public void testEvalutation_findsWordsSuccessfully() throws FailureToBuildDictionaryException {


        assertEquals(158, actual.size());
        //TODO - do not want to test for every word in list (would eventually consider doing this but not now.
        assertTrue(actual.contains("legacy"));
        assertTrue(actual.contains("canoe"));
        assertTrue(actual.contains("lowest"));
        assertTrue(actual.contains("now"));
        assertTrue(actual.contains("toe"));
        assertTrue(actual.contains("best"));
        assertTrue(actual.contains("glow"));
    }
    @Test
    public void testEvalutation_checkForWordsNotOnBoard() throws FailureToBuildDictionaryException {
        assertFalse(actual.contains("z"));
        assertFalse(actual.contains("change"));
    }

    @Test
    public void testEvalutation_checkForLettersOnBoardButAreNotWords() throws FailureToBuildDictionaryException {
        assertFalse(actual.contains("nl"));
    }
}
