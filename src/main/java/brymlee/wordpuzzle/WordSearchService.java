package brymlee.wordpuzzle;

import brymlee.wordpuzzle.internals.Coordinate;
import brymlee.wordpuzzle.internals.ResultLine;
import brymlee.wordpuzzle.internals.WordLine;
import brymlee.wordpuzzle.internals.WordPuzzle;
import java.io.InputStream;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

import static com.google.common.base.Preconditions.*;
import static java.util.stream.IntStream.*;
import static java.util.stream.Collectors.*;

public class WordSearchService{
    private Class<?> clazz;
    private String fileName;

    public static WordSearchService create(final Class<?> clazz, final String fileName){
        return new WordSearchService()
            .clazz(clazz)
            .fileName(fileName);
    }

    private WordSearchService(){

    }

    public WordSearchService clazz(final Class<?> clazz){
        this.clazz = clazz;
        return this;
    }
    
    public WordSearchService fileName(final String fileName){
        this.fileName = fileName;
        return this;
    }

    public String fileName(){
        return this.fileName;
    }

    private static Predicate<Object> isWordPalindrome(final Word word){
        final Integer wordLength = word.toString().length();
        return object -> {
            if(wordLength == 0){
                return false;
            }else if(wordLength >= 2
                  && word.toString().substring(0, 1).equals(word.toString().substring(wordLength - 1, wordLength))){
                return true;
            }else{
                return false;
            }
        };
    }

    public Results toResults() throws IOException{
        final String puzzleString = getFileAsStringFromPackage(getClass(), fileName());
        final String[] puzzleLineSplit = puzzleString.split("\n");
        checkState(puzzleLineSplit.length > 0, "The word search puzzle that has been supplied doesn't have a beginning line that lists the words to search for. Therefore this is and incorrect puzzle and will not be solved.");
        final WordLine wordLine = WordLine.create(puzzleLineSplit[0]);
        final List<String> remainingLines = range(1, puzzleLineSplit.length)
            .mapToObj(index -> puzzleLineSplit[index])
            .collect(toList());
        final WordPuzzle wordPuzzle = WordPuzzle.create(remainingLines);
        final List<ResultLine> resultLines = wordLine
            .stream()
            .map(word -> ResultLine.create(word, wordPuzzle.coordinates(word).collect(toList())))
            .map(WordSearchService::eleminatedDuplicatesDueToPalindrome)
            .collect(toList());
        return Results.create(resultLines);
    }

    private static ResultLine eleminatedDuplicatesDueToPalindrome(final ResultLine resultLine){
        final List<Coordinate> coordinates = resultLine
                .coordinates()
                .stream()
                .filter(isWordPalindrome(resultLine.word()))
                .sorted()
                .collect(toList());
        if(coordinates.size() > 0
                && coordinates.size() % 2 == 0
                && coordinates.size() != resultLine.word().toString().length()){
            final List<Coordinate> newCoordinatesToBeRemoved = range(0, coordinates.size())
                    .mapToObj(index -> index % 2 == 0 ? coordinates.get(index) : null)
                    .filter(coordinate -> coordinate != null)
                    .collect(toList());
            return ResultLine.create(resultLine.word(), newCoordinatesToBeRemoved);
        }else{
            return resultLine;
        }
    }

    public static String getFileAsStringFromPackage(final Class<?> clazz, final String fileName) throws IOException{
        final InputStream inputStream = clazz.getResourceAsStream(fileName);
        final byte[] bytes = new byte[inputStream.available()];
        inputStream.read(bytes);
        inputStream.close();
        return new String(bytes);
    }
}