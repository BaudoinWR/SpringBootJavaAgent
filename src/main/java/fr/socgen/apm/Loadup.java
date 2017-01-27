package fr.socgen.apm;

import fr.socgen.apm.json.GenericResponse;
import fr.socgen.apm.util.MemoryLeaker;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.management.*;
import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.io.Serializable;
import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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

    @GetMapping(path = "/mBeans")
    public GenericResponse getMBeans() throws IntrospectionException, InstanceNotFoundException, ReflectionException {
        MBeanServer mbeanServer = null;
        if (mbeanServer == null) {
            List<MBeanServer> mbeanServers = MBeanServerFactory.findMBeanServer(null);
            if (mbeanServers.size() == 1) {
                mbeanServer = mbeanServers.get(0);
            } else {
                mbeanServer = ManagementFactory.getPlatformMBeanServer();
            }
            Set mbeans = mbeanServer.queryNames(null, null);
            for (Object mbean : mbeans) {
                WriteAttributes(mbeanServer, (ObjectName) mbean);
            }
        }
        return null;
    }

    private void WriteAttributes(final MBeanServer mBeanServer, final ObjectName http)
    throws InstanceNotFoundException, IntrospectionException, ReflectionException
    {
        MBeanInfo info = mBeanServer.getMBeanInfo(http);
        MBeanAttributeInfo[] attrInfo = info.getAttributes();

        System.out.println("Attributes for object: " + http +":\n");
        for (MBeanAttributeInfo attr : attrInfo)
        {
            System.out.println("  " + attr.getName() + "\n");
        }
    }

    @GetMapping(path = "/dumpSpace")
    public List<SpaceOnDrive> dumpSpace() {

        FileSystemView fsv = FileSystemView.getFileSystemView();

        File[] drives = File.listRoots();
        List<SpaceOnDrive> result = new ArrayList<>();
        if (drives != null && drives.length > 0) {
            for (File aDrive : drives) {
                result.add(new SpaceOnDrive(aDrive.toString(), fsv.getSystemTypeDescription(aDrive), aDrive.getTotalSpace(), aDrive.getFreeSpace()));
            }
        }
        return result;
    }

    private class SpaceOnDrive implements Serializable {
        private final String drive;
        private final String type;
        private final long totalSpace;
        private final long freeSpace;

        SpaceOnDrive(String drive, String type, long totalSpace, long freeSpace) {
            this.drive = drive;
            this.type = type;
            this.totalSpace = totalSpace;
            this.freeSpace = freeSpace;
        }

        public String getDrive() {
            return drive;
        }

        public String getType() {
            return type;
        }

        public long getTotalSpace() {
            return totalSpace;
        }

        public long getFreeSpace() {
            return freeSpace;
        }

        public String getPercentFree() {
            long freePercentage = (freeSpace*100)/totalSpace;
            return freePercentage + "%";
        }
    }
}
