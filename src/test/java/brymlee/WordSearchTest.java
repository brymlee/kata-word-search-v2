package brymlee;

import org.junit.Test;
import java.io.IOException;
import java.util.List;

import static org.junit.Assert.*;
import static brymlee.WordSearchService.*;

public class WordSearchTest{

    @Test
    public void javaResourcesAreAbleToBeAccessed() throws IOException{
        assertTrue(getFileAsStringFromPackage(getClass(), "word-search-0.txt").contains("BONES"));
    }

    @Test
    public void canWordSearcherServiceFindWordAlongPositiveYAxis() throws IOException{
        final List<ResultLine> resultLines = WordSearchService
            .create(getClass(), "word-search-0.txt")
            .toResultLines();
        final ResultLine bonesResultLine = resultLines.get(0);
        assertEquals("BONES", bonesResultLine.word().toString());
        assertTrue(bonesResultLine.hasCoordinates(0,6,0,7,0,8,0,9,0,10));
    }

    @Test
    public void canWordSearcherServiceFindWordAlongPositiveXAxis() throws IOException{
        final List<ResultLine> resultLines = WordSearchService
            .create(getClass(), "word-search-0.txt")
            .toResultLines();
        final ResultLine scottyResultLine = resultLines.get(0);
        assertEquals("SCOTTY", scottyResultLine.word().toString());
        assertTrue(scottyResultLine.hasCoordinates(0,5,1,5,2,5,3,5,4,5,5,5));
    }

}