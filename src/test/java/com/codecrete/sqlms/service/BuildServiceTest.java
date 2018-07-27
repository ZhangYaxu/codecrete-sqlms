package com.codecrete.sqlms.service;

import com.codecrete.sqlms.configuration.UnitTestConfiguration;
import com.codecrete.sqlms.model.BuildReport;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.function.Predicate;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import static java.lang.System.lineSeparator;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

/**
 * @author Eliot Morris
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@ContextConfiguration(classes=UnitTestConfiguration.class)
public class BuildServiceTest {
    
    /**
     * Log4j logger.
     */
    private static final Logger LOG = LogManager.getLogger(BuildServiceTest.class);
    
    @Autowired
    private BuildService buildService;
    
    @Before
    public void setup() throws Exception {
    }
    
    @After
    public void teardown() throws Exception {
    }
    
    @Test
    public void build() throws Exception {

        // TODO: Unpack domain jar
        JarFile jar = getJar("codecrete-domain-0.0.1.jar");
        Path source = unpackJar(jar);
        String domain = "test";
        BuildReport report = buildService.build(source, domain);
    }
    
    
    // Using the classpath determine the absolute path of a jar so we can extract the sql files from it to create the database
    private JarFile getJar(String name) throws IOException {
    
        JarFile jar = null;
        
        // find the path of a jar from the classloader
        ClassLoader cl = ClassLoader.getSystemClassLoader();
        URL[] urls = ((URLClassLoader)cl).getURLs();
    
        for(URL url: urls) {
            
            // Check name for match and return its absolute path
            if (url.getFile().endsWith(name)) {
                jar = new JarFile(url.getFile());
                break;
            }
        }
        
        return jar;
    }
    
    // Return where its unpacked? as source?
    private Path unpackJar(JarFile jar) throws IOException {
        
        // Test whether the jar entry has the configured file extension
        Predicate<JarEntry> hasExtension = (entry) -> {
            return entry.toString().endsWith(".sql");
        };
        
        // Filter JarEntries by dbms type script extension
        List<JarEntry> entries = jar.stream().filter(hasExtension).collect(toList());
        
        Path tmp = Files.createTempDirectory("sqlms");
        
        // Unpack jars sqlms relevant files to tmp directory for future reading
        for (JarEntry entry : entries) {
            try (InputStream inputStream = jar.getInputStream(entry)) {
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
                    
                    Path destination = tmp.resolve(entry.getName());
                    Files.createDirectories(destination.getParent());
                    LOG.debug("Tmp directory created: {}", destination.getParent());
                    
                    try (BufferedWriter writer = Files.newBufferedWriter(destination)) {
                        String contents = reader.lines().collect(joining(lineSeparator()));
                        writer.write(contents);
                        LOG.debug("Jar file unpacked to: {}", destination);
                    }
                }
            }
        }
        
        // Return path to sqlms build root
        return tmp.resolve("mysql");
    }
    
    // TODO:
    private Properties getProperties(JarFile jar) throws Exception, IOException {

        Properties properties = new Properties();

        // Single argument boolean-valued function to determine if the jar entry
        // is our sqlms.properties file
        Predicate<JarEntry> isProperties = (entry) -> {
            return entry.toString().endsWith("sqlms.properties");
        };

        Optional<JarEntry> propertiesEntry = jar.stream().filter(isProperties).findFirst();

        // Open jar entry and load properties
        if (propertiesEntry.isPresent()) {
            try (InputStream inputStream = jar.getInputStream(propertiesEntry.get())) {
                properties.load(inputStream);
                LOG.debug("Property file: {} loaded from jar: {}", propertiesEntry.get().getName(), jar.getName());
            }
        }
        else throw new Exception(String.format("Entry: 'sqlms.properties' not found in jar: %s", jar.getName()));

        return properties;
    }
}