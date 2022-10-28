package esadrcanfer.us.alumno.autotesting.util;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class CheckpointUtil {

    private File checkpointFile;

    /**
     * Creates an empty checkpoint list file
     * @param folderPath is the folder on which the checkpoint list will be stored
     */
    public CheckpointUtil(String folderPath){

        if(!isCheckpointFileCreated(folderPath)){
            WriterUtil checkpointWriter = new WriterUtil(folderPath, "checkpointList.txt");
            checkpointWriter.write("ID;TestPath;Algorithm");
        }

        this.checkpointFile =  new File(folderPath+"/checkpointList.txt");
    }

    /**
     * Creates a checkpoint list with the tests given
     * @param folderPath is the folder on which the checkpoint list will be stored
     * @param tests is the list with the path of the test files inside assets.
     * @param algorithm indicates the algorithm with which the tests will be repaired if needed
     */
    public CheckpointUtil(String folderPath, List<String> tests, String algorithm){

        if(!isCheckpointFileCreated(folderPath)){
            WriterUtil checkpointWriter = new WriterUtil(folderPath, "checkpointList.txt");
            checkpointWriter.write("ID;TestPath;Algorithm");

            Integer index = 1;

            for(String test: tests){
                checkpointWriter.write(index + ";" + test + ";" + algorithm);
                index++;
            }
        }

        this.checkpointFile =  new File(folderPath + "/checkpointList.txt");
    }

    public CheckpointUtil(String folderPath, List<String> tests, List<String> algorithmsList){

        if(!isCheckpointFileCreated(folderPath)){
            WriterUtil checkpointWriter = new WriterUtil(folderPath, "checkpointList.txt");
            checkpointWriter.write("ID;TestPath;Algorithm");

            Integer index = 1;

            for(String algorithm: algorithmsList){
                for(String test: tests){
                    checkpointWriter.write(index + ";" + test + ";" + algorithm);
                    index++;
                }
            }
        }

        this.checkpointFile =  new File(folderPath + "/checkpointList.txt");
    }

    /**
     * Add all the tests suite given at the end of the checkpoint list.
     * @param tests is the list with the path of the test files inside assets.
     * @param algorithm indicates the algorithm with which the tests will be repaired if needed
     */
    public void addTests(List<String> tests, String algorithm){

        WriterUtil checkpointWriter = new WriterUtil(Paths.get(checkpointFile.getPath()));
        ReadUtil checkpointReader = new ReadUtil(checkpointFile.getPath());
        String checkpointText = checkpointReader.readText();
        String[] checkpointTextSplitted = checkpointText.split("\n");
        Integer nextIndex = checkpointTextSplitted.length;

        for(String test: tests){
            checkpointWriter.write(nextIndex + ";" + test + ";" + algorithm);
            nextIndex++;
        }

    }

    /**
     *
     * @return the test suite inside a checkpoint list
     */
    public List<String> getTestSuite(){

        List<String> res = new ArrayList<>();

        ReadUtil checkpointReader = new ReadUtil(checkpointFile.getPath());
        String checkpointText = checkpointReader.readText();
        String[] checkpointSplitted = checkpointText.split("\n");
        String checkpoint = null;

        for(int i = 0; i < checkpointSplitted.length; i++){

            if(i!=0){
                checkpoint = checkpointSplitted[i];
                res.add(checkpoint);
            }

        }

        return res;
    }

    /**
     * This method deletes the checkpoint with the given ID
     * @param checkpointId is the ID of the tests that is going to be removed from the checkpoint list
     */
    public void deleteCheckpoint(String checkpointId){

        ReadUtil reader = new ReadUtil(checkpointFile.getPath());
        String fileText = reader.readText();
        String[] fileTextSplitted = fileText.split("\n");
        String filePath = checkpointFile.getPath();

        String parentPath = checkpointFile.getParent();

        WriterUtil newFileWriter = new WriterUtil(parentPath, "temp." + filePath.split("\\.")[1]);

        for(String line: fileTextSplitted){

            if(!hasId(line, checkpointId)){
                newFileWriter.write(line);
            }

        }

        File oldFile = checkpointFile;
        File newFile = newFileWriter.getLogFile();
        oldFile.delete();
        newFile.renameTo(new File(filePath));
        this.checkpointFile = newFile;

    }

    public String getFileName(String checkpointId){
        ReadUtil reader = new ReadUtil(checkpointFile.getPath());
        String fileText = reader.readText();
        String[] fileTextSplitted = fileText.split("\n");

        String fileName = null;
        String filePath = null;

        for(String line: fileTextSplitted){

            if(line.split(";")[0].trim().equals(checkpointId)){
                filePath = line.split(";")[1].trim();
                fileName = filePath.substring(filePath.lastIndexOf("/")+1);
                break;
            }

        }

        return fileName;
    }

    public String getExperimentPath(){
        return this.checkpointFile.getPath().substring(0, checkpointFile.getPath().lastIndexOf("/")+1);
    }

    private static Boolean isCheckpointFileCreated(String folderPath){
        return Files.exists(Paths.get(folderPath + "/" + "checkpointList.txt"));
    }

    private static Boolean hasId(String line, String id){
        return line.split(";")[0].equals(id);
    }
}
