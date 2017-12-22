package fr.istic.m2il.vv.mutator.loader;

import fr.istic.m2il.vv.mutator.targetproject.TargetProject;
import javassist.ClassPool;
import javassist.Loader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JavaAssistHelper {

    private static Logger logger = LoggerFactory.getLogger(JavaAssistHelper.class);
    private ClassPool pool;
    private Loader loader;
    private CustomTranslator translator;
    private TargetProject targetProject;
    private static JavaAssistHelper instance;

    JavaAssistHelper(ClassPool pool, Loader loader, CustomTranslator translator, TargetProject targetProject) {
        this.pool = pool;
        this.loader = loader;
        this.translator = translator;
        this.setTargetProject(targetProject);
        this.initPool();
    }

    public static JavaAssistHelper getInstance(){
        if(instance == null){

            instance = new JavaAssistHelper(new ClassPool(), new Loader(), new CustomTranslator(), TargetProject.getInstance());
        }
        return instance;
    }

    public ClassPool getPool() {
        logger.info("Get Pool on  {}",this.targetProject.getLocation());
        return pool;
    }


    public Loader getLoader() {
        return loader;
    }

    public void setLoader(Loader loader) {
        this.loader = loader;
    }


    public TargetProject getTargetProject() {
		return targetProject;
	}

	public void setTargetProject(TargetProject targetProject) {
		this.targetProject = targetProject;
	}

	private void initPool(){
        try{
            logger.info("Init pool on  {}",this.targetProject.getLocation());
            this.pool = ClassPool.getDefault();
            this.loader = new Loader(pool);
            this.translator = new CustomTranslator();

            loader.addTranslator(pool, translator);
            pool.appendClassPath(targetProject.getClassesLocation().getAbsolutePath());
            pool.appendClassPath(targetProject.getTestsLocation().getAbsolutePath());

        }
        catch(Throwable exc) {
            logger.error("Impossible de charger les sources de l'input.");
            logger.error("Message {}" + exc.getMessage());
            exc.printStackTrace();
        }
    }
}
