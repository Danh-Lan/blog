package com.post.blog.controller;

import com.post.blog.model.Post;
import com.post.blog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/blog/post")
@CrossOrigin(origins = "${frontend.origin}")
public class PostController {
    @Autowired
    private PostService postService;

    @PostMapping("/{postId}/add-category/{categoryId}")
    public ResponseEntity<Post> addCategoryToPost(@PathVariable int postId, @PathVariable int categoryId) {
        Post postAfterAdded = postService.addCategoryToPost(postId, categoryId);
        return ResponseEntity.ok(postAfterAdded);
    }

    @DeleteMapping("/{postId}/remove-category/{categoryId}")
    public ResponseEntity<Post> removeCategoryFromPost(@PathVariable int postId, @PathVariable int categoryId) {
        Post postAfterRemoved = postService.removeCategoryFromPost(postId, categoryId);
        return ResponseEntity.ok(postAfterRemoved);
    }

    @GetMapping("/get-by-category/{categoryId}")
    public ResponseEntity<List<Post>> getPostsByCategory(@PathVariable int categoryId) {
        List<Post> posts = postService.getPostsByCategoryId(categoryId);
        return ResponseEntity.ok(posts);
    }

    @PostMapping("/create")
    public ResponseEntity<Post> createPost(@RequestBody Post post) {
        postService.savePost(post);
        return ResponseEntity.ok(post);
    }

    @GetMapping("/get/{slug}")
    public Post getPostBySlug(@PathVariable String slug) {
        return postService.getPostBySlug(slug);
    }

    @GetMapping("/get-all")
    public List<Post> getAllPosts() {
        return postService.getAllPosts();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Post> updatePost(@PathVariable Integer id, @RequestBody Post post) {
        Post updatedPost = postService.updatePost(id, post);
        return ResponseEntity.ok(updatedPost);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Integer id) {
        postService.deletePost(id);
        return ResponseEntity.noContent().build();
    }
}
