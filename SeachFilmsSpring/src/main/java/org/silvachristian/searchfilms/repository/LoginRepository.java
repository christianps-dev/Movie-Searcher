package org.silvachristian.searchfilms.repository;

import lombok.NonNull;
import org.silvachristian.searchfilms.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginRepository extends JpaRepository<UserEntity, Long> {

    @Query("SELECT CASE WHEN COUNT(username) > 0 THEN true ELSE false END FROM users WHERE username = :username")
    boolean existsByUsername(String username);

    @Query("SELECT u.id FROM users u WHERE u.username = :username")
    Long findUserByUsername(String username);

    UserEntity findByUsername(@org.jspecify.annotations.NonNull String username);
}
