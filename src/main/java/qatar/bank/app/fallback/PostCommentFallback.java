package qatar.bank.app.fallback;

import org.springframework.stereotype.Component;
import qatar.bank.app.repository.PostCommentClient;
import qatar.bank.app.model.Post;

import java.util.Collections;
import java.util.List;

/**
 * author: sohaib Yasir
 * fallback mechanism in 3rd party did not recover form error condition.
 */
@Component
public class PostCommentFallback implements PostCommentClient {
    @Override
    public List<Post> getPosts() {
        return Collections.emptyList();
    }

    @Override
    public Post getPostById(Long postId) {
        return null;
    }

    @Override
    public Post savePost(Post post) {
        return null;
    }
}
