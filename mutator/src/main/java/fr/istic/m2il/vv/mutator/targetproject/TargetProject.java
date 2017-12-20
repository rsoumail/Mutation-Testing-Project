package fr.istic.m2il.vv.mutator.targetproject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.List;

public class TargetProject {

    private static Logger logger = LoggerFactory.getLogger(TargetProject.class);

    private String classPackage;
    private String testClassPackage;
    private List<Class<?>> classes;
    private String[] classesNames;
    private List<Class<?>> tests;
    private File location;
    private File testsLocation;
    private File classesLocation;
    private File pom;
    private static  TargetProject instance;


    private TargetProject(){
        logger.info("Target project created ");
    }

    public static TargetProject getInstance(){
        if(instance == null){
            instance = new TargetProject();
        }
        return instance;
    }

    public String getClassPackage() {
        return classPackage;
    }

    public void setClassPackage(String classPackage) {
        this.classPackage = classPackage;
    }

    public String getTestClassPackage() {
        return testClassPackage;
    }

    public void setTestClassPackage(String testClassPackage) {
        this.testClassPackage = testClassPackage;
    }

    public List<Class<?>> getClasses() {
        return classes;
    }

    public void setClasses(List<Class<?>> classes) {
        this.classes = classes;
        String[] classesNames = this.ClassesNamesFromClasses(this.classes);
        if(classesNames != null)
          this.setClassesNames(classesNames);
    }

    public List<Class<?>> getTests() {
        return tests;
    }

    public void setTests(List<Class<?>> tests) {
        this.tests = tests;
    }

    public File getLocation() {
        return location;
    }

    public void setLocation(File location) {
        this.location = location;
        this.setClassesLocation(new File(this.getLocation().getAbsolutePath() + "/target/classes"));
        this.setTestsLocation(new File(this.getLocation().getAbsolutePath() + "/target/test-classes"));
    }

    public String[] getClassesNames() {
        return classesNames;
    }

    public void setClassesNames(String[] classesNames) {
        this.classesNames = classesNames;
    }

    public File getPom() {
        return pom;
    }

    public void setPom(File pom) {
        this.pom = pom;
    }

    public File getTestsLocation() {
        return testsLocation;
    }

    public void setTestsLocation(File testsLocation) {
        this.testsLocation = testsLocation;
    }

    public File getClassesLocation() {
        return classesLocation;
    }

    public void setClassesLocation(File classesLocation) {
        this.classesLocation = classesLocation;
    }

    public String getTestClassNameOfClass(String clazz){
        for(Class<?> klass: tests){
            if(klass.getName().matches(clazz+"Test")){
                return klass.getName();
            }
        }
        return null;
    }

    private String[] ClassesNamesFromClasses(List<Class<?>> classes){

        if(classes.size() > 0){
            String[] classesNames = new String[classes.size()];
            int i = 0;
            for(Class<?> clazz: classes){
                classesNames[i] = clazz.getName();
                i++;
            }
            return classesNames;
        }
        else
            return null;
    }
}
