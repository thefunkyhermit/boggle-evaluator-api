package com.aidangordon.boggleevaluatorapi.entities;

import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class BoggleBoard {
    List<List<String>> boardLetters;

    public BoggleBoard(){}

    public BoggleBoard(List<List<String>> boardLetters) {
        this.boardLetters = boardLetters;
    }

    public List<List<String>> getBoardLetters() {
        return boardLetters;
    }
}
