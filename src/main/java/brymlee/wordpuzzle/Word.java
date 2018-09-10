package brymlee.wordpuzzle;

import org.apache.commons.lang3.builder.EqualsBuilder;

public class Word{
    private String string;

    public static Word create(final String string){
        return new Word().string(string.trim());
    }

    private Word(){

    }

    public Word string(final String string){
        this.string = string.toUpperCase();
        return this;
    }

    public String string(){
        return this.string;
    }

    @Override
    public String toString(){
        return this.string;
    }

    @Override
    public boolean equals(final Object object){
        if(!(object instanceof Word)){
            return false;
        }else{
            final Word expectedWord = (Word) object;
            return new EqualsBuilder()
                .append(expectedWord.string(), this.string())
                .build();
        }
    }
}