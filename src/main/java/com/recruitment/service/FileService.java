package com.recruitment.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

@Service
public class FileService {

    @Autowired
    private ResourceLoader resourceLoader;

    public String getFileContent(String fileName) throws Exception {
        // Construct the path dynamically
        String filePath = "classpath:payloads/" + fileName;

        // Load the file as a resource
        Resource resource = resourceLoader.getResource(filePath);

        // Check if the resource exists
        if (!resource.exists()) {
            throw new IllegalArgumentException("File not found: " + fileName);
        }

        // Read the file content and return it as a String
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append(System.lineSeparator());
            }
        }
        return content.toString().trim(); // Trim to remove the trailing newline
    }
}
