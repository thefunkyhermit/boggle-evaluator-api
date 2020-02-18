package com.aidangordon.boggleevaluatorapi;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.aidangordon.boggleevaluatorapi.controllers.EvaluationController;
import com.aidangordon.boggleevaluatorapi.entities.BoggleBoard;
import com.aidangordon.boggleevaluatorapi.entities.EvaluationResponse;
import com.aidangordon.boggleevaluatorapi.exception.FailureToBuildDictionaryException;
import com.aidangordon.boggleevaluatorapi.exception.LocalizationNotSupportedException;
import com.aidangordon.boggleevaluatorapi.service.BoggleDictionaryService;
import com.aidangordon.boggleevaluatorapi.service.EvaluatorService;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class EvaluationControllerTest {
    @Before
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Mock
    BoggleDictionaryService boggleDictionaryService;

    @Mock
    EvaluatorService evaluatorService;

    @InjectMocks
    EvaluationController controller = new EvaluationController();

    @Test
    public void testValidation_ValidReturn() throws FailureToBuildDictionaryException, LocalizationNotSupportedException {
        List<String> row1 = new ArrayList<String>() {
            {
                add("H");
                add("A");
                add("A");
                add("T");
            }
        };
        List<String> row2 = new ArrayList<String>() {
            {
                add("H");
                add("A");
                add("A");
                add("T");
            }
        };
        List<String> row3 = new ArrayList<String>() {
            {
                add("H");
                add("A");
                add("A");
                add("T");
            }
        };
        List<String> row4 = new ArrayList<String>() {
            {
                add("H");
                add("A");
                add("A");
                add("T");
            }
        };
        List<List<String>> boardLetters = new ArrayList<List<String>>() {{
            add(row1);
            add(row2);
            add(row3);
            add(row4);

        }};
        final BoggleBoard board = new BoggleBoard(boardLetters);
        final Set<String> dictionary = new HashSet() {{
            add("WORD");
        }};

        when(boggleDictionaryService.getDictionary(anyString())).thenReturn(dictionary);
        when(evaluatorService.evaluateBoard(any(List.class))).thenReturn(dictionary);

        final ResponseEntity<EvaluationResponse> evaluationResponseResponseEntity = controller.evaluateBoard("en", board);
        assertEquals(evaluationResponseResponseEntity.getStatusCode(), HttpStatus.CREATED);
        Set<String> expected = new HashSet<>();
        expected.add("WORD");
        assertEquals(expected, evaluationResponseResponseEntity.getBody().getResults());
    }

    @Test
    public void testValidation_TooManyChars() throws FailureToBuildDictionaryException, LocalizationNotSupportedException {
        List<String> row1 = new ArrayList<String>() {
            {
                add("HA");
                add("A");
                add("A");
                add("T");
            }
        };
        List<String> row2 = new ArrayList<String>() {
            {
                add("H");
                add("A");
                add("A");
                add("T");
            }
        };
        List<String> row3 = new ArrayList<String>() {
            {
                add("H");
                add("A");
                add("A");
                add("T");
            }
        };
        List<String> row4 = new ArrayList<String>() {
            {
                add("H");
                add("A");
                add("A");
                add("T");
            }
        };
        List<List<String>> boardLetters = new ArrayList<List<String>>() {{
            add(row1);
            add(row2);
            add(row3);
            add(row4);

        }};
        final BoggleBoard board = new BoggleBoard(boardLetters);
        final ResponseEntity<EvaluationResponse> evaluationResponseResponseEntity = controller.evaluateBoard("en", board);
        assertEquals(HttpStatus.BAD_REQUEST, evaluationResponseResponseEntity.getStatusCode());
    }

    @Test
    public void testValidation_EmptyChar() throws FailureToBuildDictionaryException, LocalizationNotSupportedException {
        List<String> row1 = new ArrayList<String>() {
            {
                add("");
                add("A");
                add("A");
                add("T");
            }
        };
        List<String> row2 = new ArrayList<String>() {
            {
                add("H");
                add("A");
                add("A");
                add("T");
            }
        };
        List<String> row3 = new ArrayList<String>() {
            {
                add("H");
                add("A");
                add("A");
                add("T");
            }
        };
        List<String> row4 = new ArrayList<String>() {
            {
                add("H");
                add("A");
                add("A");
                add("T");
            }
        };
        List<List<String>> boardLetters = new ArrayList<List<String>>() {{
            add(row1);
            add(row2);
            add(row3);
            add(row4);

        }};
        final BoggleBoard board = new BoggleBoard(boardLetters);
        final ResponseEntity<EvaluationResponse> evaluationResponseResponseEntity = controller.evaluateBoard("en", board);
        assertEquals(HttpStatus.BAD_REQUEST, evaluationResponseResponseEntity.getStatusCode());
    }

    @Test(expected = LocalizationNotSupportedException.class)
    public void testValidation_UnsupportedDictionary() throws FailureToBuildDictionaryException, LocalizationNotSupportedException {
        when(boggleDictionaryService.getDictionary(anyString())).thenCallRealMethod();
        final ResponseEntity<EvaluationResponse> evaluationResponseResponseEntity = controller.evaluateBoard("fr", null);
    }
    //TODO Add test for non-valid chars, punctuation, etc
}
