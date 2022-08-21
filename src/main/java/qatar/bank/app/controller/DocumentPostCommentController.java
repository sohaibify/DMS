package qatar.bank.app.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import qatar.bank.app.exception.PDFFileNotFoundException;
import qatar.bank.app.exception.PostNotFoundException;
import qatar.bank.app.model.FileData;
import qatar.bank.app.model.Post;
import qatar.bank.app.service.DocumentPostService;

/**
 * author:- Sohaib Yasir
 */
@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/document/post")
public class DocumentPostCommentController {


    private final DocumentPostService service;

    /**
     * Write Post of specific pdf document defined in as path variable.
     *
     * @param post
     * @param documentId
     * @return ResponseEntity<FileData>
     * @throws PostNotFoundException
     * @throws PDFFileNotFoundException
     */
    @PostMapping("/write/doc/{documentId}")
    public ResponseEntity<FileData> handleWritePost(@RequestBody Post post, @PathVariable Long documentId) throws PostNotFoundException, PDFFileNotFoundException {
       Post savedPost = service.writePost(post);
       FileData document = service.getSavedDocument(documentId);
       service.updatePost(document, savedPost);
       return new ResponseEntity<>(document, HttpStatus.CREATED);
    }

    /**
     * Read post against specific document.
     *
     * @param documentId
     * @return ResponseEntity<Post>
     * @throws PDFFileNotFoundException
     * @throws PostNotFoundException
     */
    @GetMapping("/getDocumentPost/{documentId}")
    public ResponseEntity<Post> getDocumentPost(@PathVariable Long documentId) throws PDFFileNotFoundException, PostNotFoundException {
        FileData document = service.getSavedDocument(documentId);
        Post post = service.getSavedPost(document.getPostId());
        return new ResponseEntity<>(post, HttpStatus.ACCEPTED);
    }


    // TODO:- Association of comments with post. Did not find call for adding comments against post in guide.
}
