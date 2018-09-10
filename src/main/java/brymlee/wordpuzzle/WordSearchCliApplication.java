package brymlee.wordpuzzle;

import java.io.IOException;
import java.util.function.Consumer;
import java.util.function.Function;

public class WordSearchCliApplication {

    public static Results runPuzzleSolver(final Class<?> clazz,
                                          final String[] arguments,
                                          final Boolean shouldPrintToStandardOut){
        final Consumer<Results> doPrintToStandardOut = results -> results
            .resultLines()
            .stream()
            .filter(resultLine -> shouldPrintToStandardOut)
            .forEach(System.out::println);
        final Function<String, Results> toResults = fileName -> {
            try{
                final Results results = WordSearchService.create(clazz, fileName).toResults();
                doPrintToStandardOut.accept(results);
                return results;
            }catch(final Exception exception){
                throw new RuntimeException(exception);
            }
        };
        switch(arguments.length){
            case 0 : {
                return toResults.apply("word-search-0.txt");
            }case 1: {
                return toResults.apply(arguments[0]);
            }default:
                throw new RuntimeException("WordSearchCliApplication must take either zero or one parameter. In this case the parameters supplied are not compliant to that rule.");
        }
    }

    private WordSearchCliApplication(){

    }

    public static void main(final String[] arguments){
        runPuzzleSolver(WordSearchCliApplication.class, arguments, true);
    }
}
