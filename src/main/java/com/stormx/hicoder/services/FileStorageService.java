package com.stormx.hicoder.services;

import com.stormx.hicoder.entities.FileDB;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.stream.Stream;

public interface FileStorageService {
    FileDB store(MultipartFile file) throws IOException;

    FileDB getFile(String id);

    Stream<FileDB> getAllFiles();
}
