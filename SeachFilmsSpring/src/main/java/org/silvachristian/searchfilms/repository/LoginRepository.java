package org.silvachristian.searchfilms.repository;

import lombok.NonNull;
import org.silvachristian.searchfilms.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LoginRepository extends JpaRepository<UserEntity, Long> {

    @Query("SELECT u.id FROM user u WHERE u.username = :username")
    Long findUserByUsername(String username);

    Optional<UserEntity> findByUsername(@org.jspecify.annotations.NonNull String username);

    boolean existsUserEntitiesByEmail(String email);

    boolean existsUserEntitiesByUsername(String username);
}
