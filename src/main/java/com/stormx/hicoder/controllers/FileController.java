package com.stormx.hicoder.controllers;

import com.stormx.hicoder.common.SuccessResponse;
import com.stormx.hicoder.controllers.helpers.ResponseFile;
import com.stormx.hicoder.entities.FileDB;
import com.stormx.hicoder.services.FileStorageService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Tag(name = "File Controller", description = "Include method to manage file")
public class FileController {

    private final FileStorageService storageService;

    @Value("${domain.server}")
    private String SERVER_ADDRESS;

    @PostMapping(value = "/api/v1/media/upload", consumes = "multipart/form-data")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
        try {
            FileDB savedFile = storageService.store(file);
            String fileDownloadUri = SERVER_ADDRESS + "/files/" + savedFile.getId();
            return ResponseEntity.status(HttpStatus.OK).body(
                    new SuccessResponse(HttpStatus.OK,
                            "File uploaded successfully",
                            request.getRequestURI(),
                            fileDownloadUri
                    ));
        } catch (Exception e) {
            throw  new RuntimeException("Could not upload the file: " + file.getOriginalFilename() + "!");
        }
    }

    @GetMapping("/api/v1/media/files")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ResponseFile>> getListFiles() {
        List<ResponseFile> files = storageService.getAllFiles().map(dbFile -> {
            String fileDownloadUri = SERVER_ADDRESS + "/files/" + dbFile.getId();
            return new ResponseFile(
                    dbFile.getName(),
                    fileDownloadUri,
                    dbFile.getType(),
                    dbFile.getData().length);
        }).collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(files);
    }

    @GetMapping("/files/{id}")
    public ResponseEntity<byte[]> getFileByID(@PathVariable String id) {
        FileDB fileDB = storageService.getFile(id);
        String filename = fileDB.getName();
        String format = filename.substring(filename.lastIndexOf(".") + 1);
        MediaType mediaType = getMediaTypeForFileName(format);
        return ResponseEntity.ok()
                .contentType(mediaType)
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + fileDB.getName() + "\"")
                .body(fileDB.getData());
    }


    private MediaType getMediaTypeForFileName(String format) {
        return switch (format.toLowerCase()) {
            case "png" -> MediaType.IMAGE_PNG;
            case "jpg", "jpeg" -> MediaType.IMAGE_JPEG;
            case "gif" -> MediaType.IMAGE_GIF;
            case "mp4" -> MediaType.valueOf("video/mp4");
            default -> MediaType.APPLICATION_OCTET_STREAM;
        };
    }
}