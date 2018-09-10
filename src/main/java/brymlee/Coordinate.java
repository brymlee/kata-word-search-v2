package brymlee;

import org.apache.commons.lang3.builder.EqualsBuilder;

public class Coordinate{
    private Integer x;
    private Integer y;

    public static Coordinate create(final Integer x,
                                    final Integer y){
        return new Coordinate().x(x).y(y);
    }

    private Coordinate(){

    }

    public Coordinate x(final Integer x){
        this.x = x;
        return this;
    }

    public Integer x(){
        return this.x;
    }

    public Coordinate y(final Integer y){
        this.y = y;
        return this;
    }

    public Integer y(){
        return this.y;
    }

    @Override
    public String toString(){
        return "(" + x + "," + y + ")";
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
}