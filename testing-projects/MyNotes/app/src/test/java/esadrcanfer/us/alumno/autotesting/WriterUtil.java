package esadrcanfer.us.alumno.autotesting;


import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;



public class WriterUtil {
	private File logFile;

	public WriterUtil() {
		String timeLog = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
		String filename = "TestCase-" + timeLog+".txt";
		this.logFile = new File(filename);
	}

	public File getLogFile() {
		return logFile;
	}

	public void write(String text) {
		try {
			FileWriter fos = new FileWriter(getLogFile(), true);
			fos.write(text.toString());
			fos.append("\n");
			fos.close();

		} catch (FileNotFoundException e){
			e.printStackTrace();
		} catch (IOException e){
			e.printStackTrace();
		}
	}

	public String getPath() {
		String path = "";
		try {
			path = logFile.getAbsolutePath();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return path;
	}
}
