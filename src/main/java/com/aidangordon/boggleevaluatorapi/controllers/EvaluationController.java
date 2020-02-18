package com.aidangordon.boggleevaluatorapi.controllers;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.aidangordon.boggleevaluatorapi.entities.BoggleBoard;
import com.aidangordon.boggleevaluatorapi.entities.EvaluationResponse;
import com.aidangordon.boggleevaluatorapi.exception.FailureToBuildDictionaryException;
import com.aidangordon.boggleevaluatorapi.exception.LocalizationNotSupportedException;
import com.aidangordon.boggleevaluatorapi.service.BoggleDictionaryService;
import com.aidangordon.boggleevaluatorapi.service.EvaluatorService;

/*
TODO - Fix localization to use proper HTTP headers
 */

@CrossOrigin
@RestController
public class EvaluationController {
    @Autowired
    EvaluatorService evaluatorService;

    @Autowired
    BoggleDictionaryService boggleDictionaryService;

    @PostMapping("/evaluations/{localization}")
    public ResponseEntity<EvaluationResponse> evaluateBoard(@PathVariable("localization") String localization, @Valid @RequestBody BoggleBoard board) throws
            FailureToBuildDictionaryException, LocalizationNotSupportedException {
        final Set dictionary = boggleDictionaryService.getDictionary(localization);
        //TODO - Fix injection here so we don't have to use a setter
        evaluatorService.setDictionary(dictionary);
        //TODO - Fix Validation to use proper Spring Boot Validator
        if (validateBoard(board)) {
            final Set<String> results = evaluatorService.evaluateBoard(board.getBoardLetters());
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new EvaluationResponse(board.getBoardLetters(), results));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new EvaluationResponse(board.getBoardLetters(), new HashSet<>()));
        }
    }

    private boolean validateBoard(final BoggleBoard board) {
        for (List<String> row : board.getBoardLetters()) {
            for (String character : row) {
                if (character.length() != 1) {
                    return false;
                }
            }
        }
        return true;
    }

}
