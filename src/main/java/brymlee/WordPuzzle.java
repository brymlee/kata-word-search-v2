package brymlee;

import java.util.List;
import com.google.common.collect.ImmutableList;
import java.util.Optional;

import static java.util.stream.Collectors.*;
import static java.util.stream.IntStream.*;
import static java.util.Arrays.*;

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

    public Boolean contains(final Word word){
        return this.wordPuzzleLines
            .stream() 
            .map(wordPuzzleLine -> wordPuzzleLine.replaceAll(",", ""))
            .anyMatch(nonDelimitedPuzzleLine -> nonDelimitedPuzzleLine.contains(word.toString()));
    }

    private static List<Coordinate> coordinatesAtLine(final Word word, 
                                                      final List<String> lineSplit, 
                                                      final Integer x,
                                                      final Integer y,
                                                      final List<Coordinate> coordinates,
                                                      final Integer wordIndex){
        if(x == lineSplit.size()){
            return coordinates;
        }else{
            if(word.toString().substring(wordIndex, wordIndex + 1).equals(lineSplit.get(x))){
                final List<Coordinate> newCoordinates = ImmutableList.<Coordinate>builder()
                    .addAll(coordinates)
                    .add(Coordinate.create(x, y))
                    .build();
                if(wordIndex == word.toString().length() - 1){
                    return coordinatesAtLine(word, lineSplit, x + 1, y, newCoordinates, 0); 
                }else if(word.toString().substring(wordIndex + 1, wordIndex + 2).equals(lineSplit.get(x + 1))){
                    return coordinatesAtLine(word, lineSplit, x + 1, y, newCoordinates, wordIndex + 1);
                }else{
                    return coordinatesAtLine(word, lineSplit, x + 1, y, coordinates, 0);
                }
            }else{
                return coordinatesAtLine(word, lineSplit, x + 1, y, coordinates, 0);
            }
        }
    }

    public List<Coordinate> coordinates(final Word word){
        final List<Optional<String>> linesWithWord = this.wordPuzzleLines
            .stream()
            .map(wordPuzzleLine -> wordPuzzleLine.replaceAll(",", "").contains(word.toString()) 
                ? Optional.<String>of(wordPuzzleLine) 
                : Optional.<String>empty())
            .collect(toList());
        final List<Coordinate> coordinates = range(0, linesWithWord.size())
            .filter(y -> linesWithWord.get(y).isPresent())
            .mapToObj(y -> {
                final List<String> letters = asList(linesWithWord.get(y).get().split(","));
                return coordinatesAtLine(word, letters, 0, y, ImmutableList.<Coordinate>of(), 0);
            }).reduce((i, j) -> ImmutableList.<Coordinate>builder().addAll(i).addAll(j).build())
            .get();
        coordinates.forEach(coordinate -> System.out.println(coordinate)); 
        return coordinates;
    }
}