package fr.socgen.apm;

import fr.socgen.apm.json.GenericResponse;
import fr.socgen.apm.util.MemoryLeaker;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by baudoin on 17/01/17.
 */
@RestController
public class Loadup {
    Log logger = LogFactory.getLog(Loadup.class);

    @GetMapping(path = "/loadCpu/{time}")
    public GenericResponse loadCpu(@PathVariable String time) {
        final int delay = Integer.parseInt(time);
        final long start = System.currentTimeMillis();
        new Thread(() -> {
            logger.info("Starting CPU load");
            while (System.currentTimeMillis() - start < delay) {

            }
            logger.info("Finish CPU load");
        }).start();
        return new GenericResponse("Running loadCpu for " + time + "ms");
    }

    @GetMapping(path = "/loadMemory/{time}")
    public GenericResponse loadMemory(@PathVariable String time) {
        final int delay = Integer.parseInt(time);
        final long start = System.currentTimeMillis();
        new Thread(() -> {
            logger.info("Starting Memory load");
            List<MemoryLeaker> leak = new ArrayList<>();
            while (System.currentTimeMillis() - start < delay) {
                leak.add(new MemoryLeaker());
            }
            logger.info("Finish Memory load");
        }).start();
        return new GenericResponse("Running loadMemory for " + time + "ms");

    }
}
