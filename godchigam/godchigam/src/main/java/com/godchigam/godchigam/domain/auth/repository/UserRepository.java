package com.godchigam.godchigam.domain.auth.repository;

import com.godchigam.godchigam.domain.auth.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    public Optional<User> findByEmail(String email);

    @Modifying
    @Query("update User u set u.password = :password where u.userId = :userId")
    int updatePassword(Long userId, String password);
}
