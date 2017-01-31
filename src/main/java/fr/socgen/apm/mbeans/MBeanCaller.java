package fr.socgen.apm.mbeans;

import javax.management.*;
import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by x159139 on 30/01/2017.
 */
public class MBeanCaller {

    private static final MBeanServer mbeanServer;

    static {
        List<MBeanServer> mbeanServers = MBeanServerFactory.findMBeanServer(null);
        if (mbeanServers.size() == 1) {
            mbeanServer = mbeanServers.get(0);
        } else {
            mbeanServer = ManagementFactory.getPlatformMBeanServer();
        }
    }

    public static void register(Object bean, String name) throws NotCompliantMBeanException, InstanceAlreadyExistsException, MBeanRegistrationException, MalformedObjectNameException {
        ObjectName objectName = ObjectName.getInstance(name);
        mbeanServer.registerMBean(bean, objectName);
    }

    public static List<ObjectName> getMeanList() {
        Set mbeans = mbeanServer.queryNames(null, null);
        List<ObjectName> results = new ArrayList<>();
        for (Object mbean : mbeans) {
            results.add((ObjectName) mbean);
            writeAttributes(mbeanServer, (ObjectName) mbean);
        }
        return results;
    }

    private static void writeAttributes(final MBeanServer mBeanServer, final ObjectName http)
    {
        MBeanInfo info = null;
        try {
            info = mBeanServer.getMBeanInfo(http);
            MBeanOperationInfo[] attrInfo = info.getOperations();
            System.out.println("Operations for object: " + http +":\n");
            for (MBeanOperationInfo attr : attrInfo)
            {
                System.out.println("  " + attr.getName() + "\n");
            }
        } catch (InstanceNotFoundException e) {
            e.printStackTrace();
        } catch (IntrospectionException e) {
            e.printStackTrace();
        } catch (ReflectionException e) {
            e.printStackTrace();
        }
    }
}
