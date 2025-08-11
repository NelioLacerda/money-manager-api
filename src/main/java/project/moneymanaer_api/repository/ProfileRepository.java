package project.moneymanaer_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.moneymanaer_api.entity.ProfileEntity;

import java.util.Optional;

public interface ProfileRepository extends JpaRepository<ProfileEntity, Long> {

    Optional<ProfileEntity> findByEmail(String email);

    Optional<ProfileEntity> findByUserName(String userName);

    Optional<ProfileEntity> findByActivationToken(String activationToken);
}