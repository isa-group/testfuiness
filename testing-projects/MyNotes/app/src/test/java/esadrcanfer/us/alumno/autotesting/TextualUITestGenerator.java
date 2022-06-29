package esadrcanfer.us.alumno.autotesting;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.VariableDeclarationExpr;
import com.github.javaparser.ast.visitor.VoidVisitor;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.github.javaparser.symbolsolver.JavaSymbolSolver;
import com.github.javaparser.symbolsolver.model.resolution.TypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.CombinedTypeSolver;
import com.google.common.collect.Lists;

import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


public class TextualUITestGenerator {

    // Name to change
    private static final String FILE = "MainActivityTest2";

    private static final String FILE_PATH = "src\\androidTest\\java\\esadrcanfer\\us\\alumno\\autotesting\\tests\\"+FILE+".java";

    WriterUtil writerUtil = new WriterUtil();

    private List<String> objectTypes = new ArrayList<>();
    private List<String> selectors = new ArrayList<>();
    private List<String> texts = new ArrayList<>();
    private String tempId;
    private Boolean notSwap = true;
    private int replacingCount = 0;
    private String predicate;
    private String objectId;
    private Boolean isMatchingText = false;
    private Boolean isObjectType = false;

    @Test
    public void textualUITestGenerator() throws FileNotFoundException {
        // Parser configuration:
        TypeSolver typeSolver = new CombinedTypeSolver();
        JavaSymbolSolver symbolSolver = new JavaSymbolSolver(typeSolver);
        StaticJavaParser.getConfiguration().setSymbolResolver(symbolSolver);
        // Parsing:
        CompilationUnit cu = StaticJavaParser.parse(new File(FILE_PATH));
        Optional<ClassOrInterfaceDeclaration> fileParsed = cu.getClassByName(FILE);
        // We print the class to check that the class is correcly parsed
        //System.out.println(fileParsed);
        // We  visit each methos and print its name:
        VoidVisitor<?> methodNameVisitor = new MethodNamePrinter();
        methodNameVisitor.visit(cu, null);
        cu.findAll(MethodDeclaration.class).forEach((md) -> {
            exploreMethods(md);
        });

        writerUtil.write(BuildConfig.APPLICATION_ID);
        writerUtil.write("-1");
        writerUtil.write(String.valueOf(objectTypes.size()));
        for(int i = 0; i < objectTypes.size(); i++){
            writerUtil.write(objectTypes.get(i)+", "+"UiSelector"+"["+selectors.get(i)+"]"+", "+texts.get(i));
        }
        if (predicate != null) {
            writerUtil.write(predicate);
        } else {
            Assert.fail("You must provide an assertion");
        }
    }

    public void exploreMethods(MethodDeclaration md) {
        List<String> methodsToAvoid = Lists.newArrayList("matchesSafely", "childAtPosition", "describeInfo");
        if (!methodsToAvoid.contains(md.getName())) {
            processMethod(md);
        }
    }

    private void processMethod(MethodDeclaration md) {
        // Procesamos las declaraciones de interacciones:
        md.findAll(VariableDeclarationExpr.class).forEach(vde -> {
            processVariableDesclaration(vde);
        });

        List<String> childs = new ArrayList<>();
        // Procesamos las acciones sobre las interacciones:
        md.findAll(MethodCallExpr.class).forEach(cn -> {
            processActionAndSelectors(cn, childs);
        });
    }

    private void processVariableDesclaration(VariableDeclarationExpr vde) {
        for(int i = 0; i < vde.getVariables().size(); i++){
            if((vde.getVariable(i).getName().toString().startsWith("appCompatEditText") || vde.getVariable(i).getName().toString().startsWith("editText")) && vde.getVariable(i).toString().contains("withText")){
                replacingCount++;
            }
        }
    }

    private void processActionAndSelectors(MethodCallExpr mc, List<String> childs) {
        if(mc.getName().toString().equals("scrollTo")){
            objectTypes.add("SCROLL_TO");
            selectors.add("RESOURCE_ID=");
            texts.add(this.tempId);
            if(!texts.isEmpty()){
                Collections.swap(texts,texts.size() -1, texts.size() -2);
            }
            notSwap = false;

        }else if(mc.getName().toString().equals("click")){
            selectors.add(objectId);
            isObjectType = true;
            if(mc.getParentNode().toString().contains("appCompatCheckBox") || mc.getParentNode().toString().contains("materialCheckBox") || mc.getParentNode().toString().contains("checkBox")){
                objectTypes.add("CHECKBOX");
            }
            if(mc.getParentNode().toString().contains("appCompatRadioButton") || mc.getParentNode().toString().contains("materialRadioButton") || mc.getParentNode().toString().contains("radioButton")){
                objectTypes.add("RADIO_BUTTON");
            }
            if(mc.getParentNode().toString().contains("appCompatSpinner") || mc.getParentNode().toString().contains("spinner")){
                objectTypes.add("SPINNER");
                if(!notSwap && !texts.isEmpty()){
                    Collections.swap(texts,texts.size() -1, texts.size() -2);
                }
                texts.add("  ");
            }
            if(mc.getParentNode().toString().contains("appCompatCheckedTextView") || mc.getParentNode().toString().contains("checkedTextView")){
                objectTypes.add("CHECKED_TEXT");
                texts.add("  ");
                selectors.remove(selectors.size() - 1);
            }
            if(mc.getParentNode().toString().contains("switch")){
                objectTypes.add("SWITCH");
            }
            if(mc.getParentNode().toString().contains("appCompatButton") || mc.getParentNode().toString().contains("materialButton")
                    || mc.getParentNode().toString().contains("floatingActionButton") || mc.getParentNode().toString().contains("appCompatToggleButton")
                    || mc.getParentNode().toString().contains("appCompatImageButton")
                    || mc.getParentNode().toString().contains("bottomNavigationItemView")
                    || mc.getParentNode().toString().contains("materialCardView")
                    || mc.getParentNode().toString().contains("chip")
                    || mc.getParentNode().toString().contains("materialTextView")
                    || mc.getParentNode().toString().contains("actionMenuItemView")
                    || mc.getParentNode().toString().contains("navigationRailItemView")
                    || mc.getParentNode().toString().contains("tabView")
                    || mc.getParentNode().toString().contains("actionMenuItemView")
                    || mc.getParentNode().toString().contains("overflowMenuButton")
                    || mc.getParentNode().toString().contains("button")){
                objectTypes.add("BUTTON");
            }
        }

        if (mc.getName().toString().equals("atPosition")) {
            selectors.add("POSITION=" + mc.getArguments().toString().substring(1, mc.getArguments().toString().length() - 1));
        }

        if(mc.getName().toString().equals("click") && (mc.getParentNode().toString().startsWith("Optional[appCompatEditText") || mc.getParentNode().toString().startsWith("Optional[editText"))){
            objectTypes.add("TEXT");
        }

        if(mc.toString().startsWith("childAtPosition")){
            childs.add(mc.toString());
        }

        for(int i = 0; i < mc.getArguments().size(); i++){
            Boolean isChild = false;
            for(int j = 0; j < childs.size(); j++){
                if(childs.get(j).contains(mc.getArgument(i).toString())){
                    isChild = true;
                }
            }

            if(isChild == false){
                if (mc.getName().toString().equals("withId")) {
                    objectId = "RESOURCE_ID=" + BuildConfig.APPLICATION_ID + ":id/" + mc.getArguments().get(i).toString().substring(5);
                    this.tempId = "toElementById=" + mc.getArgument(i).toString().substring(5);
                }

                if (mc.getName().toString().equals("withText")) {
                    texts.add(mc.getArguments().get(i).toString().substring(1, mc.getArgument(i).toString().length() - 1));
                    if (isMatchingText == true) {
                        predicate = "finalState.contains(" + mc.getArgument(i).toString() + ")";
                        texts.remove(texts.size() - 1);
                        isMatchingText = false;
                    }
                }

                if(mc.getName().toString().equals("replaceText")){
                    selectors.add(objectId);
                    objectTypes.add("TEXT");
                    if(!(replacingCount == 0)){
                        texts.remove(texts.size() - 1);
                    }else if (notSwap == false && !texts.isEmpty()){
                        Collections.swap(texts,texts.size() -1, texts.size() -2);
                    }
                    texts.add(mc.getArguments().get(i).toString().substring(1, mc.getArgument(i).toString().length() - 1));
                    replacingCount--;
                }
            }

            if(mc.getArguments().toString().equals("[closeSoftKeyboard()]")){
                selectors.remove(selectors.size() - 1);
                texts.remove(texts.size() - 1);
                replacingCount--;
            }

            if (mc.getName().toString().equals("check")) {
                if(texts.size() == 1){
                    texts.remove(0);
                }
                if(isObjectType){
                    if (mc.getArgument(i).toString().equals("matches(isDisplayed())")){
                        predicate = "finalState.contains(" + '"' + texts.get(texts.size() - 1) + '"' + ")";
                    }
                    texts.remove(texts.size() - 1);
                }
                if(mc.getArgument(i).toString().startsWith("matches(withText(")){
                    isMatchingText = true;
                }
                if(mc.getArgument(i).toString().equals("doesNotExist()")){
                    predicate = "finalState.size() < initialState.size()";
                }
            }
        }
    }

    private class MethodNamePrinter extends VoidVisitorAdapter<Void> {
        @Override
        public void visit(MethodDeclaration md, Void arg) {
            super.visit(md, arg);
        }
    }
}
