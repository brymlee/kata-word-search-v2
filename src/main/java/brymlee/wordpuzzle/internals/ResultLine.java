package brymlee.wordpuzzle.internals;

import brymlee.wordpuzzle.Word;
import java.util.List;

import static com.google.common.base.Preconditions.*;
import static java.util.stream.IntStream.*;
import static java.util.stream.Collectors.*;

public class ResultLine{
    private Word word;
    private List<Coordinate> coordinates;

    public static ResultLine create(final Word word, final List<Coordinate> coordinates){
        return new ResultLine().word(word).coordinates(coordinates);
    }

    private ResultLine(){

    }

    public Word word(){
        return this.word;
    }

    private static Boolean whereIndexIsEven(final Integer index){
        return index % 2 != 0;
    }

    public Boolean hasCoordinates(final Integer ... coordinates){
        checkArgument(coordinates.length % 2 == 0, "Coordinates passed into hasCoordinates method must be divisible by two because the method is binding both x and y.");
        final List<Coordinate> expectedCoordinates = range(0, coordinates.length)
            .filter(ResultLine::whereIndexIsEven)
            .mapToObj(index -> Coordinate.create(coordinates[index - 1], 0, coordinates[index], 0))
            .collect(toList());
        if(expectedCoordinates.size() != this.coordinates.size()){
            return false;
        }else{
            return range(0, expectedCoordinates.size())
                .allMatch(index -> expectedCoordinates.get(index).equals(this.coordinates.get(index)));
        }
    }

    public ResultLine word(final Word word){
        this.word = word;
        return this;
    }

    public ResultLine coordinates(final List<Coordinate> coordinates){
        this.coordinates = coordinates;
        return this;
    }

    public List<Coordinate> coordinates(){
        return this.coordinates;
    }

    private static String coordinateToString(final Coordinate coordinate){
        return coordinate.toString();
    }
    
    private static String concatenateStrings(final String i, final String j){
        return i + "," + j;
    }

    @Override
    public String toString(){
        if(this.coordinates.size() == 0){
            return this.word() + ":";
        }else{
            return this.word() + ": " + this.coordinates()
                .stream()
                .map(ResultLine::coordinateToString)
                .reduce(ResultLine::concatenateStrings)
                .get();
        }
    }
    
}