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
import java.util.concurrent.TimeUnit;

import esadrcanfer.us.alumno.autotesting.inagraph.actions.Action;

public class WriterUtil {
	private File logFile;

	public WriterUtil(){

		File assets = new File("src/main/assets");

		String timeLog = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
		String fileName = "TestCase-" + timeLog+".txt";
		File testFile = null;

		if(assets.exists()){
			testFile = new File("src/main/assets/tests/", fileName);
		}else{
			testFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath()+"/repairedTests", fileName);
		}

		if(testFile.getParentFile().mkdirs()==true){
			Log.d("ISA", "New directory successfully created!!");
		}else{
			Log.d("ISA", "Either the directory already exits or it is bad formed");
		}

		try {
			testFile.createNewFile();
		}catch (Exception e){
			Log.e("ISA", "A file with the given name already exists in the specified directory.");
			e.printStackTrace();
		}
		this.logFile = testFile;

	}

	public WriterUtil(String baseFileName) {

		File assets = new File("src/main/assets");

		String timeLog = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
		String fileName = baseFileName + timeLog+".txt";
		File testFile = null;

		if(assets.exists()){
			testFile = new File("src/main/assets/tests/", fileName);
		}else{
			testFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath()+"/repairedTests", fileName);
		}

		if(testFile.getParentFile().mkdirs()==true){
			Log.d("ISA", "New directory successfully created!!");
		}else{
			Log.d("ISA", "Either the directory already exits or it is bad formed");
		}

		try {
			testFile.createNewFile();
		}catch (Exception e){
			Log.e("ISA", "A file with the given name already exists in the specified directory.");
			e.printStackTrace();
		}
		this.logFile = testFile;

	}

	public WriterUtil(String path, String fileName) {

		File assets = new File("src/main/assets");

		String name = fileName+".txt";

		File testFile = null;

		if(assets.exists()){
			testFile = new File("src/main/assets/"+path, name);
		}else{
			testFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath()+path, fileName);
		}

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

	public static void saveInDevice(TestCase testCase, Long seed, String fileName, Long reparationTime, String checkpointId){

		String timeLog = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
		WriterUtil writer = new WriterUtil("/repairedTests", fileName+"-"+timeLog+".txt");
		WriterUtil dataMetrics = new WriterUtil("/repairedTests", "dataMetrics.csv");
		WriterUtil checkpointList = new WriterUtil("/repairedTests", "checkpointList.txt");
		writer.write(testCase, (long) seed);
		if(reparationTime != null){
			int seconds = (int) (reparationTime / 1000) % 60 ;
			int minutes = (int) ((reparationTime / (1000*60)) % 60);
			int hours   = (int) ((reparationTime / (1000*60*60)) % 24);


			dataMetrics.write(String.format(fileName+";%d h %d min %d sec", hours, minutes, seconds));
		}

		if(checkpointId!= null) checkpointList.deleteCheckpoint(checkpointId);

	}

	private void deleteCheckpoint(String checkpointId){

		ReadUtil reader = new ReadUtil(this.getPath());
		String fileText = reader.readText();
		String[] fileTextSplitted = fileText.split("\n");
		String filePath = this.getPath();

		String parentPath = this.getLogFile().getParent().split("Download")[1];

		WriterUtil newFileWriter = new WriterUtil(parentPath, "temp." + filePath.split("\\.")[1]);

		for(String line: fileTextSplitted){

			if(!hasId(line, checkpointId)){

				newFileWriter.write(line);
			}

		}

		File oldFile = this.getLogFile();
		oldFile.delete();
		File newFile = newFileWriter.getLogFile();
		newFile.renameTo(new File(filePath));

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
	
	private static Boolean hasId(String line, String id){
		return line.split(";")[0].equals(id);
	}
	

}
