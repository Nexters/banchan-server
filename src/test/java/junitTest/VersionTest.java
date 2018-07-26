package junitTest;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;

public class VersionTest {

    private Integer junit4Int;
    private Integer junit5Int;

    @org.junit.Before
    public void junit4Setup(){
        junit4Int = 4;
    }

    @org.junit.jupiter.api.BeforeEach
    public void junit5Setup(){
        this.junit5Int = 5;
    }

    @org.junit.Test
    public void junit4(){
        org.junit.Assert.assertThat(junit4Int, is(4));
        org.junit.Assert.assertThat(junit4Int, is(not(5)));
    }

    @org.junit.jupiter.api.Test
    public void junit5(){
        org.junit.jupiter.api.Assertions.assertEquals(5, junit5Int.intValue());
        org.junit.jupiter.api.Assertions.assertNotEquals(4, junit5Int.intValue());
    }
}
