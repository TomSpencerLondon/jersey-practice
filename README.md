### Jersey 3 Fundamentals

The Jersey RESTful Web Services, formerly Glassfish Jersey, currently Eclipse Jersey, framework is an open source
framework for developing RESTful Web Services in Java.
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

### RESTful Architecture

REST does not mean adhoc service creation. There are standards for how we create restful services. The Richardson
Maturity
Model helps clarify the levels of REST in our applications

![image](https://github.com/TomSpencerLondon/jersey-practice/assets/27693622/6cc502b5-d566-483e-9d5f-517c676250d5)

Level 0 is just xml. Level 0 is focused on one endpoint. Level 1 is focused on objects and urls. Level 2 is focused on
HTTP verbs.
GET is used for getting resources. POST for creating resources. We also use HTTP response codes for our navigation.
Level 3 adds
HATEOAS or links to help with navigation.

#### HTTP verbs

Create, Read, Update, Delete
POST, GET, PUT, DELETE
Level 3 puts the focus on discoverability using HATEOAS.

#### HATEOAS

HATEOAS stands for Hypermedia as the Engine or Application State. The focus is on interacting through hypermedia to
allow the server
and the client to be more decoupled. Future actions are determined by the server response. Links are included for what
the user can
do with the response. This can help Long Term design. The CRUD functions are synonymous with level 2 of the Richardson
maturity model.

| Function | Verb   |
|----------|--------|
| Create   | POST   |
| Read     | GET    | 
| Update   | PUT    |
| Delete   | DELETE |

### Json
JavaScript Object Notation is a more performance and easier to use response that is a simpler version of XML.
JSON is more difficult to validate.

### XML
XML can be used with REST. XML is easy to validate.

### Binary Output
Binary is not necessarily an alternative to JSON and XML. Binary is used for files, images and pdfs. Binary is frequently used
in RESTful services.

#### Using GET to Retrieve Entities
We will create:
- Speaker
- SpeakerRepository
- SpeakerResource
  - We will annotate the service with GET
- http://localhost:8080/speaker

We add an interface for the Repository and extract an interface:
```java
public class SpeakerRepositoryStub implements SpeakerRepository {

    @Override
    public List<Speaker> findAll() {
        List<Speaker> speakers = new ArrayList<>();

        Speaker speaker1 = new Speaker();
        speaker1.setId(1L);
        speaker1.setName("Bryan");
        speaker1.setCompany("Pluralsight");
        speakers.add(speaker1);

        Speaker speaker2 = new Speaker();
        speaker2.setId(2L);
        speaker2.setName("Roger");
        speaker2.setCompany("Wilco");
        speakers.add(speaker2);

        return speakers;
    }
}
```
and this is the interface:
```java
public interface SpeakerRepository {
    List<Speaker> findAll();
}

```

We add the glassfish dependency for the json response:
```xml

<dependency>
  <groupId>org.glassfish.jersey.media</groupId>
  <artifactId>jersey-media-json-binding</artifactId>
</dependency>
```
We now get a response when we go to localhost:8080/speaker:
![image](https://github.com/TomSpencerLondon/LeetCode/assets/27693622/cd81b9fa-c463-4257-82bd-3c30eadfd489)

We can also get single speakers:
```text
@Path("{id}")
@PathParam("id")
```
We will write a lambda function to extract the speaker. The url will look like this:
http://localhost:8080/speaker/1

```java
@Path("speaker")
public class SpeakerResource {

    private SpeakerRepository speakerRepository = new SpeakerRepositoryStub();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Speaker> findAll() {
        return speakerRepository.findAll();
    }

    @Path("{id}")
    @GET
    public Speaker getSpeaker(@PathParam("id") Long id) {
        return speakerRepository.findById(id);
    }
}
```

We also refactor the StubSpeakerRepository:
```java

public class SpeakerRepositoryStub implements SpeakerRepository {

    private final List<Speaker> speakers = new ArrayList<>();

    public SpeakerRepositoryStub() {
        Speaker speaker1 = new Speaker();
        speaker1.setId(1L);
        speaker1.setName("Bryan");
        speaker1.setCompany("Pluralsight");
        speakers.add(speaker1);

        Speaker speaker2 = new Speaker();
        speaker2.setId(2L);
        speaker2.setName("Roger");
        speaker2.setCompany("Wilco");
        speakers.add(speaker2);
    }

    @Override
    public List<Speaker> findAll() {
        return speakers;
    }

    @Override
    public Speaker findById(Long id) {
        return findSpeakerById(speakers, id);
    }

    private Speaker findSpeakerById(List<Speaker> speakers, Long id) {
        return speakers
                .stream()
                .filter(s -> s.getId().equals(id))
                .findFirst()
                .orElseThrow(UnsupportedOperationException::new);
    }
}
```

#### Using POST to Create Entities
We use POST to create objects in our application:
```java
@Path("speaker")
public class SpeakerResource {
  @POST
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  @Produces(MediaType.APPLICATION_JSON)
  public Speaker createSpeakerWithParams(MultivaluedMap<String, String> formParams) {
    System.out.println(formParams.getFirst("name"));
    System.out.println(formParams.getFirst("company"));

    return null;
  }
}
```
We will create a POST method in our SpeakerResource. We will use a curl command to test our POST method. We can test the POST with:
```bash
tom@tom-ubuntu:~/Projects/conference-service$ curl -d "name=steve&company=ACME" -X POST http://localhost:8080/speaker
```
We see the following in the run logs:
```bash
Jersey app started with endpoints available at http://localhost:8080/
Hit Ctrl-C to stop it...
steve
ACME

```

We can add another function to our resource:
```java
@Path("speaker")
public class SpeakerResource {
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Speaker createSpeaker(Speaker speaker) {
        System.out.println(speaker.getName());

        speaker = speakerRepository.create(speaker);
        return speaker;
    }
}
```
We can test the function with:

```bash
tom@tom-ubuntu:~/Projects/conference-service$ curl -w "\n" -d '{"name": "Steve", "company": "ACME"}' -H "Content-Type:application/json" -X POST http://localhost:8080/speaker
{"company":"ACME","id":6,"name":"Steve"}
```

### Building a RESTful Client in Jersey

We can use the Postman client to test our API

![image](https://github.com/TomSpencerLondon/DropWizard-Course/assets/27693622/7c9de442-0aa1-4a1c-8a28-3f696a066632)

We can also test the POST function:
![image](https://github.com/TomSpencerLondon/DropWizard-Course/assets/27693622/cf154da6-1747-4ef6-9d3d-54a4975c31d7)

We can also write a Jersey Client which can be shipped with our project. This can show the intended use. It is repeatable
and production worthy. We will first add the dependencies to our pom.xml:

### Lists of Objects with Jersey
We use GenericType lists of objects with Jersey response.readEntity():
```txt
List<Speakers> speakers = response.readEntity(new GenericType<List<Speaker>>(){});
```

We can use this in our SpeakerClient:
```java
public class SpeakerClient {
    private Client client;
    private final String SPEAKER_URI = "http://localhost:8080/speaker";

    public SpeakerClient() {
        client = ClientBuilder.newClient();
    }


    public Speaker get(Long l) {
        return client.target(SPEAKER_URI)
                .path(String.valueOf(l))
                .request(MediaType.APPLICATION_JSON)
                .get(Speaker.class);
    }

    public List<Speaker> get () {
        Response response = client.target(SPEAKER_URI)
                .request(MediaType.APPLICATION_JSON)
                .get();

        List<Speaker> speakers = response.readEntity(new GenericType<List<Speaker>>(){});

        return speakers;
    }

    public Speaker post (Speaker speaker) {
        return client.target(SPEAKER_URI)
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(speaker, MediaType.APPLICATION_JSON))
                .readEntity(Speaker.class);
    }


    public static void main(String[] args) {
        SpeakerClient client = new SpeakerClient();
        Speaker speaker = client.get(1L);

        System.out.println(speaker.getName());

        List<Speaker> speakers = client.get();

        System.out.println(speakers.size());

        speaker = new Speaker();
        speaker.setName("Alex");
        speaker.setCompany("School");
        speaker = client.post(speaker);

        System.out.println(speaker.getId());
    }

}
```

### Using PUT to Update Entities
PUTs are used to update records. They are very similar to POST:

```java
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.Produces;

@Path("speaker")
public class SpeakerResource {

  @PUT
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Speaker updateSpeaker(Speaker speaker) {
      speaker = speakerRepository.update(speaker);
      
      return speaker;
  }
}
```
![image](https://github.com/TomSpencerLondon/DropWizard-Course/assets/27693622/21c9f4c1-bd3c-44c3-b260-3d049276e207)

### Using DELETE to delete entities
DELETE looks like get by Id and POST combined:

```java
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;

@Path("speaker")
public class SpeakerResource {

  @Path("{id}")
  @DELETE
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public void deleteSpeaker(@PathParam("id") Long id) {
    speakerRepository.delete(id);
  }
}
```

