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
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
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
			testFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath()+"/reparation_experiment", fileName);
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
		String fileName = baseFileName+".txt";
		File testFile = null;

		if(assets.exists()){
			testFile = new File("src/main/assets/tests/", fileName);
		}else{
			testFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath()+"/reparation_experiment/old_versions_data", fileName);
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

	public WriterUtil(Path filePath) {

		File assets = new File("src/main/assets");

		File testFile = null;

		if(assets.exists()){
			testFile = new File("src/main/assets", filePath.toString());
		}else{
			testFile = new File(filePath.toUri());
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
			testFile = new File(path, fileName);
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

	public static void saveInDevice(TestCase testCase, Long seed, String fileName, Long reparationTime, String checkpointId, String algorithm, String experimentPath){

		String downloadsPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath();
		String timeLog = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
		WriterUtil writer = new WriterUtil(downloadsPath+"/"+ experimentPath +"/repairedTests", fileName+"-"+algorithm+"-"+timeLog+".txt");
		WriterUtil dataMetrics = new WriterUtil(downloadsPath+"/"+ experimentPath, "dataMetrics.csv");
		CheckpointUtil checkpointList = new CheckpointUtil(downloadsPath+"/"+ experimentPath);
		writer.write(testCase, (long) seed);
		if(reparationTime != null){
			int seconds = (int) (reparationTime / 1000) % 60 ;
			int minutes = (int) ((reparationTime / (1000*60)) % 60);
			int hours   = (int) ((reparationTime / (1000*60*60)) % 24);


			dataMetrics.write(String.format(fileName+";"+algorithm+";%d h %d min %d sec;%s", hours, minutes, seconds, algorithm));
		}

		if(checkpointId!= null) checkpointList.deleteCheckpoint(checkpointId);

	}

	public static void saveInDeviceWATER(List<TestCase> repairs, Long seed, String fileName, Long reparationTime, String checkpointId, String algorithm, String experimentPath){

		String downloadsPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath();
		String timeLog = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
		WriterUtil dataMetrics = new WriterUtil(downloadsPath+"/"+ experimentPath, "dataMetrics.csv");
		CheckpointUtil checkpointList = new CheckpointUtil(downloadsPath+"/"+ experimentPath);
		WriterUtil testWriter = null;
		TestCase repair;

		for(int i = 0; i<repairs.size(); i++){

			repair = repairs.get(i);

			testWriter = new WriterUtil(downloadsPath+"/"+ experimentPath +"/repairedTests", fileName+"-"+algorithm+"-repair"+i+"-"+timeLog+".txt");
			testWriter.write(repair, (long) seed);

		}
		if(reparationTime != null){
			int seconds = (int) (reparationTime / 1000) % 60 ;
			int minutes = (int) ((reparationTime / (1000*60)) % 60);
			int hours   = (int) ((reparationTime / (1000*60*60)) % 24);


			dataMetrics.write(String.format(fileName+";"+algorithm+";%d h %d min %d sec;%s", hours, minutes, seconds, algorithm));
		}

		if(checkpointId!= null) checkpointList.deleteCheckpoint(checkpointId);

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
