package brymlee;

public class Word{

    private String string;

    public static Word create(final String string){
        return new Word().string(string);
    }

    private Word(){

    }

    public Word string(final String string){
        this.string = string.toUpperCase();
        return this;
    }

    @Override
    public String toString(){
        return this.string; 
    }
}