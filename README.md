### Jersey 3 Fundamentals

The Jersey RESTful Web Services, formerly Glassfish Jersey, currently Eclipse Jersey, framework is an open source framework for developing RESTful Web Services in Java.
This course is good for Jersey 3 fundamentals:
https://www.pluralsight.com/courses/jersey-3-fundamentals


This link is quite useful for background on jersey:
https://eclipse-ee4j.github.io/jersey.github.io/documentation/3.0.0/index.html

#### Start project
First we check our java and maven versions:
```bash
tom@tom-ubuntu:~/Projects$ java -version
openjdk version "17.0.6" 2023-01-17
OpenJDK Runtime Environment GraalVM CE 22.3.1 (build 17.0.6+10-jvmci-22.3-b13)
OpenJDK 64-Bit Server VM GraalVM CE 22.3.1 (build 17.0.6+10-jvmci-22.3-b13, mixed mode, sharing)
tom@tom-ubuntu:~/Projects$ mvn -version
Apache Maven 3.6.3
Maven home: /usr/share/maven
Java version: 17.0.6, vendor: GraalVM Community, runtime: /usr/lib/jvm/graalvm-ce-java17-22.3.1
Default locale: en_GB, platform encoding: UTF-8
OS name: "linux", version: "5.19.0-41-generic", arch: "amd64", family: "unix"
```

We can now run the command for generating a 
```bash
tom@tom-ubuntu:~/Projects$ mvn archetype:generate -DarchetypeArtifactId=jersey-quickstart-grizzly2 \
> -DarchetypeGroupId=org.glassfish.jersey.archetypes -DinteractiveMode=false \
> -DgroupId=com.tomspencerlondon -DartifactId=conference-service -Dpackage=com.tomspencerlondon
[INFO] Scanning for projects...
[INFO] 
[INFO] ------------------< org.apache.maven:standalone-pom >-------------------
[INFO] Building Maven Stub Project (No POM) 1
[INFO] --------------------------------[ pom ]---------------------------------
[INFO] 
[INFO] >>> maven-archetype-plugin:3.2.1:generate (default-cli) > generate-sources @ standalone-pom >>>
[INFO] 
[INFO] <<< maven-archetype-plugin:3.2.1:generate (default-cli) < generate-sources @ standalone-pom <<<
[INFO] 
[INFO] 
[INFO] --- maven-archetype-plugin:3.2.1:generate (default-cli) @ standalone-pom ---
[INFO] Generating project in Batch mode
[INFO] Archetype [org.glassfish.jersey.archetypes:jersey-quickstart-grizzly2:3.1.1] found in catalog remote
Downloading from central: https://repo.maven.apache.org/maven2/org/glassfish/jersey/archetypes/jersey-quickstart-grizzly2/3.1.1/jersey-quickstart-grizzly2-3.1.1.pom
Downloaded from central: https://repo.maven.apache.org/maven2/org/glassfish/jersey/archetypes/jersey-quickstart-grizzly2/3.1.1/jersey-quickstart-grizzly2-3.1.1.pom (2.1 kB at 9.9 kB/s)
Downloading from central: https://repo.maven.apache.org/maven2/org/glassfish/jersey/archetypes/project/3.1.1/project-3.1.1.pom
Downloaded from central: https://repo.maven.apache.org/maven2/org/glassfish/jersey/archetypes/project/3.1.1/project-3.1.1.pom (2.2 kB at 70 kB/s)
Downloading from central: https://repo.maven.apache.org/maven2/org/glassfish/jersey/archetypes/jersey-quickstart-grizzly2/3.1.1/jersey-quickstart-grizzly2-3.1.1.jar
Downloaded from central: https://repo.maven.apache.org/maven2/org/glassfish/jersey/archetypes/jersey-quickstart-grizzly2/3.1.1/jersey-quickstart-grizzly2-3.1.1.jar (20 kB at 104 kB/s)
[INFO] ----------------------------------------------------------------------------
[INFO] Using following parameters for creating project from Old (1.x) Archetype: jersey-quickstart-grizzly2:3.1.1
[INFO] ----------------------------------------------------------------------------
[INFO] Parameter: basedir, Value: /home/tom/Projects
[INFO] Parameter: package, Value: com.tomspencerlondon
[INFO] Parameter: groupId, Value: com.tomspencerlondon
[INFO] Parameter: artifactId, Value: conference-service
[INFO] Parameter: packageName, Value: com.tomspencerlondon
[INFO] Parameter: version, Value: 1.0-SNAPSHOT
[INFO] project created from Old (1.x) Archetype in dir: /home/tom/Projects/conference-service
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  4.779 s
[INFO] Finished at: 2023-05-11T13:30:01+01:00
[INFO] ------------------------------------------------------------------------

```

This creates the following folder structure:
![image](https://github.com/TomSpencerLondon/LeetCode/assets/27693622/74ea44d7-d34c-40b9-a2a9-3b30833143c4)

We then change the Java version to Java 17:
```xml

<build>
    <plugins>
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-compiler-plugin</artifactId>
            <version>3.8.1</version>
            <inherited>true</inherited>
            <configuration>
                <source>17</source>
                <target>17</target>
            </configuration>
        </plugin>
        <plugin>
            <groupId>org.codehaus.mojo</groupId>
            <artifactId>exec-maven-plugin</artifactId>
            <version>1.2.1</version>
            <executions>
                <execution>
                    <goals>
                        <goal>java</goal>
                    </goals>
                </execution>
            </executions>
            <configuration>
                <mainClass>com.tomspencerlondon.Main</mainClass>
            </configuration>
        </plugin>
    </plugins>
</build>
```
The first web service has been written for us:
```java
@Path("myresource")
public class MyResource {

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getIt() {
        return "Got it!";
    }
}
```

We also have a main function which uses grizzly to start a server:
```java
 */
public class Main {
    // Base URI the Grizzly HTTP server will listen on
    public static final String BASE_URI = "http://localhost:8080/";

    /**
     * Starts Grizzly HTTP server exposing JAX-RS resources defined in this application.
     * @return Grizzly HTTP server.
     */
    public static HttpServer startServer() {
        // create a resource config that scans for JAX-RS resources and providers
        // in com.tomspencerlondon package
        final ResourceConfig rc = new ResourceConfig().packages("com.tomspencerlondon");

        // create and start a new instance of grizzly http server
        // exposing the Jersey application at BASE_URI
        return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
    }

    /**
     * Main method.
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        final HttpServer server = startServer();
        System.out.println(String.format("Jersey app started with endpoints available at "
                + "%s%nHit Ctrl-C to stop it...", BASE_URI));
        System.in.read();
        server.stop();
    }
}

```

We can view the Jersey service on the grizzly server at localhost:8080/myresource

![image](https://github.com/TomSpencerLondon/LeetCode/assets/27693622/b214c2b7-797d-4687-811f-4bbfa1f7e98d)

