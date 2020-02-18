package com.aidangordon.boggleevaluatorapi.entities;

import java.util.List;
import java.util.Set;

public class EvaluationResponse {
    private List<List<String>> board;
    private final Set<String> results;

    public EvaluationResponse(final List<List<String>> board, final Set<String> results) {
        this.board = board;
        this.results = results;
    }

    public List<List<String>> getBoard() {
        return board;
    }

    public Set<String> getResults() {
        return results;
    }
}
