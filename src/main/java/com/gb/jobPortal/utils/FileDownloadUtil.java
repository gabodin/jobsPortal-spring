package com.gb.jobPortal.utils;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileDownloadUtil {

    private Path foundfile;

    public Resource getFileAsResource(String downloadDir,
                                      String filename) throws IOException {
        Path path = Paths.get(downloadDir);
        Files.list(path).forEach(file -> {
            if (file.getFileName().toString().startsWith(filename)) {
                foundfile = file;
            }
        });
        if (foundfile != null) {
            return new UrlResource(foundfile.toUri());
        }

        return null;
    }
}
