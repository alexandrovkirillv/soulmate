package com.soulmate.soulmate.repo;

import com.soulmate.soulmate.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

}
