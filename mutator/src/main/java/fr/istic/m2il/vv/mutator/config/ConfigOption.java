package fr.istic.m2il.vv.mutator.config;

public enum ConfigOption {
    TARGET_PROJECT("target.project"),
    MAVEN_HOME("maven.home"),
    REPORT_DIR("report.dir"),
    TIME_STAMPED_REPORTS("report.timestamped"),
    MUTATORS("mutators");

    private final String option;

    ConfigOption(final String option) {
        this.option = option;
    }

    /* (non-Javadoc)
     * @see java.lang.Enum#toString()
     */
    @Override
    public String toString() {
        return option;
    }
}
