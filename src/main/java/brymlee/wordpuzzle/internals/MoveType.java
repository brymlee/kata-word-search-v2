package brymlee.wordpuzzle.internals;

import java.util.function.Function;

public enum MoveType{
    UP(coordinate -> coordinate.decrementY(),
       coordinate -> coordinate.incrementX().y(coordinate.maxY()),
       puzzle -> coordinate -> coordinate.maxY(puzzle.length - 1).y(coordinate.maxY())),
    UP_RIGHT(coordinate -> coordinate.incrementX().decrementY(),
             coordinate -> resetter(coordinate.resetXAt() <= 0, coordinate, coordinate.resetYAt() - 1, coordinate.maxY(), 0, coordinate.resetXAt() - 1),
             puzzle -> coordinate -> coordinate.maxX(puzzle[0].length - 1).x(coordinate.maxX()).resetXAt(coordinate.maxX()).maxY(puzzle.length - 1).y(coordinate.maxY()).resetYAt(coordinate.maxY())),
    RIGHT(coordinate -> coordinate.incrementX(),
          coordinate -> coordinate.x(0).incrementY(),
          puzzle -> coordinate -> coordinate.x(0)),
    DOWN_RIGHT(coordinate -> coordinate.incrementX().incrementY(),
               coordinate -> resetter(coordinate.resetYAt() <= 0, coordinate, 0, coordinate.resetYAt() - 1, coordinate.resetXAt() + 1, 0),
               puzzle -> coordinate -> coordinate.x(0).maxX(puzzle[0].length - 1).maxY(puzzle.length - 1).resetXAt(0).resetYAt(coordinate.maxY())),
    DOWN(coordinate -> coordinate.incrementY(),
         coordinate -> coordinate.incrementX().y(0),
         puzzle -> coordinate -> coordinate.x(0).y(0)),
    DOWN_LEFT(coordinate -> coordinate.decrementX().incrementY(),
              coordinate -> resetter(coordinate.resetYAt() <= 0, coordinate, 0, coordinate.resetYAt() - 1, coordinate.resetXAt() - 1, coordinate.maxX()),
              puzzle -> coordinate -> coordinate.maxX(puzzle[0].length - 1).x(coordinate.maxX()).maxY(puzzle.length - 1).y(coordinate.maxY()).resetXAt(coordinate.maxX()).resetYAt(coordinate.maxY())),
    LEFT(coordinate -> coordinate.decrementX(),
         coordinate -> coordinate.x(coordinate.maxX()).incrementY(),
         puzzle -> coordinate -> coordinate.maxX(puzzle[0].length - 1).x(coordinate.maxX())),
    UP_LEFT(coordinate -> coordinate.decrementX().decrementY(),
            coordinate -> resetter(coordinate.resetYAt() > coordinate.maxY(), coordinate, coordinate.maxY(), coordinate.resetYAt() + 1, coordinate.resetXAt() - 1, coordinate.maxX()),
            puzzle -> coordinate -> coordinate.maxX(puzzle[0].length - 1).x(coordinate.maxX()).y(0).maxY(puzzle.length - 1).resetXAt(coordinate.maxX()).resetYAt(0));

    private Function<Coordinate, Coordinate> coordinateMover;
    private Function<Coordinate, Coordinate> coordinateReseter;
    private Function<String[][], Function<Coordinate, Coordinate>> coordinateStarter;

    MoveType(final Function<Coordinate, Coordinate> coordinateMover,
             final Function<Coordinate, Coordinate> coordinateReseter,
             final Function<String[][], Function<Coordinate, Coordinate>> coordinateStarter){
        this.coordinateMover = coordinateMover;
        this.coordinateReseter = coordinateReseter;
        this.coordinateStarter = coordinateStarter;
    }

    public static Coordinate resetter(final Boolean expression,
                                      final Coordinate coordinate,
                                      final Integer yResetWhenTrue,
                                      final Integer yResetWhenFalse,
                                      final Integer xResetWhenTrue,
                                      final Integer xResetWhenFalse){
        final Integer newResetYAt = expression
            ? yResetWhenTrue
            : yResetWhenFalse;
        final Integer newResetXAt = expression
            ? xResetWhenTrue
            : xResetWhenFalse;
        return coordinate.x(newResetXAt).resetXAt(newResetXAt).y(newResetYAt).resetYAt(newResetYAt);
    }

    public Function<Coordinate, Coordinate> coordinateMover(){
        return this.coordinateMover;
    }

    public Function<Coordinate, Coordinate> coordinateReseter(){
        return this.coordinateReseter;
    }

    public Function<String[][], Function<Coordinate, Coordinate>> coordinateStarter(){
        return this.coordinateStarter;
    }
}