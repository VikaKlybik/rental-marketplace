package com.bsuir.repository;

import com.bsuir.entity.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ChatRepository extends JpaRepository<Chat, Long> {
    Optional<Chat> findByUserOneIdAndUserTwoId(Long userOneId, Long userTwoId);
    @Query("SELECT c FROM Chat c WHERE c.userOne.username = :username OR c.userTwo.username = :username")
    List<Chat> findByUsername(@Param("username") String username);

}