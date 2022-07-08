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

		String timeLog = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
		String fileName = "TestCase-" + timeLog+".txt";
		File testFile = new File("src/main/assets/tests/", fileName);
		testFile.getParentFile().mkdirs();
		try {
			testFile.createNewFile();
		}catch (Exception e){
			Log.e("ISA", "An error while creating the .txt file has occurred.");
			e.printStackTrace();
		}
		this.logFile = testFile;

	}

	public WriterUtil(String baseFileName) {

		String timeLog = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
		String fileName = baseFileName + timeLog+".txt";
		File testFile = new File("src/main/assets/tests/", fileName);
		testFile.getParentFile().mkdirs();
		try {
			testFile.createNewFile();
		}catch (Exception e){
			Log.e("ISA", "An error while creating the .txt file has occurred.");
			e.printStackTrace();
		}
		this.logFile = testFile;

	}

	public WriterUtil(String path, String fileName) {
		String name = fileName+".txt";
		File testFile = new File("src/main/assets/"+path, name);

		if(testFile.getParentFile().mkdirs()==true){
			Log.d("ISA", "New directory successfully created!!");
		}else{
			Log.d("ISA", "Either the directory already exits or it is bad formed");
		}

		try {
			testFile.createNewFile();
		}catch (Exception e){

			Log.d("ISA", "A file with the given name already exists in the specified directory.");
		}
		this.logFile = testFile;
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
