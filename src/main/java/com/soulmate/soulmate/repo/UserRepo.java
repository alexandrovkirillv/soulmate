package com.soulmate.soulmate.repo;

import com.soulmate.soulmate.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long> {
    User findByUsername(String name);
}
