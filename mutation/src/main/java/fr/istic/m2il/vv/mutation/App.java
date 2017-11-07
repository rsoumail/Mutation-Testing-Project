package fr.istic.m2il.vv.mutation;

import javassist.*;

public class App {
    public void main(String[] args){
        try{
            ClassPool pool = ClassPool.getDefault();
            Loader loader = new Loader(pool);

            Translator logger = new Translator() {
                public void start(ClassPool classPool) throws NotFoundException, CannotCompileException {
                    System.out.println("Starting...");
                }

                public void onLoad(ClassPool classPool, String classname) throws NotFoundException, CannotCompileException {
                    System.out.println("Loading...: " + classname);
                }
            };

            loader.addTranslator(pool, logger);
            pool.appendClassPath("input/target/classes");
        }
        catch(Throwable exc) {
            System.out.println("Oh, no! Something went wrong.");
            System.out.println(exc.getMessage());
            exc.printStackTrace();
        }
    }
}
