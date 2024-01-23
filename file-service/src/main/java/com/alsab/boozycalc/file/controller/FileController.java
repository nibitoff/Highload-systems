package com.alsab.boozycalc.file.controller;

import com.alsab.boozycalc.file.service.FileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/files")
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    @PostMapping("/upload")
    public ResponseEntity<Boolean> uploadPartyPicture(@RequestParam String fileName, @RequestParam MultipartFile file) {
        return ResponseEntity.ok(fileService.uploadPartyPicture(fileName, file));
    }

    @PostMapping("/download")
    public ResponseEntity<byte[]> downloadPartyPicture(@RequestParam String fileName) {
        return ResponseEntity.ok(fileService.downloadPartyPicture(fileName));
    }


}

