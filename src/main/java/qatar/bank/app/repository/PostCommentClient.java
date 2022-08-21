package qatar.bank.app.repository;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import qatar.bank.app.fallback.PostCommentFallback;
import qatar.bank.app.model.Post;

import java.util.List;

@FeignClient(
            value = "jplaceholder",
            url = "https://jsonplaceholder.typicode.com/",
            fallback = PostCommentFallback.class
            )
public interface PostCommentClient {
    @RequestMapping(method = RequestMethod.GET, value = "/posts")
    List<Post> getPosts();

    @RequestMapping(method = RequestMethod.GET, value = "/posts/{postId}", produces = "application/json")
    Post getPostById(@PathVariable("postId") Long postId);

    @RequestMapping(method = RequestMethod.POST, value = "/posts")
    Post savePost(@RequestBody Post post);
}
