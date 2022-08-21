package qatar.bank.app.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import qatar.bank.app.exception.PDFFileNotFoundException;
import qatar.bank.app.model.FileData;
import qatar.bank.app.service.FileService;

import java.io.IOException;
import java.util.List;

/**
 * author:- Sohaib Yasir
 */
@Slf4j
@RestController
@RequestMapping(path = "/document")
public class PDFDocumentController {

    private FileService fileService;

    @Autowired
    public PDFDocumentController(FileService storageService) {
        this.fileService = storageService;
    }

    /**
     * Download file of specified name.
     *
     * @param filename
     * @return ResponseEntity<Resource>
     * @throws PDFFileNotFoundException
     */

    @GetMapping("/download/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) throws PDFFileNotFoundException {

        Resource file = fileService.loadAsResource(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    /**
     * View all saved document in fetched form DB.
     *
     * @return List<FileData>
     */

    @GetMapping("/view/all")
    public List<FileData> viewAllDocuments() {
        return fileService.loadAll();
    }

    /**
     * Upload file to database and file system.
     *
     * @param file
     * @param redirectAttributes
     * @return string
     * @throws PDFFileNotFoundException
     */
    @PostMapping("/uploadFile")
    public String handleFileUpload(@RequestParam("file") MultipartFile file,
                                   RedirectAttributes redirectAttributes) throws PDFFileNotFoundException {

        fileService.store(file);
        redirectAttributes.addFlashAttribute("message",
                "You successfully uploaded " + file.getOriginalFilename() + "!");
        return "File uploaded";
    }

    /**
     * Delete from file system and database.
     * @param fileName
     * @throws IOException
     */
    @DeleteMapping("delete/{fileName}")
    public void deleteDocument(@PathVariable String fileName) throws IOException {
        fileService.deleteFile(fileName);
    }

    @ExceptionHandler(PDFFileNotFoundException.class)
    public ResponseEntity<?> handlePdfFileNotFound(PDFFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }

}
