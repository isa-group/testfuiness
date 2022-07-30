package esadrcanfer.us.alumno.autotesting.util;

import static android.support.test.InstrumentationRegistry.getInstrumentation;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import androidx.test.uiautomator.UiObjectNotFoundException;

import java.io.IOException;
import java.nio.file.InvalidPathException;
import java.util.ArrayList;
import java.util.List;

import esadrcanfer.us.alumno.autotesting.inagraph.actions.TextInputGenerator;

public class TestParser {

    /**
     * Given the path of the folder de contains a test suite written in text plain files,
     * the function creates inside the Download/repairedTest folder of the device a new file
     * that contains the same tests written in a single java test class.
     * @param path A string that represents the path of the folder
     *            inside assets where the tests are located.
     * @param testName The name of the java class generated.
     * @param packageName Name of the app on which the test will be performed (It must be the one
     *                   that appears when access from the main screen/menu of the device).
     */
    public static void parseTextPlainTest(String path, String testName, String packageName){

        Context appContext = getInstrumentation().getTargetContext();
        AssetManager assetManager = appContext.getAssets();

        String[] files;
        List<String> filesText = new ArrayList<>();
        WriterUtil writer = new WriterUtil();
        ReadUtil reader;

        setupJavaTestFile(writer, testName, packageName);

        try{
            files = assetManager.list(path);

            if(files.length != 0){
                for(String file: files){
                    reader = new ReadUtil(path+"/"+file, true);
                    filesText.add(reader.readText());
                }
            }else{
                throw new InvalidPathException(path, "The path is not a directory!");
            }

            String fileText = "";
            String fileName = "";

            for(int i = 0; i < filesText.size(); i++){

                fileText = filesText.get(i);
                fileName = files[i];

                parseTest(writer, packageName, fileText, fileName);
            }
        }catch(IOException e){
            e.printStackTrace();
        }catch(InvalidPathException e){
            e.printStackTrace();
        }finally{
            writer.write("}");
        }

    }

    /**
     * ------------------- Auxiliar methods -------------------
     */

    private static void parseTest(WriterUtil writer, String packageName, String fileText, String fileName){

        createTestHeader(writer, packageName, fileName);

        String[] lines = fileText.split("\n");
        int numberOfActions = Integer.parseInt(lines[2]);

        String action = "";
        String predicate = "";
        String generatorType = "";
        String generatorParameters = "";
        int textInputCounter = 0;
        String[] configLines = ReadUtil.readConfigFile();

        for(int i = 3; i <= numberOfActions+2; i++){
            action = lines[i];

            if(action.startsWith("TEXT")) {

                if(textInputCounter >= configLines.length) textInputCounter = 0;

                String configLine = configLines[textInputCounter];
                textInputCounter++;
                String[] splitConfigLine = configLine.split(":");

                generatorType = splitConfigLine[0].trim();
                generatorParameters = splitConfigLine[1].trim();
            }

            if(action.startsWith("CUSTOM ASSERTION")){
                predicate = action;
            }else{
                writeAction(writer, action, generatorType, generatorParameters);
            }
        }

        writer.write("\t\t// WRITE ASSERTIONS HERE\n");
        writer.write("\t}\n");
    }

    private static void writeAction(WriterUtil writer, String action, String generatorType, String generatorParameters){

        StringBuilder actionToWrite = new StringBuilder();
        String rootDeviceString = "\t\tmDevice.findObject(new UiSelector()";
        String rootScrollableString = "\t\tnew UiScrollable(new UiSelector()";

        Log.d("ISA", action);

        String[] splitAction = action.split(",");
        String type = splitAction[0];
        String selector = "";
        String value = "";

        if(splitAction.length>1) {
            selector = splitAction[1].trim();
            value = splitAction.length == 2 ? "" : splitAction[2].trim();
        }

        String parsedActionType = parseActionType(type, value, generatorType, generatorParameters);

        if(!(type.equals("ENTER") || type.equals("GO_BACK") || type.equals("SCREENSHOT"))) {
            String parsedSelector = parseSelector(selector);

            actionToWrite.append(
                            type.equals("SCROLL_TO") ? rootScrollableString : rootDeviceString
                                );
            actionToWrite.append(parsedSelector);
        }

        actionToWrite.append(parsedActionType);

        writer.write(actionToWrite.toString());
    }

    private static void createTestHeader(WriterUtil writer, String packageName, String fileName){

        String methodName = createTestNameFromFileName(fileName);

        writer.write("\t@Test\n" +
                "\tpublic void "+ methodName +"() throws UiObjectNotFoundException {\n");

        writer.write("\t\tUiDevice mDevice = UiDevice.getInstance(getInstrumentation());\n" +
                            "\t\tmDevice.pressHome();\n" +
                            "\n" +
                            "\t\tUiObject allAppsButton = mDevice.findObject(new UiSelector().description(\"Apps list\"));\n" +
                            "\t\tallAppsButton.click();\n" +
                            "\n" +
                            "\t\tUiScrollable appViews = new UiScrollable(new UiSelector().scrollable(false));\n" +
                            "\t\tappViews.scrollIntoView(new UiSelector().text(\""+ packageName +"\"));\n" +
                            "\n" +
                            "\t\tUiObject testingApp = mDevice.findObject(new UiSelector().text(\""+ packageName +"\"));\n" +
                            "\t\ttestingApp.clickAndWaitForNewWindow();\n");
    }

    private static String createTestNameFromFileName(String fileName){

        String name = fileName.replace(".txt", "");

        return name.substring(0, 1).toLowerCase() + name.substring(1);
    }

    private static String parseSelector(String selector){

        String selectorType = "";
        String selectorValue = "";
        String parsedSelector = "";

        if(!(selector.equals("UiSelector[backButton]") || selector.equals("UiSelector[enterButton]")
                || selector.startsWith("UiSelector[onClass=]") || selector.equals(""))) {

            selectorType = selector.substring(selector.indexOf("[") + 1, selector.indexOf("=")).trim();
            selectorValue = selector.substring(selector.indexOf("=") + 1, selector.length() - 1);

        }

        switch(selectorType){

            case "RESOURCE_ID":
                parsedSelector = ".resourceId(\""+selectorValue+"\"))";
                break;

            case "DESCRIPTION":
                parsedSelector = ".description(\""+selectorValue+"\"))";
                break;

            case "TEXT":
                parsedSelector = ".text(\""+selectorValue+"\"))";
                break;

            case "SCROLLABLE":
                parsedSelector = ".scrollable("+selectorValue+"))";
                break;

            case "POSITION":
                parsedSelector = ".className(\"android.widget.CheckedTextView\").index("+selectorValue+")))";
                break;

            case "CLASS":
                int index = Integer.parseInt(
                        selectorValue.substring(selectorValue.indexOf("(")+1, selectorValue.indexOf(")"))
                );
                String className = selectorValue.substring(0, selectorValue.indexOf("("));

                parsedSelector = ".className(\""+className+"\").index("+index+"))";
                break;
        }

        return parsedSelector;

    }

    private static String parseActionType(String type, String value, String generatorType, String generatorParameters){

        String parsedAction = "";
        try {
            switch (type) {
                case "BUTTON":
                case "CHECKBOX":
                case "RADIO_BUTTON":
                case "SPINNER":
                case "CHECKED_TEXT":
                case "SWITCH":

                    parsedAction = ".click();\n";
                    break;

                case "TEXT":

                    TextInputGenerator textInputGenerator = new TextInputGenerator((long) -1, value, generatorType, generatorParameters);
                    String input = textInputGenerator.generateInput(null);

                    parsedAction = ".setText(\""+input+"\");\n";
                    break;

                case "SCROLL_TO":

                    if (value.startsWith("Elements=")) {
                        parsedAction = ".scrollToEnd("+Integer.valueOf(value.replace("Elements=",""))+");\n";
                    } else if (value.startsWith("toElementById=")) {
                        parsedAction = ".scrollIntoView(new UiSelector().resourceId(\""+value.replace("toElementById=","")+"\"));\n";
                    } else if (value.startsWith("toElementByText=")) {
                        parsedAction = ".scrollIntoView(new UiSelector().text(\""+value.replace("toElementByText=","")+"\"));\n";
                    } else if (value.startsWith("toElementByDescription=")) {
                        parsedAction = ".scrollIntoView(new UiSelector().description(\""+value.replace("toElementByDescription=","")+"\"));\n";
                    } else
                        parsedAction = ".scrollToEnd(Integer.MAX_VALUE);\n";
                    break;

                case "COUNT_DOWN":

                    parsedAction = ".waitUntilGone("+value+");\n";
                    break;

                case "GO_BACK":
                    parsedAction = "mDevice.pressBack()\n";
                    break;
                case "SCREENSHOT":
                    parsedAction = "mDevice.takeScreenshot(new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath()+\"/screenshots\"));\n";
                    break;
                case "ENTER":
                    parsedAction = "mDevice.pressEnter();\n";
                    break;

            }
        } catch (UiObjectNotFoundException e) {
            e.printStackTrace();
        }

        return parsedAction;
    }

    private static void setupJavaTestFile(WriterUtil writer, String testName, String packageName){

        writer.write("// REPLACE THIS LINE WITH THE PACKAGE NAME\n");
        writer.write("// REPLACE THIS LINE WITH IMPORTS\n");

        writer.write("@RunWith(AndroidJUnit4.class)\n" +
                            "@SdkSuppress(minSdkVersion = 18)\n" +
                            "@FixMethodOrder(MethodSorters.NAME_ASCENDING)");

        writer.write("public class " + testName + "{\n");
        writer.write("\tprivate static final int LAUNCH_TIMEOUT = 5000;\n" +
                            "\tprivate static final String BASIC_SAMPLE_PACKAGE = \""+ packageName +"\";\n" +
                            "\tprivate UiDevice mDevice;\n");

        writer.write("\t@Before\n" +
                            "\tpublic void startMainActivityFromHomeScreen() {\n" +
                            "\n" +
                            "\t\t// Initialize UiDevice instance\n" +
                            "\t\tmDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());\n" +
                            "\n" +
                            "\t\t// Start from the home screen\n" +
                            "\t\tmDevice.pressHome();\n" +
                            "\n" +
                            "\t\t// Wait for launcher\n" +
                            "\t\tfinal String launcherPackage = mDevice.getLauncherPackageName();\n" +
                            "\t\tassertThat(launcherPackage, notNullValue());\n" +
                            "\t\tmDevice.wait(Until.hasObject(By.pkg(launcherPackage).depth(0)), LAUNCH_TIMEOUT);\n" +
                            "\n" +
                            "\t\t// Wait for the app to appear\n" +
                            "\t\tmDevice.wait(Until.hasObject(By.pkg(BASIC_SAMPLE_PACKAGE).depth(0)), LAUNCH_TIMEOUT);\n" +
                            "\t}\n");

        writer.write("\t@Test\n" +
                            "\tpublic void checkPreconditions() {\n" +
                            "\t\tassertThat(mDevice, notNullValue());\n" +
                            "\t}\n");

    }

}
