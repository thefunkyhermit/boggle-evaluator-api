package com.aidangordon.boggleevaluatorapi.service;

import java.util.List;
import java.util.Set;
import java.util.HashSet;

import org.springframework.stereotype.Service;

/*
The algorithm for this was heavily influenced by
https://www.geeksforgeeks.org/boggle-find-possible-words-board-characters/
 */
@Service
public class EvaluatorService {
    final int numRows = 4;
    final int numColumns = 4;
    private Set<String> dictionary;

    public EvaluatorService() {
        this.dictionary = new HashSet<>();
    }

    public EvaluatorService(Set dictionary) {
        this.dictionary = dictionary;
    }

    //TODO - this hurts my heard to add this but I didn't have time to figure out how to properly inject this with Autowiring.
    //I avoid mutable classes as much as possible (I should have just used old fashioned constructor injection instead of autowiring?
    public void setDictionary(final Set dictionary) {
        this.dictionary = dictionary;
    }

    /**
     * Accepts a double list of characters and finds all matching words in the dictionary
     *
     * @return A set of all matching words for the submitted boggle board
     */
    public Set<String> evaluateBoard(final List<List<String>> boggleBoard) {
        Set<String> wordsOnBoard = findWords(convert(boggleBoard));
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

    private char[][] convert(final List<List<String>> boardLetters) {
        char[][] letters = new char[4][4];
        for (int i = 0; i < boardLetters.size(); i++) {
            char[] newRow = new char[4];
            for (int j = 0; j < boardLetters.get(i).size(); j++) {
                //We assume that none of the strings submitted are a single character
                newRow[j] = boardLetters.get(i).get(j).charAt(0);
            }
            letters[i] = newRow;
        }
        return letters;
    }

}
