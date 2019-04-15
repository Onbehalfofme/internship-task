package ru.innopolis.demo.data;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.innopolis.demo.models.UserProfile;

import java.util.Optional;

/**
 * JPA-repository for {@link UserProfile} model
 */
public interface UserRepository extends JpaRepository<UserProfile, Long> {

    boolean existsByEmail(String email);

    Optional<UserProfile> findByEmail(String email);
    UserProfile getByEmail(String email);
}
