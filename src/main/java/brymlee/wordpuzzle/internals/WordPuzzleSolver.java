package brymlee.wordpuzzle.internals;

import brymlee.wordpuzzle.Word;
import java.util.List;
import java.util.stream.Stream;
import com.google.common.collect.ImmutableList;

import static java.util.stream.IntStream.*;
import static java.util.Arrays.*;

public class WordPuzzleSolver{
    private Word word;
    private String[][] puzzle;
    private Coordinate currentCoordinate;
    private MoveType moveType;
    private Stream<Coordinate> foundCoordinates;
    private Stream<Coordinate> stagingFoundCoordinates;
    private String wordBuffer;

    public static WordPuzzleSolver create(final Word word, final String[][] puzzle){
        return new WordPuzzleSolver()
            .word(word)
            .puzzle(puzzle)
            .solved(false)
            .wordBuffer("")
            .stagingFoundCoordinates(Stream.<Coordinate>of())
            .foundCoordinates(Stream.<Coordinate>of());
    }

    private WordPuzzleSolver(){

    }

    public WordPuzzleSolver wordBuffer(final String wordBuffer){
        this.wordBuffer = wordBuffer;
        return this;
    }

    public String wordBuffer(){
        return this.wordBuffer;
    }

    public WordPuzzleSolver solved(final Boolean solved){
        return this;
    }

    public WordPuzzleSolver word(final Word word){
        this.word = word;
        return this;
    }

    public Word word(){
        return this.word;
    }

    public WordPuzzleSolver puzzle(final String[][] puzzle){
        this.puzzle = puzzle;
        return this;
    }

    public String[][] puzzle(){
        return this.puzzle;
    }

    public WordPuzzleSolver currentCoordinate(final Coordinate currentCoordinate){
        this.currentCoordinate = currentCoordinate;
        return this;
    }

    public Coordinate currentCoordinate(){
        return this.currentCoordinate;
    }

    public WordPuzzleSolver moveType(final MoveType moveType){
        this.moveType = moveType;
        return this;
    }

    public MoveType moveType(){
        return this.moveType;
    }

    public WordPuzzleSolver move(){
        final Coordinate movedCoordinate = this.moveType().coordinateMover().apply(this.currentCoordinate().copy());
        return this.currentCoordinate(movedCoordinate);
    }

    public WordPuzzleSolver moveReset(){
        final Coordinate resetCoordinate = this.moveType().coordinateReseter().apply(this.currentCoordinate().copy());
        this.currentCoordinate(resetCoordinate).letter();
        return this;
    }

    public WordPuzzleSolver doStart(){
        this.currentCoordinate = this.moveType().coordinateStarter().apply(this.puzzle()).apply(Coordinate.create(0, 0, 0, 0));
        return this;
    }

    private static Boolean isBufferSubsetOfWord(final Word word, final String wordBuffer){
        return range(0, wordBuffer.length())
            .allMatch(index -> wordBuffer.substring(index, index + 1).equals(word.toString().substring(index, index + 1)));
    }

    public WordPuzzleSolver solve(){
        try{
            return this.solveRecursive();
        }catch(final ArrayIndexOutOfBoundsException exceptionToTriggerReset){
            try{
                return this.moveReset().wordBuffer("").stagingFoundCoordinates(Stream.<Coordinate>of()).solveRecursive();
            }catch(final ArrayIndexOutOfBoundsException exceptionToTriggerSolved){
                return this; 
            }
        }
    }

    protected static Stream<Coordinate> concat(final Coordinate coordinate, final Stream<Coordinate> ... coordinates){
        return Stream.concat(concat(coordinates), Stream.<Coordinate>of(coordinate));
    }

    private static Stream<Coordinate> concatenateStreams(final Stream<Coordinate> i, final Stream<Coordinate> j){
        return Stream.concat(i, j);
    }

    protected static Stream<Coordinate> concat(final Stream<Coordinate> ... coordinates){
        return asList(coordinates)
            .stream()
            .reduce(WordPuzzleSolver::concatenateStreams)
            .get();
    }

    private WordPuzzleSolver solveRecursive(){
        final String newWordBuffer = this.wordBuffer() + this.letter();
        if(this.word().toString().equals(newWordBuffer)){
            final Stream<Coordinate> newFoundCoordinates = concat(this.currentCoordinate(), this.foundCoordinates(), this.stagingFoundCoordinates());
            return this.wordBuffer("")
                .stagingFoundCoordinates(Stream.<Coordinate>of())
                .foundCoordinates(newFoundCoordinates)
                .move()
                .solve();
        }else if(isBufferSubsetOfWord(this.word(), newWordBuffer)){
            final Stream<Coordinate> newStagingFoundCoordinates = concat(this.currentCoordinate(), this.stagingFoundCoordinates());
            return this.wordBuffer(newWordBuffer)
                .stagingFoundCoordinates(newStagingFoundCoordinates)
                .move()
                .solve();
        }else{
            return this
                .wordBuffer("")
                .stagingFoundCoordinates(Stream.<Coordinate>of())
                .move()
                .solve();
        }
    }

    public WordPuzzleSolver foundCoordinates(final Stream<Coordinate> foundCoordinates){
        this.foundCoordinates = foundCoordinates;
        return this;
    }

    public Stream<Coordinate> foundCoordinates(){
        return this.foundCoordinates;
    }

    public WordPuzzleSolver stagingFoundCoordinates(final Stream<Coordinate> stagingFoundCoordinates){
        this.stagingFoundCoordinates = stagingFoundCoordinates;
        return this;
    }

    public Stream<Coordinate> stagingFoundCoordinates(){
        return this.stagingFoundCoordinates;
    }

    public String letter(){
        return puzzle[this.currentCoordinate.y()][this.currentCoordinate.x()];
    }
}