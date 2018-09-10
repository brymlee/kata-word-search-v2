package brymlee.wordpuzzle;

import org.junit.Test;
import java.io.IOException;

import static org.junit.Assert.*;
import static brymlee.wordpuzzle.WordSearchService.*;

public class WordSearchTest{
    @Test
    public void javaResourcesAreAbleToBeAccessed() throws IOException{
        assertTrue(getFileAsStringFromPackage(getClass(), "word-search-0.txt").contains("BONES"));
    }

    private static void wordSearchAssertion(final String fileName,
                                            final Word word,
                                            final Integer ... coordinates) throws IOException {
        final Results results = WordSearchService
            .create(WordSearchTest.class, fileName)
            .toResults();
        assertTrue(results.hasWord(word));
        assertTrue(results.withWord(word).hasCoordinates(coordinates));
    }

    private static void wordSearchAssertion(final Results results,
                                            final Word word,
                                            final Integer ... coordinates){
        assertTrue(results.hasWord(word));
        assertTrue(results.withWord(word).hasCoordinates(coordinates));
    }

    @Test
    public void canWordSearcherServiceFindWordUp() throws IOException{
        wordSearchAssertion("word-search-0.txt", Word.create("KHAN"),5,9,5,8,5,7,5,6);
    }

    @Test
    public void canWordSearcherServiceFindWordRight() throws IOException{
        wordSearchAssertion("word-search-0.txt", Word.create("SCOTTY"), 0,5,1,5,2,5,3,5,4,5,5,5);
    }

    @Test
    public void canWordSearcherServiceFindWordDown() throws IOException{
        wordSearchAssertion("word-search-0.txt", Word.create("BONES"), 0,6,0,7,0,8,0,9,0,10);
    }

    @Test
    public void canWordSearcherServiceFindWordLeft() throws IOException{
        wordSearchAssertion("word-search-0.txt", Word.create("KIRK"), 4,7,3,7,2,7,1,7);
    }

    @Test
    public void canWordSearcherServiceFindWordUpLeft() throws IOException{
        wordSearchAssertion("word-search-0.txt", Word.create("SULU"), 3,3,2,2,1,1,0,0);
    }

    @Test
    public void canWordSearcherServiceFindWordDownRight() throws IOException{
        wordSearchAssertion("word-search-0.txt", Word.create("SPOCK"), 2,1,3,2,4,3,5,4,6,5);
    }

    @Test
    public void canWordSearcherServiceFindWordDownLeft() throws IOException{
        wordSearchAssertion("word-search-0.txt", Word.create("UHURA"), 4,0,3,1,2,2,1,3,0,4);
    }

    @Test
    public void canWordSearcherServiceFindWordUpRight() throws IOException{
        wordSearchAssertion("word-search-1.txt", Word.create("UP"), 1,1,2,0);
    }

    @Test
    public void canWordSearchCliApplicationSolvePuzzleWordSearch0(){
        final Results results = WordSearchCliApplication.runPuzzleSolver(getClass(), new String[]{"word-search-0.txt"}, false);
        wordSearchAssertion(results, Word.create("BONES"), 0,6,0,7,0,8,0,9,0,10);
        wordSearchAssertion(results, Word.create("KHAN"), 5,9,5,8,5,7,5,6);
        wordSearchAssertion(results, Word.create("KIRK"), 4,7,3,7,2,7,1,7);
        wordSearchAssertion(results, Word.create("SCOTTY"), 0,5,1,5,2,5,3,5,4,5,5,5);
        wordSearchAssertion(results, Word.create("SPOCK"), 2,1,3,2,4,3,5,4,6,5);
        wordSearchAssertion(results, Word.create("SULU"), 3,3,2,2,1,1,0,0);
        wordSearchAssertion(results, Word.create("UHURA"), 4,0,3,1,2,2,1,3,0,4);
    }

    @Test
    public void canWordSearchCliApplicationSolvePuzzleWordSearch1(){
        final Results results = WordSearchCliApplication.runPuzzleSolver(getClass(), new String[]{"word-search-2.txt"}, false);
        wordSearchAssertion(results, Word.create("BARKER"), 12,0,11,1,10,2,9,3,8,4,7,5);
        wordSearchAssertion(results, Word.create("BOB"), 11,12,11,13,11,14,12,12,13,11,14,10);
        wordSearchAssertion(results, Word.create("HANKS"), 12,3,11,3,10,3,9,3,8,3);
        wordSearchAssertion(results, Word.create("PAM"), 7,6,8,6,9,6);
        wordSearchAssertion(results, Word.create("RANDY"), 10,10,9,9,8,8,7,7,6,6);
        wordSearchAssertion(results, Word.create("TOM"), 3,13,4,12,5,11);
    }
}