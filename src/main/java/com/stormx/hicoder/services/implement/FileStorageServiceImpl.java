package com.stormx.hicoder.services.implement;

import com.stormx.hicoder.entities.FileDB;
import com.stormx.hicoder.repositories.FileDBRepository;
import com.stormx.hicoder.services.FileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Objects;
import java.util.regex.Pattern;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class FileStorageServiceImpl implements FileStorageService {
    private final FileDBRepository fileDBRepository;
    @Override
    public FileDB store(MultipartFile file) throws IOException {
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        String urlFriendlyName = fileName.replaceAll(" ", "-");
        try {
            urlFriendlyName = URLEncoder.encode(urlFriendlyName, "UTF-8");
        } catch (UnsupportedEncodingException e) {
           throw  new RuntimeException("Could not encode the file name: " + fileName);
        }
        FileDB FileDB = new FileDB(urlFriendlyName, file.getContentType(), file.getBytes());
        return fileDBRepository.save(FileDB);
    }

    @Override
    public FileDB getFile(String id) {
        return fileDBRepository.findById(id).get();
    }

    @Override
    public Stream<FileDB> getAllFiles() {
        return fileDBRepository.findAll().stream();
    }

}
