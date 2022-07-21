package esadrcanfer.us.alumno.autotesting.tests;

import static org.junit.Assert.assertFalse;

import org.junit.Test;

import java.nio.file.NoSuchFileException;

import esadrcanfer.us.alumno.autotesting.util.TextPlainTestsImporter;

public class TextPlainTestsImporterTests {

    @Test
    public void textPlainTestsImporter() throws NoSuchFileException {

        try{
            TextPlainTestsImporter.importFromAssetsDir("");
        }catch (Exception e){
            assertFalse(true);
        }

    }

}
