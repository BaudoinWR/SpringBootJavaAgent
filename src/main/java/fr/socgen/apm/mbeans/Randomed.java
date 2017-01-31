package fr.socgen.apm.mbeans;

import java.util.Random;

/**
 * Created by x159139 on 30/01/2017.
 */
public class Randomed implements RandomedMXBean {

    public Randomed() {}

    @Override
    public long getLong() {
        return new Random().nextInt(100);
    }

}
