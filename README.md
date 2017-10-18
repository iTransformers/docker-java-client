# docker-java-client
Thin wrapper on some docker java SDK api methods

# To configure

Edit src/main/resources/dokcer-java.properties with your docker parameters




#Build
```
mvn clean install
```

# Run
## Through maven
```
mvn spring-boot:run
```

## From java

```
$ java -jar target/DockerServiceManager-1.0-SNAPSHOT-jar-with-dependencies.jar
```