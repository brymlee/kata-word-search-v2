package brymlee;

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
    
    public Boolean hasCoordinates(final Integer ... coordinates){
        checkArgument(coordinates.length % 2 == 0, "Coordinates passed into hasCoordinates method must be divisible by two because the method is binding both x and y.");
        final List<Coordinate> expectedCoordinates = range(0, coordinates.length)
            .filter(index -> index % 2 != 0)
            .mapToObj(index -> Coordinate.create(coordinates[index - 1], coordinates[index]))
            .collect(toList());
        if(expectedCoordinates.size() != this.coordinates.size()){
            return false;
        }else{
            return range(0, expectedCoordinates.size())
                .allMatch(index -> {
                    final Coordinate expectedCoordinate = expectedCoordinates.get(index);
                    final Coordinate actualCoordinate = this.coordinates.get(index);
                    return expectedCoordinate.equals(actualCoordinate);
                });
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

}