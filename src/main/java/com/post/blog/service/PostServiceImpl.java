package com.post.blog.service;

import com.post.blog.model.Category;
import com.post.blog.repository.CategoryRepository;
import com.post.blog.repository.PostRepository;
import com.post.blog.model.Post;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    public Post addCategoryToPost(int postId, int categoryId) {
        Optional<Post> postOptional = postRepository.findById(postId);
        Optional<Category> categoryOptional = categoryRepository.findById(categoryId);

        if (postOptional.isPresent() && categoryOptional.isPresent()) {
            Post post = postOptional.get();
            Category category = categoryOptional.get();
            post.getCategories().add(category);
            postRepository.save(post);

            return post;
        } else {
            throw new EntityNotFoundException("Post or Category not found : " + postId + "; " + categoryId);
        }
    }

    public Post removeCategoryFromPost(int postId, int categoryId) {
        Optional<Post> postOptional = postRepository.findById(postId);
        if (postOptional.isPresent()) {
            Post post = postOptional.get();
            boolean removed = post.getCategories().removeIf(category -> category.getId() == categoryId);

            if (removed) {
                postRepository.save(post);

                return post;
            } else {
                throw new EntityNotFoundException("Category not found in Post");
            }
        } else {
            throw new EntityNotFoundException("Post not found");
        }
    }

    public List<Post> getPostsByCategoryId(int categoryId) {
        return postRepository.findPostsByCategoryId(categoryId);
    }

    @Override
    public Post savePost(Post post) {
        return postRepository.save(post);
    }

    @Override
    public Post getPostBySlug(String slug) { return postRepository.findBySlug(slug); }

    @Override
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    @Override
    public Post updatePost(Integer id, Post updatedPost) {  // PostDTO is better
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Post not found"));

        if (updatedPost.getTitle() != null) {
            post.setTitle(updatedPost.getTitle());
        }

        if (updatedPost.getContent() != null) {
            post.setContent(updatedPost.getContent());
        }

        if (updatedPost.getSlug() != null) {
            post.setSlug(updatedPost.getSlug());
        }

        if (updatedPost.getFrontMatter() != null) {
            post.setFrontMatter(updatedPost.getFrontMatter());
        }

        return postRepository.save(post);
    }

    @Override
    public void deletePost(Integer id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Post not found"));

        postRepository.delete(post);
    }
}
