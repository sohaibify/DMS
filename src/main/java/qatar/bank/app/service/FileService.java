package qatar.bank.app.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;
import qatar.bank.app.exception.PDFFileNotFoundException;
import qatar.bank.app.model.FileData;


import java.io.IOException;
import java.util.List;

public interface FileService {
    void init() throws IOException;

    void store(MultipartFile file) throws PDFFileNotFoundException;

    List<FileData> loadAll();

    Resource loadAsResource(String filename) throws PDFFileNotFoundException;

    void deleteAll() throws IOException;

    void deleteFile(String fileName) throws IOException;
}
