package com.andersen.islam.hw1.loader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class PluginClassLoader extends ClassLoader {
    private final String directory;

    public PluginClassLoader(String directory) {
        this.directory = directory;
    }

    @Override
    public Class<?> findClass(String name) throws ClassNotFoundException {
        String path = directory + "/" + name + ".class";
        try {
            // Read class file as bytes
            byte[] classBytes = Files.readAllBytes(Paths.get(path));

            // Convert to Class object
            return defineClass(null, classBytes, 0, classBytes.length);
        } catch (IOException e) {
            throw new ClassNotFoundException("Failed to load class: " + name, e);
        }
    }
}
