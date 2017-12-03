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

    public JavaAssistHelper(ClassPool pool, Loader loader, CustomTranslator translator, TargetProject targetProject) {
        this.pool = pool;
        this.loader = loader;
        this.translator = translator;
        this.targetProject = targetProject;
        this.initPool();
    }

    public ClassPool getPool() {
        logger.info("Get Pool on  {}",this.targetProject.getLocation());
        return pool;
    }

    public void setPool(ClassPool pool) {
        this.pool = pool;
    }

    public Loader getLoader() {
        return loader;
    }

    public void setLoader(Loader loader) {
        this.loader = loader;
    }

    public CustomTranslator getTranslator() {
        return translator;
    }

    public void setTranslator(CustomTranslator translator) {
        this.translator = translator;
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

            /*Ajouter un jar dans le pool*/
           /* pool.insertClassPath( "/Path/from/root/myjarfile.jar" );*/

        }
        catch(Throwable exc) {
            System.out.println("Impossible de charger les sources de l'input.");
            System.out.println(exc.getMessage());
            exc.printStackTrace();
        }
    }
}
