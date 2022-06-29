package esadrcanfer.us.alumno.autotesting.util;


import esadrcanfer.us.alumno.autotesting.TestCase;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import esadrcanfer.us.alumno.autotesting.inagraph.actions.Action;

public class WriterUtil {
	private File logFile;

	public WriterUtil(){
		this("TestCase-");
	}

	public WriterUtil(String basefilename) {
		String timeLog = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
		String filename = basefilename + timeLog+".txt";
		this.logFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), filename);
	}
	
	public File getLogFile() {
		return logFile;
	}

	public void write(TestCase testCase, Long seed){
		write(testCase.getAppPackage());
		write(String.valueOf(seed));
		write(String.valueOf(testCase.getTestActions().size()));
		for (Action action : testCase.getTestActions()) {
			write(action.toString());
		}
		write(testCase.getPredicate().toString());
	}

	public void write(String text) {
		try {
			if(!getLogFile().exists())
				getLogFile().createNewFile();
			FileWriter fos = new FileWriter(getLogFile(), true);
			fos.write(text.toString());
			fos.append("\n");
			fos.close();
			Log.d("TFG", "Saved!");

		} catch (FileNotFoundException e){
			e.printStackTrace();
			Log.d("TFG", "File not found!");
		} catch (IOException e){
			e.printStackTrace();
			Log.d("TFG", "Error saving!");
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
