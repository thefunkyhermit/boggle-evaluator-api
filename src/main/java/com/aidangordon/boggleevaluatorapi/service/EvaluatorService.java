package com.aidangordon.boggleevaluatorapi.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.HashSet;

import org.springframework.stereotype.Service;

@Service
public class EvaluatorService {
    final int numRows = 4;
    final int numColumns = 4;
    private final Set<String> dictionary;

    public EvaluatorService(Set dictionary) {
        this.dictionary = dictionary;
    }

    public Set<String> evaluateBoard(final char[][] boggleBoard) {
        Set<String> wordsOnBoard = findWords(boggleBoard);
        return wordsOnBoard;
    }

    // A recursive function to print all words present on boggle
    private Set<String> findWordsUtil(char boggle[][], boolean visited[][], int currentRow,
                              int currentColumn, String currentString) {
        // Mark current cell as visited and append current character
        // to currentString
        visited[currentRow][currentColumn] = true;
        currentString = currentString + boggle[currentRow][currentColumn];
        Set<String> wordsOnBoard = new HashSet<>();
        // If currentString is present in dictionary, then print it
        if (isWord(currentString))
            wordsOnBoard.add(currentString.toLowerCase());

        // Traverse 8 adjacent cells of boggle[currentRow][currentColumn]
        for (int row = currentRow - 1; row <= currentRow + 1 && row < numRows; row++) {
            for (int col = currentColumn - 1; col <= currentColumn + 1 && col < numColumns; col++) {
                if (row >= 0 && col >= 0 && !visited[row][col]) {
                    final Set<String> wordsUtil = findWordsUtil(boggle, visited, row, col, currentString);
                    wordsOnBoard.addAll(wordsUtil);
                }
            }
        }
        // Mark visited of current cell as false
        visited[currentRow][currentColumn] = false;
        return wordsOnBoard;
    }

    private Set<String> findWords(char[][] boggle) {

        // Mark all characters as not visited
        boolean visited[][] = new boolean[numRows][numColumns];

        Set<String> wordsOnBoard = new HashSet<>();
        // Initialize current string
        String currentString = "";

        // Consider every character and look for all words
        // starting with this character
        for (int i = 0; i < numRows; i++)
            for (int j = 0; j < numColumns; j++)
                wordsOnBoard.addAll(findWordsUtil(boggle, visited, i, j, currentString));

        return wordsOnBoard;
    }

    private boolean isWord(final String currentString) {
        boolean isWord = false;
        if (dictionary.contains(currentString.toLowerCase())) {
            isWord = true;
        }
        return isWord;
    }
}
