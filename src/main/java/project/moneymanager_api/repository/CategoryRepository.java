package project.moneymanager_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.moneymanager_api.constants.CategoryType;
import project.moneymanager_api.entity.CategoryEntity;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {

    List<CategoryEntity> findByProfileId(Long profileId);

    Optional<CategoryEntity> findByIdAndProfileId(Long id, Long profileId);

    List<CategoryEntity> findByTypeAndProfileId(CategoryType type, Long profileId);

    Boolean existsByNameAndProfileId(String name, Long profileId);
}
