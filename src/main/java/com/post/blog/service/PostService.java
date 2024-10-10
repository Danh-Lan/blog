package com.post.blog.service;

import com.post.blog.model.Post;

import java.util.List;

public interface PostService {
    Post addCategoryToPost(int postId, int categoryId);
    Post removeCategoryFromPost(int postId, int categoryId);
    List<Post> getPostsByCategoryId(int categoryId);
    Post savePost(Post post);
    Post getPostBySlug(String slug);
    List<Post> getAllPosts();
    Post updatePost(Integer id, Post updatedPost);
    void deletePost(Integer id);
}
