package com.semantiq.server.controller;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
@RequestMapping("/static")
@CrossOrigin(origins = "*")
public class FileController {

    private static final String FILES_DIRECTORY = "src/main/resources/Files/static/";

    @GetMapping("/{file}")
    public ResponseEntity<Resource> serveChatbotBundle(@PathVariable String file) {
        return serveFile(file);
    }

    private ResponseEntity<Resource> serveFile(String fileName) {
        Resource file = loadFileAsResource(fileName);

        if (file == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(file);
    }

    private Resource loadFileAsResource(String fileName) {
        try {
            Path filePath = Paths.get(FILES_DIRECTORY).resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists()) {
                return resource;
            } else {
                return null;
            }
        } catch (MalformedURLException ex) {
            return null;
        }
    }
}
