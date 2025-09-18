package com.example.crudlibrary.repository;

import com.example.crudlibrary.model.entity.User;
import com.example.crudlibrary.model.entity.UserRefreshToken;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<UserRefreshToken,Long> {
    Optional<UserRefreshToken> findRefreshTokenByToken(String token);

    @Modifying
    @Transactional
    @Query("DELETE FROM UserRefreshToken r WHERE r.user = :user")
    void deleteByUser(@Param("user") User user);
}
