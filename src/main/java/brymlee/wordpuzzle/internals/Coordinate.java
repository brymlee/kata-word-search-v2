package brymlee.wordpuzzle.internals;

import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;

public class Coordinate implements Comparable<Coordinate>{
    private Integer x;
    private Integer maxX;
    private Integer resetXAt;
    private Integer y;
    private Integer maxY;
    private Integer resetYAt;

    public static Coordinate create(final Integer x,
                                    final Integer maxX,
                                    final Integer y,
                                    final Integer maxY){
        return new Coordinate().x(x).y(y).maxX(maxX).maxY(maxY);                                    
    }
    
    private Coordinate(){

    }

    public Coordinate x(final Integer x){
        this.x = x;
        return this;
    }

    public Coordinate decrementX(){
        this.x--;
        return this;
    }

    public Coordinate incrementX(){
        this.x++;    
        return this;
    }

    public Integer x(){
        return this.x;
    }

    public Coordinate maxX(final Integer maxX){
        this.maxX = maxX;
        return this;
    }

    public Coordinate resetXAt(final Integer resetXAt){
        this.resetXAt = resetXAt;
        return this;
    }

    public Integer resetXAt(){
        return this.resetXAt;
    }

    public Integer maxX(){
        return this.maxX;
    }

    public Coordinate resetYAt(final Integer resetYAt){
        this.resetYAt = resetYAt;
        return this;
    }

    public Integer resetYAt(){
        return this.resetYAt;
    }

    public Coordinate y(final Integer y){
        this.y = y;
        return this;
    }

    public Integer y(){
        return this.y;
    }

    public Coordinate decrementY(){
        this.y--;
        return this;
    }

    public Coordinate incrementY(){
        this.y++;
        return this;
    }

    public Coordinate maxY(final Integer maxY){
        this.maxY = maxY;
        return this;
    }

    public Integer maxY(){
        return this.maxY;
    }

    public Coordinate copy(){
        return new Coordinate()
            .x(this.x())
            .maxX(this.maxX())
            .resetXAt(this.resetXAt())
            .y(this.y())
            .maxY(this.maxY())
            .resetYAt(this.resetYAt());
    }

    @Override
    public String toString(){
        return "(" + this.x() + "," + this.y() + ")";
    }

    @Override
    public boolean equals(final Object object){
        if(!(object instanceof Coordinate)){
            return false;
        }else{
            final Coordinate expectedCoordinate = (Coordinate) object;
            return new EqualsBuilder()
                .append(this.x(), expectedCoordinate.x())
                .append(this.y(), expectedCoordinate.y())
                .build();
        }
    }

    @Override
    public int compareTo(final Coordinate coordinate) {
        if(this.x() < coordinate.x()){
            return -1;
        }else if(this.x() == coordinate.x()){
            if(this.y() < coordinate.y()){
                return -1;
            }else if(this.y() == coordinate.y()){
                return 0;
            }else{
                return 1;
            }
        }else{
            return 1;
        }
    }
}