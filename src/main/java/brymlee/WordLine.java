package brymlee;

import java.util.stream.Stream;

import static java.util.Arrays.*;

public class WordLine{

    private String wordLineString;

    public static WordLine create(final String wordLineString){
        return new WordLine().wordLineString(wordLineString);
    }

    private WordLine(){

    }

    public WordLine wordLineString(final String wordLineString){
        this.wordLineString = wordLineString;
        return this;
    }

    public Stream<Word> stream(){
        return asList(this.wordLineString.split(","))
            .stream()
            .map(string -> Word.create(string));
    }
}