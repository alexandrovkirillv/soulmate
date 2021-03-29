package com.findsoulmate.repo;

import com.findsoulmate.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long> {
    User findByUsername(String name);
}
