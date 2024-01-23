package com.alsab.boozycalc.file.service;

import com.alsab.boozycalc.file.exception.FileOperationException;
import com.alsab.boozycalc.file.exception.PartyPictureIsNotExistException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class FileService {

    public Boolean uploadPartyPicture(String fileName, MultipartFile file){
        try {
            File dir = new File("C:\\Users\\spide\\OneDrive\\Desktop\\STUDIES\\4_th_course\\High-efficiency systems\\Lab1\\file-service\\src\\main\\resources\\pictureStorage");
            if (!dir.exists()) {
                dir.mkdirs();
            }
            File newFile = new File(dir.getAbsolutePath() + "/" + fileName + ".jpg");
            file.transferTo(newFile);
        } catch (IOException e) {
            throw new FileOperationException(e);
        }
        return true;
    }

    public byte[] downloadPartyPicture(String fileName){
        try {
            File file = new File("C:\\Users\\spide\\OneDrive\\Desktop\\STUDIES\\4_th_course\\High-efficiency systems\\Lab1\\file-service\\src\\main\\resources\\pictureStorage\\"
                    + fileName + ".jpg");
            if (!file.exists())
                throw new PartyPictureIsNotExistException(fileName);
            return IOUtils.toByteArray(file.toURI());
        }catch (IOException e){
            throw new FileOperationException(e);
        }
    }
}
