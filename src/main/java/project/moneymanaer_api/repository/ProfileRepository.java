package project.moneymanaer_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.moneymanaer_api.entity.ProfileEntity;

import java.util.Optional;

public interface ProfileRepository extends JpaRepository<ProfileEntity, Long> {

    Optional<ProfileEntity> findByUserName(String userName);
}