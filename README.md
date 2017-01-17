# SpringBootJavaAgent
Test of JavaAgents on SpringBoot

Start application with :

```
mvn spring-boot:run -Drun.jvmArguments='-javaagent:/home/baudoin/APM/glowroot/glowroot.jar'
```

  * http://localhost:8080/loadCpu/5000

  * http://localhost:8080/loadMemory/5000
