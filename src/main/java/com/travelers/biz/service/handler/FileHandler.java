package com.travelers.biz.service.handler;

import com.travelers.exception.ErrorCode;
import com.travelers.exception.RunnableWithThrowable;
import com.travelers.exception.TravelersException;
import com.travelers.util.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.io.File;
import java.io.IOException;

@Slf4j
@Component
public class FileHandler {

    private final String localDirectory;

    public FileHandler(@Value("${spring.file.directory}") final String localDirectory){
        this.localDirectory = localDirectory;
    }

    public File convert(@NotNull final MultipartFile multipartFile) {
        final String path = FileUtils.getStoredLocation(multipartFile.getOriginalFilename(), localDirectory);

        final File file = new File(path);
        write(multipartFile, file);
        return file;
    }

    private void write(final MultipartFile multipartFile, final File file) {
        copy(file, () -> {
            if (file.createNewFile()) {
                FileCopyUtils.copy(multipartFile.getBytes(), file);
                return;
            }
            throw new TravelersException(ErrorCode.CLIENT_BAD_REQUEST);
        });
    }

    private void copy(final File file, final RunnableWithThrowable run) {
        try {
            run.runWithThrowable();
        } catch (IOException e) {
            remove(file);
            throw new TravelersException(ErrorCode.CLIENT_BAD_REQUEST);
        }

    }

    public void remove(final File targetFile) {
        if (targetFile.delete()) {
        }
    }
}
