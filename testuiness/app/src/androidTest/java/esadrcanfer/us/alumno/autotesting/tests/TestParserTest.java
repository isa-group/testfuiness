package esadrcanfer.us.alumno.autotesting.tests;

import org.junit.Test;

import esadrcanfer.us.alumno.autotesting.util.TestParser;

public class TestParserTest {

    @Test
    public void testTextPlainParser(){

        String path = "Test Clock/API 27, 28, 29";

        TestParser.parseTextPlainTest(path, "TestClock", "Clock");

    }

}
