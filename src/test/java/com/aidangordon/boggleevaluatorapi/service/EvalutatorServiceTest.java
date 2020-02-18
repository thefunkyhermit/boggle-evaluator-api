package com.aidangordon.boggleevaluatorapi.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;

import com.aidangordon.boggleevaluatorapi.exception.FailureToBuildDictionaryException;
import com.aidangordon.boggleevaluatorapi.exception.LocalizationNotSupportedException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class EvalutatorServiceTest {
    private static Set dictionary;

    private static Set<String> actual;

    @BeforeClass
    public static void setup() throws FailureToBuildDictionaryException, LocalizationNotSupportedException {
        BoggleDictionaryService dictionaryService = new BoggleDictionaryService();
        dictionary = dictionaryService.getDictionary("en");
        EvaluatorService evaluatorService = new EvaluatorService(dictionary);
        List<String> row1 = new ArrayList<String>() {
            {
                add("N");
                add("A");
                add("C");
                add("I");
            }
        };
        List<String> row2 = new ArrayList<String>() {
            {
                add("O");
                add("L");
                add("G");
                add("Y");
            }
        };
        List<String> row3 = new ArrayList<String>() {
            {
                add("W");
                add("E");
                add("S");
                add("U");
            }
        };
        List<String> row4 = new ArrayList<String>() {
            {
                add("B");
                add("T");
                add("O");
                add("E");
            }
        };
        List<List<String>> boardLetters = new ArrayList<List<String>>() {{
            add(row1);
            add(row2);
            add(row3);
            add(row4);

        }};
        actual = evaluatorService.evaluateBoard(boardLetters);
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
