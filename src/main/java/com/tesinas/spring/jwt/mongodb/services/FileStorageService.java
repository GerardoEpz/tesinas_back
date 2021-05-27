package com.tesinas.spring.jwt.mongodb.services;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.stream.Stream;

public interface FileStorageService {

    public void init(Path folder);

    public void save(byte[] file, Path folder);

    public Resource load(String filename, Path path);

    public void deleteAll(Path folder);

    public Stream<Path> loadAll(Path folder);
}
