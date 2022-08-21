package qatar.bank.app.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import qatar.bank.app.exception.PDFFileNotFoundException;
import qatar.bank.app.exception.PostNotFoundException;
import qatar.bank.app.repository.PostCommentClient;
import qatar.bank.app.model.FileData;
import qatar.bank.app.model.Post;
import qatar.bank.app.repository.FileDataRepository;

import java.util.Objects;

@Slf4j
@AllArgsConstructor
@Service
public class DocumentPostService {

    private final FileDataRepository fileRepository;
    private final PostCommentClient postRepository;
    public FileData getSavedDocument(Long documentId) throws PDFFileNotFoundException {
        FileData file = fileRepository.findById(documentId).get();
        if (Objects.isNull(file)) {
            throw new PDFFileNotFoundException("PDF file not found");
        }
        return file;
    }

    public Post getSavedPost(Long postId) throws PostNotFoundException {
        Post post = postRepository.getPostById(postId);
        if(Objects.isNull(post)) {
            throw new PostNotFoundException("Post not found");
        }
        return post;
    }

    public Post writePost(Post post) {
        return postRepository.savePost(post);
    }

    public void updatePost(FileData document, Post savedPost) {
        document.setPostId(savedPost.getId());
        fileRepository.save(document);
    }
}
