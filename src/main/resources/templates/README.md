${project.artifactId} ${project.version}
========================================
1. [Introduction](#introduction)
1. [Installation](#installation)
1. [Usage](#usage)
1. [Configuration](#configuration)
1. [Plugins](#plugins)
1. [Dependencies](#dependencies)
1. [Documentation](#documentation)
1. [Authors](#authors)
1. [Acknowledgments](#acknowledgments)
1. [License](#license)


Introduction
------------
Structured Query Language source Management Suite

---

**Build Service**: Search the specified source path for the configured root 
source directory. Then recursively search to the specified directory depth for 
all sql scripts with the configured file extension. Future versions will then
interpret all sql scripts for syntax errors and execute them to the specified
schema.

---

**Boilerplate Service**: Use freemarker templates to generate database table,
stored procedure, function and trigger creation scripts. The generated code is 
designed to work in conjunction with the codecrete-orm framework including adding
audit tables and limiting all access to the database to an api of stored routines
for security and speed.

---

**Dashboard Service**: 

---

**Drop Service**: Perform a hard schema drop.

---

**Export Service**: Given a standing sql database, reverse engineer it into a set
of sqlms default directories; patches, records, routines, grants, schemas, users,
tables and triggers, optionally ignoring data records.

---

**Rollback Service**: Attempt to undo the last build by reading the Build Report
and systematically deleting all tables, routines, triggers and even records listed.


Installation
------------
First clone the git repository.

`git clone https://github.com/${git.user}/${project.artifactId}.git`

Then install the jar to your local maven repository.

`mvn install`

Finally add the dependency to your projects pom.
```
<project>
    <dependencies>
        <dependency>
            <groupId>${project.groupId}</groupId>
            <artifactId>${project.artifactId}</artifactId>
            <version>${project.version}</version>
        </dependency>
        ...
    </dependencies>
    ...
</project>            
```

Usage
-----
...


Configuration
-------------


Plugins
-------
The following maven plugins are configured and available by default to the generated 
project. All variables listed in this section are also available during execution 
of the [`mvn archetype:generate`](https://maven.apache.org/archetype/maven-archetype-plugin/generate-mojo.html "Generates a new project from an archetype, or updates the actual project if using a partial archetype") 
goal although its probably easier to just change them in the new projects pom.xml 
manually once its created since they will be populated with the projects current
default values. The actual project [pom.xml](https://github.com/morrisde/codecrete-archetype-lib/blob/master/pom.xml) 
file.

+ [**maven-compiler-plugin**](https://maven.apache.org/plugins/maven-compiler-plugin/): 
The Compiler Plugin is used to compile the sources of your project.
```xml
<project>
    <build>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <version>${maven.compiler.plugin.version}</version>
                <configuration>
                    <source>${java.version}</source>
                    <target>${java.version}</target>
                    <compilerArgs>
                        <arg>-verbose</arg>
                        <arg>-Xlint:all,-path</arg>
                    </compilerArgs>
                </configuration>
            </plugin>
            ...
        </plugins>
        ...
    </build>
    ...
</project>
```

---

+ [**maven-failsafe-plugin**](http://maven.apache.org/surefire/maven-failsafe-plugin/): 
Integration testing utility that is setup to execute during the [`mvn verify`](https://maven.apache.org/guides/introduction/introduction-to-the-lifecycle.html) 
phase of the lifecycle. By default it will execute any test matching the patterns: 
    > "\*\*/IT\*.java", "\*\*/\*IT.java", "\*\*/\*ITCase.java"
```xml
<project>
    <build>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-failsafe-plugin</artifactId>
                <version>${maven.failsafe.plugin.version}</version>
                <executions>
                    <execution>
                        <goals>
                            <goal>integration-test</goal>
                            <goal>verify</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>
           ...
        </plugins>
        ...
    </build>
    ...
</project>
```

---

+ [**maven-jar-plugin**]("https://maven.apache.org/plugins/maven-jar-plugin/"):
The Maven Javadoc plugin generates all javadocs for the project and writes them 
to *project/target/site/javadocs* during the [`mvn compile`](https://maven.apache.org/guides/introduction/introduction-to-the-lifecycle.html)
phase of the lifecycle.
```xml
<project>
    <build>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-jar-plugin</artifactId>
                <version>${maven.jar.plugin.version}</version>
            </plugin>
           ...
        </plugins>
        ...
    </build>
    ...
</project>
```

---

+ [**maven-javadoc-plugin**](https://maven.apache.org/plugins/maven-javadoc-plugin/): 
The Javadoc Plugin uses the Javadoc tool to generate javadocs for the specified 
project.
```xml
<project>
    <build>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-javadoc-plugin</artifactId>
                <version>${maven.javadoc.plugin.version}</version>
                <executions>
                    <execution>
                        <id>create-javadocs</id>
                        <phase>compile</phase>
                        <goals>
                            <goal>javadoc</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>
           ...
        </plugins>
        ...
    </build>
    ...
</project>
```

---

+ [**maven-resources-plugin**](https://maven.apache.org/plugins/maven-resources-plugin/): 
The Resources Plugin handles the copying of project resources to the output directory. 
Configured to copy the compiled README.md markdown velocity template from the 
projects main resources directory to the root directory during the `mvn validate` 
phase. Then during the `mvn package phase` it copies all generated javadocs to 
the projects `/docs` directory for compatibility with [GitHubs pages](https://help.github.com/articles/configuring-a-publishing-source-for-github-pages/#publishing-your-github-pages-site-from-a-docs-folder-on-your-master-branch). 
```xml
<project>
    <build>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-resources-plugin</artifactId>
                <version>${maven.resources.plugin.version}</version>
                <executions>
                    <execution>
                        <id>copy-javadocs</id>
                        <phase>package</phase>
                        <goals>
                            <goal>copy-resources</goal>
                        </goals>
                        <configuration>
                            <outputDirectory>${project.basedir}/docs</outputDirectory>
                            <resources>
                                <resource>
                                    <directory>${project.reporting.outputDirectory}/apidocs</directory>
                                    <filtering>false</filtering>
                                </resource>
                            </resources>
                        </configuration>
                    </execution>
                </executions>
            </plugin>
           ...
        </plugins>
        ...
    </build>
    ...
</project>
```

---

+ [**maven-source-plugin**](https://maven.apache.org/plugins/maven-source-plugin/): 
The Maven source plugin generates a jar archive file of the projects source code 
for distribution. It is configured to run during the [`mvn verify`](https://maven.apache.org/guides/introduction/introduction-to-the-lifecycle.html) 
phase of the lifecycle.
```xml
<project>
    <build>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-source-plugin</artifactId>
                <version>${maven.source.plugin.version}</version>
                <executions>
                    <execution>
                        <id>attach-sources</id>
                        <phase>verify</phase>
                        <goals>
                            <goal>jar-no-fork</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>
            ...
        </plugins>
        ...
    </build>
    ...
</project>
```

---

+ [**maven-surefire-plugin**](http://maven.apache.org/surefire/maven-surefire-plugin/): 
The Surefire Plugin is used during the test phase of the build lifecycle to 
execute the unit tests of an application.
The Maven surefire plugin runs during the [`mvn test`](https://maven.apache.org/guides/introduction/introduction-to-the-lifecycle.html) 
phase of the lifecycle. It runs any files with names matching the following regular expressions.
    > "\*\*/Test\*.java", "\*\*/\*Test.java", "\*\*/\*Tests.java", "\*\*/\*TestCase.java"
```xml
<project>
    <build>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-surefire-plugin</artifactId>
                <version>${maven.surefire.plugin.version}</version>
            </plugin>
           ...
        </plugins>
        ...
    </build>
    ...
</project>
```

---

+ [**versions-maven-plugin**](http://www.mojohaus.org/versions-maven-plugin/): 
The MojoHaus versions plugin detects and reports on all plugin and dependency 
versions against their latest version. It also has goals to automatically update 
versions. In our pom.xml it is configured to execute during the [`mvn process-resources`](https://maven.apache.org/guides/introduction/introduction-to-the-lifecycle.html "copy and process the resources into the destination directory, ready for packaging") 
phase of the build. This goal should not be called directly but instead is executed 
during the [`mvn compile`](https://maven.apache.org/guides/introduction/introduction-to-the-lifecycle.html "compile the source code of the project.") 
phase.
```xml
<project>
    <build>
        <plugins>
            <plugin>
                <groupId>org.codehaus.mojo</groupId>
                <artifactId>versions-maven-plugin</artifactId>
                <version>${versions.maven.plugin.version}</version>
                <executions>
                    <execution>
                        <phase>process-resources</phase>
                        <goals>
                            <goal>display-plugin-updates</goal>
                            <goal>display-dependency-updates</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>
           ...
        </plugins>
        ...
    </build>
    ...
</project>
```


Dependencies
------------
+ [**JUnit**](http://junit.org/junit4/): Unit testing framework.
    + **GroupId:** junit
    + **ArtifactId:** [junit](https://search.maven.org/#search%7Cgav%7C1%7Cg%3A%22junit%22%20AND%20a%3A%22junit%22)
    + **Version**: ${junit.version}
    + **Scope**: test

+ [**AssertJ**](http://joel-costigliola.github.io/assertj/): Rich and fluent 
assertions for testing for Java.
    + **GroupId:** org.assertj
    + **ArtifactId:** [assertj-core](https://search.maven.org/#search%7Cgav%7C1%7Cg%3A%22org.assertj%22%20AND%20a%3A%22assertj-core%22)
    + **Version**: ${assertj.core.version}
    + **Scope**: test

+ [**Log4j Api**](https://logging.apache.org/log4j/2.x/): Apache logging framework 
for Java.
    + **GroupId:** org.apache.logging.log4j
    + **ArtifactId:** [log4j-api](https://search.maven.org/#search%7Cgav%7C1%7Cg%3A%22org.apache.logging.log4j%22%20AND%20a%3A%22log4j-api%22)
    + **Version**: ${log4j.api.version}
    
+ [**Log4j Core**](https://logging.apache.org/log4j/2.x/): Apache logging framework 
for Java.    
    + **GroupId:** org.apache.logging.log4j
    + **ArtifactId:** [log4j-core](https://search.maven.org/#search%7Cgav%7C1%7Cg%3A%22org.apache.logging.log4j%22%20AND%20a%3A%22log4j-core%22)
    + **Version**: ${log4j.core.version}


Documentation
-------------
See [JavaDocs](https://${git.user}.github.io/${project.artifactId}/)


Authors
-------
+ [Eliot Morris](https://www.linkedin.com/in/derek-eliot-morris/ "LinkedIn")


Acknowledgments
---------------
+ [Apache Log4j](https://logging.apache.org/log4j/2.x/team-list.html "Apache Log4j Project Team")
+ [Apache Maven](https://maven.apache.org/team-list.html "Apache Maven Project Team")
+ [AssertJ](https://github.com/joel-costigliola "Joel Costigliola")
+ [JUnit](https://github.com/orgs/junit-team/people "JUnit Team")
+ [MojoHaus](http://www.mojohaus.org/versions-maven-plugin/team-list.html "MojoHaus Versions Project Team")


License
-------
+ [MIT](https://en.wikipedia.org/wiki/MIT_License "MIT License")
