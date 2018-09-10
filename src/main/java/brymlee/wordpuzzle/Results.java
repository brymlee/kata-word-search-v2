package brymlee.wordpuzzle;

import brymlee.wordpuzzle.internals.ResultLine;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;

public class Results{
    private List<ResultLine> resultLines;

    public static Results create(final List<ResultLine> resultLines){
        return new Results().resultLines(resultLines);
    }

    private Results(){

    }

    public static Results create(String s) {
        return null;
    }

    public Results resultLines(final List<ResultLine> resultLines){
        this.resultLines = resultLines;
        return this;
    }

    public List<ResultLine> resultLines(){
        return this.resultLines;
    }

    private static Predicate<ResultLine> wordEqualsWord(final Word word){
        return resultLine -> resultLine.word().equals(word);
    }

    private static Boolean areThereAnyCoordinates(final ResultLine resultLine){
        return resultLine.coordinates().size() > 0;
    }

    private static Stream<ResultLine> hasWord(final List<ResultLine> resultLines, final Word word){
        return resultLines
            .stream()
            .filter(wordEqualsWord(word))
            .filter(Results::areThereAnyCoordinates);
    }

    public Boolean hasWord(final Word word){
        return hasWord(this.resultLines, word).count() > 0;
    }

    public Results withWord(final Word word){
        return create(hasWord(this.resultLines, word).collect(toList()));
    }

    public Boolean hasCoordinates(final Integer ... coordinates){
        final ResultLine resultLine = this.resultLines.stream().findAny().get();
        return resultLine.hasCoordinates(coordinates);
    }
}