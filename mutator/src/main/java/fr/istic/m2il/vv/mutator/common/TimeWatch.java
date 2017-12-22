package fr.istic.m2il.vv.mutator.common;


import java.util.concurrent.TimeUnit;

public class TimeWatch {

    long starts;

    @CoverageIgnore
    private TimeWatch() {
        reset();
    }

    @CoverageIgnore
    public static TimeWatch start() {
        return new TimeWatch();
    }

    @CoverageIgnore
    public TimeWatch reset() {
        starts = System.nanoTime();
        return this;
    }

    @CoverageIgnore
    public long time() {
        return System.nanoTime() - starts;
    }

    @CoverageIgnore
    public long time(TimeUnit unit) {
        return unit.convert(time(), TimeUnit.NANOSECONDS);
    }

    @CoverageIgnore
    public String toMinuteSeconds(){
        return String.format("%d min, %d sec", time(TimeUnit.MINUTES),
                time(TimeUnit.SECONDS) - time(TimeUnit.MINUTES));
    }
}
