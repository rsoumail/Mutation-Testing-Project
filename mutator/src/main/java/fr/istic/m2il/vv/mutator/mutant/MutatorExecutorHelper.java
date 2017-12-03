package fr.istic.m2il.vv.mutator.mutant;

import fr.istic.m2il.vv.mutator.targetproject.TargetProject;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class MutatorExecutorHelper {


    public Object getInstanceOf(MutantType type, TargetProject targetProject) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Class<?> clazz = Class.forName("fr.istic.m2il.vv.mutator.mutant."+type.toString());
        Constructor<?> constructor = clazz.getConstructor(TargetProject.class);
        Object instance = constructor.newInstance(targetProject);
        return instance;
    }
}
