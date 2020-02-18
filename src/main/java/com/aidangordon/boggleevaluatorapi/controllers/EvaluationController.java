package com.aidangordon.boggleevaluatorapi.controllers;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.aidangordon.boggleevaluatorapi.entities.BoggleBoard;
import com.aidangordon.boggleevaluatorapi.entities.EvaluationResponse;
import com.aidangordon.boggleevaluatorapi.exception.FailureToBuildDictionaryException;
import com.aidangordon.boggleevaluatorapi.service.BoggleDictionaryService;
import com.aidangordon.boggleevaluatorapi.service.EvaluatorService;

@CrossOrigin
@RestController
public class EvaluationController {
    @Autowired
    EvaluatorService evaluatorService;

    @Autowired
    BoggleDictionaryService boggleDictionaryService;

    @PostMapping("/evaluations")
    public ResponseEntity<EvaluationResponse> evaluateBoard(@RequestBody BoggleBoard board) throws FailureToBuildDictionaryException {
        final Set dictionary = boggleDictionaryService.getDictionary();
        evaluatorService = new EvaluatorService(dictionary);
        final Set<String> results = evaluatorService.evaluateBoard(convert(board.getBoardLetters()));
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new EvaluationResponse(board.getBoardLetters(), results));
    }

    private char[][] convert(final List<List<String>> boardLetters) {
        char[][] letters = new char[4][4];
        for (int i = 0; i<boardLetters.size(); i++) {
            char[]newRow=new char[4];
            for (int j=0; j<boardLetters.get(i).size();j++) {
                //We assume that none of the strings submitted are a single character
                newRow[j] = boardLetters.get(i).get(j).charAt(0);
            }
            letters[i] = newRow;
        }
        return letters;
    }

}
