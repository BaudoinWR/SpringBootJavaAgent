package fr.socgen.apm;

import fr.socgen.apm.mbeans.MBeanCaller;
import fr.socgen.apm.mbeans.Randomed;
import fr.socgen.apm.mbeans.ThradCounter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.management.InstanceAlreadyExistsException;
import javax.management.MBeanRegistrationException;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;

@SpringBootApplication
public class GlowrootApplication {

	public static void main(String[] args) throws NotCompliantMBeanException, InstanceAlreadyExistsException, MalformedObjectNameException, MBeanRegistrationException {
		SpringApplication.run(GlowrootApplication.class, args);
		MBeanCaller.register(new Randomed(), "fr.socgen.apm.mbeans:type=Randomed");
		MBeanCaller.register(new ThradCounter(), "fr.socgen.apm.mbeans:type=ThreadCounter");
	}
}
