package com.post.blog.repository;

import com.post.blog.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {
    @Query("SELECT p FROM Post p JOIN p.categories c WHERE c.id = :categoryId")
    List<Post> findPostsByCategoryId(@Param("categoryId") int categoryId);

    Post findBySlug(String slug);
}
