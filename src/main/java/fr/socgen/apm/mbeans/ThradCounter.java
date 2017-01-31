package fr.socgen.apm.mbeans;

/**
 * Created by x159139 on 31/01/2017.
 */
public class ThradCounter implements ThreadCounterMXBean {
    @Override
    public long getThreadCount() {
        return java.lang.Thread.activeCount();
    }
}
