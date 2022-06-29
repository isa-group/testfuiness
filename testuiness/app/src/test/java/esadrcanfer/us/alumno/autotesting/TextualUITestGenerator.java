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


import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;



public class TextualUITestGenerator {

    private static final String FILE_PATH = "src\\androidTest\\java\\esadrcanfer\\us\\alumno\\autotesting\\tests\\SimpleTest.java";

    WriterUtil writerUtil = new WriterUtil();

    private List<String> objectTypes = new ArrayList<>();
    private List<String> selectors = new ArrayList<>();

    @Test
    public void textualUITestGenerator() throws FileNotFoundException {
        // Parser configuration:
        TypeSolver typeSolver = new CombinedTypeSolver();
        JavaSymbolSolver symbolSolver = new JavaSymbolSolver(typeSolver);
        StaticJavaParser.getConfiguration().setSymbolResolver(symbolSolver);
        // Parsing:
        CompilationUnit cu = StaticJavaParser.parse(new File(FILE_PATH));
        Optional<ClassOrInterfaceDeclaration> simpleTest = cu.getClassByName("SimpleTest");
        // We print the class to check that the class is correcly parsed
        System.out.println(simpleTest);
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
            writerUtil.write(objectTypes.get(i)+","+" UiSelector"+"["+selectors.get(i)+"]"+",");
        }
        writerUtil.write("finalState.contains(testActions[1].value)");
    }

    public void exploreMethods(MethodDeclaration md) {
        List<String> methodsToAvoid = Lists.newArrayList("matchesSafely", "childAtPosition", "describeInfo");
        if (!methodsToAvoid.contains(md.getName())) {
            processMethod(md);
            //writerUtil.write(objectType+","+" UiSelector"+"["+selector+"]"+",");
        }
    }

    private void processMethod(MethodDeclaration md) {
        // Procesamos las declaraciones de interacciones:
        md.findAll(VariableDeclarationExpr.class).forEach(vde -> {
            processVariableDesclaration(vde);
        });
        // Procesmoas las acciones sobre las interacciones:
        md.findAll(MethodCallExpr.class).forEach(cn -> {
            processAction(cn);
        });
    }

    private void processVariableDesclaration(VariableDeclarationExpr vde) {
        // Imprimimos el nombre de la variable
        //System.out.println("Variable name:" + vde.getVariables().get(0).getName());
        //writerUtil.write("Variable name:" + vde.getVariables().get(0).getName());
        // Imprimimos el tipo de selector usado y sus parámetros:
        vde.findAll(MethodCallExpr.class).forEach(mc -> {
            finderSelector(mc);
        });
    }

    private void processAction(MethodCallExpr cn) {
        cn.findAll(MethodCallExpr.class).forEach(mc -> {
            if (mc.getName().toString().equals("perform")) {
                if(mc.getArgument(0).toString().equals("ViewActions.click()")){
                    objectTypes.add("BUTTON");
                }
                //System.out.println("Perform " + mc.getArgument(0).toString() + " on " + cn.getScope().toString());
                //writerUtil.write("Perform " + mc.getArgument(0).toString() + " on " + cn.getScope().toString());
            }
        });
    }

    private void finderSelector(MethodCallExpr mc) {
        List<String> supportedSelectors = Lists.newArrayList("withId", "withText");
        if(mc.getScope().toString().equals("Optional[ViewMatchers]")){
            if(mc.getArguments().toString().contains("R")){
                for(int i=0; i < mc.getArguments().size(); i++){
                    if(mc.getParentNode().toString().contains("allOf")) {
                        selectors.add("RESOURCE_ID="+BuildConfig.APPLICATION_ID+":id/"+mc.getArguments().get(i).toString().substring(5));
                    }
                }
            }
        }
    }

    private class MethodNamePrinter extends VoidVisitorAdapter<Void> {
        @Override
        public void visit(MethodDeclaration md, Void arg) {
            super.visit(md, arg);
            //System.out.println("Method Name Printed: " + md.getName());
            //esadrcanfer.us.alumno.autotesting.TextualUITestGenerator.this.writerUtil.write("Method Name Printed: " + md.getNameAsString());
        }
    }
}
