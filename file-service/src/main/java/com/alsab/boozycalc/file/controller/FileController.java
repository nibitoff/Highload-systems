package com.alsab.boozycalc.file.controller;

import com.alsab.boozycalc.file.service.FileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@Tag(name = "File controller", description = "Controller to manage files")
@RestController
@RequestMapping("/api/v1/files")
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    @PostMapping("/upload")
    @Operation(description = "Upload file",
            summary = "Upload file",
            tags = "files")
    public ResponseEntity<Boolean> uploadPartyPicture(@RequestParam String fileName, @RequestParam MultipartFile file) {
        return ResponseEntity.ok(fileService.uploadPartyPicture(fileName, file));
    }

    @PostMapping("/download")
    @Operation(description = "Download file",
            summary = "Download file",
            tags = "files")
    public ResponseEntity<byte[]> downloadPartyPicture(@RequestParam String fileName) {
        return ResponseEntity.ok(fileService.downloadPartyPicture(fileName));
    }


}

