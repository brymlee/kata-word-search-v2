package brymlee.wordpuzzle.internals;

import brymlee.wordpuzzle.Word;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;
import static java.util.stream.IntStream.*;
import static java.util.Arrays.*;
import static com.google.common.collect.FluentIterable.*;

public class WordPuzzle{

    private List<String> wordPuzzleLines;

    public static WordPuzzle create(final List<String> wordPuzzleLines){
        return new WordPuzzle().wordPuzzleLines(wordPuzzleLines);
    }

    private WordPuzzle(){

    }

    public WordPuzzle wordPuzzleLines(final List<String> wordPuzzleLines){
        this.wordPuzzleLines = wordPuzzleLines;
        return this;
    }

    private static Stream<Coordinate> moveTypeToWordCoordinates(final Word word, final String[][] puzzle, final MoveType moveType){
        return WordPuzzleSolver
            .create(word, puzzle)
            .moveType(moveType)
            .doStart()
            .solve()
            .foundCoordinates();
    }

    protected static Stream<Coordinate> concatenateStreams(final Stream<Coordinate> i, final Stream<Coordinate> j){
        return WordPuzzleSolver.concat(i, j);
    }

    private static Stream<Coordinate> solvePuzzleForWordCoordinates(final Word word, final String[][] puzzle){
        return asList(MoveType.values())
            .stream()
            .map(moveType -> moveTypeToWordCoordinates(word, puzzle, moveType))
            .reduce(WordPuzzle::concatenateStreams)
            .get();
    }

    public Stream<Coordinate> coordinates(final Word word){
        final List<String[]> listPuzzle = range(0, this.wordPuzzleLines.size())
            .mapToObj(y -> this.wordPuzzleLines.get(y).trim().split(","))
            .collect(toList());
        final String[][] puzzle = from(listPuzzle).toArray(String[].class);
        return solvePuzzleForWordCoordinates(word, puzzle);
    }
}