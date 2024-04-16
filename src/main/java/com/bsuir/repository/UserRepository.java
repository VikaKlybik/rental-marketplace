package com.bsuir.repository;

import com.bsuir.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    @Query("SELECT COUNT(u) FROM User u JOIN u.roles r WHERE UPPER(r.name) = UPPER('USER')")
    Integer findAllUserCount();

    @Query("SELECT COUNT(u) FROM User u WHERE u.totalPropertyCount > 1")
    Integer findAllUsersCountWithMoreThanOneProperty();
    boolean existsByUsername(String username);
    boolean existsByUserDetailsPhone(String phone);
    boolean existsByUserDetailsEmail(String email);
}