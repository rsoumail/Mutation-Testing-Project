package fr.istic.m2il.vv.mutator.config;

public enum ConfigurationProperties {
    TARGET_PROJECT("target.project"),
    MAVEN_HOME("maven.home"),
    MUTATORS("mutators");

    private final String propertie;

    ConfigurationProperties(final String propertie) {
        this.propertie = propertie;
    }

    /* (non-Javadoc)
     * @see java.lang.Enum#toString()
     */
    @Override
    public String toString() {
        return propertie;
    }
}
