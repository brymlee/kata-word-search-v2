package brymlee;

import com.google.common.collect.ImmutableList;
import java.io.InputStream;
import java.io.IOException;
import java.util.List;

import static com.google.common.base.Preconditions.*;
import static com.google.common.base.Predicates.*;
import static java.util.stream.IntStream.*;
import static java.util.stream.Collectors.*;
import static java.util.Arrays.*;

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

    public List<ResultLine> toResultLines() throws IOException{
        final String puzzleString = getFileAsStringFromPackage(getClass(), fileName());
        final String[] puzzleLineSplit = puzzleString.split("\n");
        checkState(puzzleLineSplit.length > 0, "The word search puzzle that has been supplied doesn't have a beginning line that lists the words to search for. Therefore this is an incorrect puzzle and will not be solved.");
        final WordLine wordLine = WordLine.create(puzzleLineSplit[0]);
        final List<String> remainingLines = range(1, puzzleLineSplit.length)
            .mapToObj(index -> puzzleLineSplit[index])
            .collect(toList());
        final WordPuzzle wordPuzzle = WordPuzzle.create(remainingLines);
        return wordLine
            .stream()
            .filter(word -> wordPuzzle.contains(word))
            .map(word -> ResultLine.create(word, wordPuzzle.coordinates(word)))
            .collect(toList());
    }

    protected static String getFileAsStringFromPackage(final Class<?> clazz, final String fileName) throws IOException{
        final InputStream inputStream = clazz.getResourceAsStream(fileName);
        final byte[] bytes = new byte[inputStream.available()];
        inputStream.read(bytes);
        inputStream.close();
        return new String(bytes);
    }
}