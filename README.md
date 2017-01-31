# SpringBootJavaAgent
Test of JavaAgents on SpringBoot

Start application with :

```
mvn spring-boot:run -Drun.jvmArguments='-javaagent:/home/baudoin/APM/glowroot/glowroot.jar'

mvn spring-boot:run -Drun.jvmArguments="-javaagent:D:/dev/glowroot_source/agent/dist/target/glowroot-agent-0.9.9.jar -Dglowroot.internal.gaugeCollectionIntervalMillis=30000"
```

  * http://localhost:8080/loadCpu/5000

  * http://localhost:8080/loadMemory/5000
