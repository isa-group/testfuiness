package esadrcanfer.us.alumno.autotesting.tests;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import android.content.Context;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.test.InstrumentationRegistry;
import android.util.Log;

import androidx.test.uiautomator.UiDevice;

import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
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
