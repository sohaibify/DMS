package qatar.bank.app.service;


import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;
import qatar.bank.app.config.FileStorageProperties;
import qatar.bank.app.exception.PDFFileNotFoundException;
import qatar.bank.app.model.FileData;
import qatar.bank.app.repository.FileDataRepository;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.*;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class FileServiceImpl implements FileService {
    private Path rootLocation;
    @Autowired
   private FileDataRepository fileDataRepository;

    @Autowired
    FileServiceImpl(FileStorageProperties fileStorageProperties) {
        rootLocation = Paths.get(fileStorageProperties.getLocation());
        init();
    }
    @Override
    public void init()  {
        try {
            Files.createDirectories(rootLocation);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional
    @Override
    public void store(MultipartFile file) throws PDFFileNotFoundException {
        if(file.isEmpty()) {
            throw new PDFFileNotFoundException("PDF file not found");
        }
        String name = file.getOriginalFilename();
        try{
            String[] fileName = this.rootLocation.toFile().list(getFileNameFilter(file.getOriginalFilename()));
            if(fileName.length > 0) {
                name = renameFile(file.getOriginalFilename(), fileName);
            }

            Path fileStorePath = this.rootLocation.resolve(Paths.get(name).normalize());
            InputStream stream = file.getInputStream();
            Files.copy(stream, fileStorePath, StandardCopyOption.REPLACE_EXISTING);

            FileData fileData = new FileData();
            fileData.setFileName(name);
            fileData.setFileSize(file.getSize());
            fileData.setLocation(fileStorePath.toString());
            fileDataRepository.save(fileData);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public List<FileData> loadAll() {
        return fileDataRepository.findAll();
    }
    @Override
    public Resource loadAsResource(String filename) throws PDFFileNotFoundException {
        FileData fileData = fileDataRepository.findByFileName(filename);
        if(Objects.isNull(fileData)) {
            throw new PDFFileNotFoundException("Requested file not found");
        }
        Path file = load(fileData.getFileName());
        try {
            Resource resource = new UrlResource(file.toUri());
            return resource;
        } catch (MalformedURLException e) {
            throw new PDFFileNotFoundException("Can not load "+filename, e);
        }
    }

    @Override
    public void deleteAll() throws IOException {
        FileSystemUtils.deleteRecursively(this.rootLocation);

    }

    @Transactional
    @Override
    public void deleteFile(String fileName) throws IOException {
        FileData file = fileDataRepository.findByFileName(fileName);
        if(!Objects.isNull(file)) {
            fileDataRepository.delete(file);
            FileUtils.forceDelete(load(fileName).toFile());
        }
    }

    private Path load(String filename) {
        return this.rootLocation.resolve(filename);
    }

    private String renameFile(String fileName, String[] names) {
        int name = names.length;
        String fileArray[] = fileName.split(".pdf");
        fileName = fileArray[0].concat(" (").concat(""+name).concat(")").concat(".pdf");
        return fileName;
    }

    private FilenameFilter getFileNameFilter(String rename) {
        String fileName = rename.substring(0, rename.length()-4);
        FilenameFilter filenameFilter = new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                if(name.startsWith(fileName) && name.endsWith(".pdf")) {
                    return true;
                } else {
                    return false;
                }
            }
        };
        return filenameFilter;
    }
}
